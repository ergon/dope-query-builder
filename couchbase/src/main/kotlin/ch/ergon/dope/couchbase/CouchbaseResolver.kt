package ch.ergon.dope.couchbase

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryString
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.merge
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.AliasedBucketDefinition
import ch.ergon.dope.resolvable.bucket.IndexReference
import ch.ergon.dope.resolvable.bucket.UseIndex
import ch.ergon.dope.resolvable.bucket.UseKeysClass
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.IndexHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysHintClass
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.SetAssignment
import ch.ergon.dope.resolvable.clause.model.WindowDeclaration
import ch.ergon.dope.resolvable.clause.model.WithClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Between
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.CurrentRow
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Following
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Preceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedFollowing
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedPreceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.CaseClass
import ch.ergon.dope.resolvable.expression.type.ObjectEntryPrimitive
import ch.ergon.dope.resolvable.expression.type.function.string.factory.CustomTokenOptions
import ch.ergon.dope.resolver.QueryResolver

interface AbstractCouchbaseResolver : QueryResolver<CouchbaseDopeQuery> {
    abstract override val manager: DopeQueryManager
}

class CouchbaseResolver(
    override val manager: DopeQueryManager = DopeQueryManager(),
) : ClauseResolver, ExpressionResolver, InfixOperatorResolver {
    override fun resolve(resolvable: Resolvable): CouchbaseDopeQuery =
        when (resolvable) {
            is Clause -> resolve(resolvable)

            is Expression<*> -> resolve(resolvable)

            is WithClause -> {
                val first = resolvable.withExpression
                val firstDope = first.toWithDefinitionDopeQuery(this)
                val additionalWithExpressions = resolvable.additionalWithExpressions.map { variable ->
                    variable.toWithDefinitionDopeQuery(this)
                }
                CouchbaseDopeQuery(
                    queryString = "WITH ${listOf(firstDope, *additionalWithExpressions.toTypedArray()).joinToString(", ") { it.queryString }}",
                    parameters = firstDope.parameters.merge(*additionalWithExpressions.map { it.parameters }.toTypedArray()),
                )
            }

            is OrderExpression -> {
                val exp = resolvable.expression.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = listOfNotNull(exp.queryString, resolvable.orderByType?.queryString).joinToString(" "),
                    parameters = exp.parameters,
                )
            }

            is SetAssignment<*> -> {
                val field = resolvable.field.toDopeQuery(this)
                val value = resolvable.value.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = "${field.queryString} = ${value.queryString}",
                    parameters = field.parameters.merge(value.parameters),
                )
            }

            is AliasedBucketDefinition -> CouchbaseDopeQuery("`${resolvable.name}` AS `${resolvable.alias}`")

            is UseKeysClass -> {
                val bucket = when (val b = resolvable.bucket) {
                    is AliasedBucket -> b.asBucketDefinition().toDopeQuery(this)
                    else -> resolvable.bucket.toDopeQuery(this)
                }
                val keys = resolvable.useKeys.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(bucket.queryString, "USE KEYS", keys.queryString),
                    parameters = bucket.parameters.merge(keys.parameters),
                )
            }

            is IndexReference -> {
                val name = resolvable.indexName?.let { "`$it`" }
                val type = resolvable.indexType?.queryString
                CouchbaseDopeQuery(queryString = listOfNotNull(name, type).joinToString(" "))
            }

            is UseIndex -> {
                val bucket = resolvable.bucket.toDopeQuery(this)
                val refs = resolvable.indexReferences.map { it.toDopeQuery(this) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        bucket.queryString,
                        "USE INDEX",
                        formatListToQueryStringWithBrackets(refs, separator = ", ", prefix = "(", postfix = ")"),
                    ),
                    parameters = bucket.parameters.merge(*refs.map { it.parameters }.toTypedArray()),
                )
            }

            is ObjectEntryPrimitive<*> -> {
                val key = resolvable.key.toDopeQuery(this)
                val value = resolvable.value.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = "${key.queryString} : ${value.queryString}",
                    parameters = key.parameters.merge(value.parameters),
                )
            }

            is OverWindowDefinition -> {
                val win = resolvable.windowDefinition.toDopeQuery(this)
                CouchbaseDopeQuery("OVER (${win.queryString})", win.parameters)
            }

            is OverWindowReference -> CouchbaseDopeQuery("OVER `${resolvable.windowReference}`")

            is WindowDefinition -> {
                val ref = resolvable.windowReferenceExpression?.toDopeQuery(this)
                val parts = mutableListOf<String>()
                val params = mutableListOf<DopeParameters>()
                if (ref != null) {
                    parts += ref.queryString; params += ref.parameters
                }
                resolvable.windowPartitionClause?.map { it.toDopeQuery(this) }?.let { list ->
                    parts += list.joinToString(", ", prefix = "PARTITION BY ") { it.queryString }
                    params += list.map { it.parameters }
                }
                resolvable.windowOrderClause?.map { it.toDopeQuery(this) }?.let { list ->
                    parts += list.joinToString(", ", prefix = "ORDER BY ") { it.queryString }
                    params += list.map { it.parameters }
                }
                val frame = resolvable.windowFrameClause?.toDopeQuery(this)
                if (frame != null) {
                    parts += frame.queryString; params += listOf(frame.parameters)
                }
                CouchbaseDopeQuery(parts.joinToString(" "), params.merge())
            }

            is WindowFrameClause -> {
                val extent = resolvable.windowFrameExtent.toDopeQuery(this)
                val windowFrameQueryString = listOfNotNull(
                    resolvable.windowFrameType.queryString,
                    extent.queryString,
                    resolvable.windowFrameExclusion?.queryString,
                ).joinToString(" ")
                CouchbaseDopeQuery(windowFrameQueryString, extent.parameters)
            }

            is Between -> {
                val b = resolvable.between.toDopeQuery(this)
                val a = resolvable.and.toDopeQuery(this)
                CouchbaseDopeQuery(
                    formatToQueryString("BETWEEN", b.queryString, "AND", a.queryString, separator = " "),
                    b.parameters.merge(a.parameters),
                )
            }

            is UnboundedFollowing -> CouchbaseDopeQuery("UNBOUNDED FOLLOWING")

            is UnboundedPreceding -> CouchbaseDopeQuery("UNBOUNDED PRECEDING")

            is CurrentRow -> CouchbaseDopeQuery("CURRENT ROW")

            is Following -> {
                val off = resolvable.offset.toDopeQuery(this)
                CouchbaseDopeQuery(formatToQueryString(off.queryString, "FOLLOWING"), off.parameters)
            }

            is Preceding -> {
                val off = resolvable.offset.toDopeQuery(this)
                CouchbaseDopeQuery(formatToQueryString(off.queryString, "PRECEDING"), off.parameters)
            }

            is OrderingTerm -> {
                val expressionDopeQuery = resolvable.expression.toDopeQuery(this)
                val orderingQueryString =
                    expressionDopeQuery.queryString + (
                        resolvable.orderType?.let { " $it" }
                            ?: ""
                        ) + (resolvable.nullsOrder?.let { " " + it.queryString } ?: "")
                CouchbaseDopeQuery(orderingQueryString, expressionDopeQuery.parameters)
            }

            is WindowDeclaration -> {
                val windowDefinitionDopeQuery = resolvable.windowDefinition?.toDopeQuery(this)
                CouchbaseDopeQuery(
                    "`${resolvable.reference}` AS (${windowDefinitionDopeQuery?.queryString.orEmpty()})",
                    windowDefinitionDopeQuery?.parameters.orEmpty(),
                )
            }

            is CaseClass<*> -> {
                val casePart = resolvable.case?.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = "CASE" + (casePart?.queryString?.let { " $it" } ?: ""),
                    parameters = casePart?.parameters.orEmpty(),
                )
            }

            is Asterisk -> {
                val path = resolvable.path?.toDopeQuery(this)?.queryString
                CouchbaseDopeQuery(queryString = path?.let { "$it.*" } ?: "*")
            }

            is CustomTokenOptions -> {
                val options = listOfNotNull(
                    resolvable.name?.let { "name" to it },
                    resolvable.case?.let { "case" to "\"${it.queryString}\"" },
                    resolvable.specials?.let { "specials" to it },
                )
                val queryString = options
                    .joinToString(", ", "{", "}") { (key, value) -> "\"$key\": $value" }
                    .takeIf { options.isNotEmpty() }
                    .orEmpty()

                CouchbaseDopeQuery(queryString = queryString)
            }

            is HashOrNestedLoopHint -> when (resolvable) {
                HashOrNestedLoopHint.HASH_BUILD -> CouchbaseDopeQuery("HASH (BUILD)")
                HashOrNestedLoopHint.HASH_PROBE -> CouchbaseDopeQuery("HASH (PROBE)")
                HashOrNestedLoopHint.NESTED_LOOP -> CouchbaseDopeQuery("NL")
            }

            is KeysHintClass -> {
                val keys = resolvable.keys.toDopeQuery(this)
                CouchbaseDopeQuery(queryString = "KEYS ${keys.queryString}", parameters = keys.parameters)
            }

            is IndexHint -> {
                val refs = resolvable.indexReferences.map { it.toDopeQuery(this) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryString("INDEX", formatListToQueryStringWithBrackets(refs)),
                    parameters = refs.map { it.parameters }.merge(),
                )
            }

            else -> throw UnsupportedOperationException("Not supported: $resolvable")
        }
}
