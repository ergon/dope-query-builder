package ch.ergon.dope.couchbase

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.resolvable.expression.type.MetaExpression.MetaField
import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatPathToQueryString
import ch.ergon.dope.couchbase.util.formatToQueryString
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSeparator
import ch.ergon.dope.merge
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.ArrayAccess
import ch.ergon.dope.resolvable.expression.type.ArrayPrimitive
import ch.ergon.dope.resolvable.expression.type.BooleanPrimitive
import ch.ergon.dope.resolvable.expression.type.CaseExpression
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.resolvable.expression.type.ElseCaseExpression
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.MISSING
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.ObjectEntry
import ch.ergon.dope.resolvable.expression.type.ObjectPrimitive
import ch.ergon.dope.resolvable.expression.type.Parameter
import ch.ergon.dope.resolvable.expression.type.SelectExpression
import ch.ergon.dope.resolvable.expression.type.StringPrimitive
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.NegationExpression
import ch.ergon.dope.resolvable.expression.type.collection.ExistsExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.collection.SatisfiesExpression
import ch.ergon.dope.resolvable.expression.type.function.array.UnpackExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponentType
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType
import ch.ergon.dope.resolvable.expression.type.logic.NotExpression
import ch.ergon.dope.resolvable.expression.type.range.RangeIndexedLike
import ch.ergon.dope.resolvable.expression.type.range.RangeLike
import ch.ergon.dope.resolvable.expression.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsMissingExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotMissingExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotNullExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotValuedExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNullExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsValuedExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotBetweenExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

internal object TypeExpressionResolver {
    fun resolve(resolver: CouchbaseResolver, typeExpression: TypeExpression<*>) =
        when (typeExpression) {
            is FunctionOperator -> FunctionOperatorResolver.resolve(resolver, typeExpression)
            is NotExpression -> {
                val arg = typeExpression.argument.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryStringWithSeparator("NOT", separator = " ", arg.queryString),
                    arg.parameters,
                )
            }

            is NegationExpression -> {
                val arg = typeExpression.argument.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryStringWithSeparator("-", separator = "", arg.queryString),
                    arg.parameters,
                )
            }

            is BetweenExpression<*> -> {
                val left = typeExpression.expression.toDopeQuery(resolver)
                val lower = typeExpression.start.toDopeQuery(resolver)
                val upper = typeExpression.end.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        left.queryString,
                        "BETWEEN ${lower.queryString}",
                        "AND ${upper.queryString}",
                        separator = " ",
                    ),
                    parameters = left.parameters.merge(lower.parameters, upper.parameters),
                )
            }

            is NotBetweenExpression<*> -> {
                val left = typeExpression.expression.toDopeQuery(resolver)
                val lower = typeExpression.start.toDopeQuery(resolver)
                val upper = typeExpression.end.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        left.queryString,
                        "NOT BETWEEN ${lower.queryString}",
                        "AND ${upper.queryString}",
                        separator = " ",
                    ),
                    parameters = left.parameters.merge(lower.parameters, upper.parameters),
                )
            }

            is IsNullExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryString(fieldDopeQuery.queryString, "IS NULL"),
                    fieldDopeQuery.parameters,
                )
            }

            is IsNotNullExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryString(fieldDopeQuery.queryString, "IS NOT NULL"),
                    fieldDopeQuery.parameters,
                )
            }

            is IsMissingExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryString(fieldDopeQuery.queryString, "IS MISSING"),
                    fieldDopeQuery.parameters,
                )
            }

            is IsNotMissingExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryString(fieldDopeQuery.queryString, "IS NOT MISSING"),
                    fieldDopeQuery.parameters,
                )
            }

            is IsValuedExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryString(fieldDopeQuery.queryString, "IS VALUED"),
                    fieldDopeQuery.parameters,
                )
            }

            is IsNotValuedExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryString(fieldDopeQuery.queryString, "IS NOT VALUED"),
                    fieldDopeQuery.parameters,
                )
            }

            is ExistsExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = "EXISTS ${arrayDopeQuery.queryString}",
                    parameters = arrayDopeQuery.parameters,
                )
            }

            is SatisfiesExpression<*> -> {
                val array = typeExpression.arrayExpression.toDopeQuery(resolver)
                val iteratorName = typeExpression.iteratorName ?: resolver.manager.iteratorManager.getIteratorName()

                @Suppress("UNCHECKED_CAST")
                val predicateFunc =
                    typeExpression.predicate as (Iterator<ValidType>) -> TypeExpression<BooleanType>
                val predicate = predicateFunc(Iterator(iteratorName)).toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = "${typeExpression.satisfiesType} `$iteratorName` " +
                        "IN ${array.queryString} SATISFIES ${predicate.queryString} END",
                    parameters = array.parameters.merge(predicate.parameters),
                )
            }

            is Iterator<*> -> CouchbaseDopeQuery("`${typeExpression.variable}`")

            is SelectExpression<*> -> {
                val inner = typeExpression.selectClause.toDopeQuery(resolver)
                CouchbaseDopeQuery("(${inner.queryString})", inner.parameters)
            }

            is ArrayAccess<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                val indexDopeQuery = typeExpression.index.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    "${arrayDopeQuery.queryString}[${indexDopeQuery.queryString}]",
                    arrayDopeQuery.parameters.merge(indexDopeQuery.parameters),
                )
            }

            is ObjectEntry<*> -> {
                val objectDopeQuery = typeExpression.objectExpression.toDopeQuery(resolver)
                CouchbaseDopeQuery("${objectDopeQuery.queryString}.`${typeExpression.key}`", objectDopeQuery.parameters)
            }

            is MetaField<*> -> {
                val meta = typeExpression.metaExpression.toDopeQuery(resolver)
                CouchbaseDopeQuery("${meta.queryString}.`${typeExpression.name}`", meta.parameters)
            }

            is Parameter<*> -> {
                when (val name = typeExpression.parameterName) {
                    null -> {
                        val placeholder = "$" + resolver.manager.parameterManager.count
                        CouchbaseDopeQuery(
                            queryString = placeholder,
                            parameters = DopeParameters(positionalParameters = listOf(typeExpression.value)),
                        )
                    }

                    else -> CouchbaseDopeQuery(
                        queryString = "$$name",
                        parameters = DopeParameters(namedParameters = mapOf(name to typeExpression.value)),
                    )
                }
            }

            NULL -> CouchbaseDopeQuery("NULL")
            MISSING -> CouchbaseDopeQuery("MISSING")
            TRUE -> CouchbaseDopeQuery("TRUE")
            FALSE -> CouchbaseDopeQuery("FALSE")

            is NumberPrimitive -> CouchbaseDopeQuery("${typeExpression.value}")
            is StringPrimitive -> CouchbaseDopeQuery("\"${typeExpression.value}\"")
            is BooleanPrimitive -> {
                when (typeExpression.value) {
                    true -> CouchbaseDopeQuery("TRUE")
                    false -> CouchbaseDopeQuery("FALSE")
                }
            }

            is ArrayPrimitive<*> -> {
                val items = typeExpression.collection.map { it.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = formatListToQueryStringWithBrackets(items, prefix = "[", postfix = "]"),
                    parameters = items.map { it.parameters }.merge(),
                )
            }

            is DateUnitType -> CouchbaseDopeQuery("\"${typeExpression.name}\"")
            is DateComponentType -> CouchbaseDopeQuery("\"${typeExpression.name}\"")

            is ObjectPrimitive -> {
                val entries = typeExpression.entries.map { it.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = "{${entries.joinToString(", ") { it.queryString }}}",
                    parameters = entries.map { it.parameters }.merge(),
                )
            }

            is CaseExpression<*, *> -> {
                val case = typeExpression.case.toDopeQuery(resolver)
                val pairs = listOf(typeExpression.firstSearchResult) + typeExpression.additionalSearchResults
                val expressionDopeQueries =
                    pairs.map { it.searchExpression.toDopeQuery(resolver) to it.resultExpression.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = case.queryString +
                        expressionDopeQueries.joinToString(separator = " ", prefix = " ", postfix = " ") {
                            "WHEN ${it.first.queryString} THEN ${it.second.queryString}"
                        } + "END",
                    parameters = case.parameters.merge(
                        *expressionDopeQueries.map { it.first.parameters.merge(it.second.parameters) }
                            .toTypedArray(),
                    ),
                )
            }

            is ElseCaseExpression<*, *> -> {
                val case = typeExpression.case.toDopeQuery(resolver)
                val pairs = listOf(typeExpression.firstSearchResult) + typeExpression.additionalSearchResults
                val expressionDopeQueries =
                    pairs.map { it.searchExpression.toDopeQuery(resolver) to it.resultExpression.toDopeQuery(resolver) }
                val elseDopeQuery = typeExpression.elseCase.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = case.queryString +
                        expressionDopeQueries.joinToString(separator = " ", prefix = " ", postfix = " ") {
                            "WHEN ${it.first.queryString} THEN ${it.second.queryString}"
                        } + "ELSE ${elseDopeQuery.queryString} " + "END",
                    parameters = case.parameters.merge(
                        *expressionDopeQueries.map { it.first.parameters.merge(it.second.parameters) }
                            .toTypedArray(),
                    ),
                )
            }

            is IField<*> -> {
                CouchbaseDopeQuery(
                    queryString = formatPathToQueryString(typeExpression.name, typeExpression.path),
                )
            }

            is UnpackExpression -> {
                val objectDopeQuery = typeExpression.objectArray.toDopeQuery(resolver)
                CouchbaseDopeQuery("${objectDopeQuery.queryString}[*]", objectDopeQuery.parameters)
            }

            is DopeVariable<*> -> CouchbaseDopeQuery("`${typeExpression.name}`")

            is RangeLike<*, *, *> -> {
                val rangeDopeQuery = typeExpression.range.toDopeQuery(resolver)
                val iteratorVariable = typeExpression.iteratorName ?: resolver.manager.iteratorManager.getIteratorName()
                val iteratorAny = Iterator<ValidType>(iteratorVariable)

                @Suppress("UNCHECKED_CAST")
                val withKeyExpression =
                    (typeExpression.withAttributeKeys as ((Iterator<ValidType>) -> TypeExpression<StringType>)?)?.invoke(
                        iteratorAny,
                    )

                @Suppress("UNCHECKED_CAST")
                val transformationExpression =
                    (typeExpression.transformation as (Iterator<ValidType>) -> TypeExpression<ValidType>)(iteratorAny)

                @Suppress("UNCHECKED_CAST")
                val condExpr =
                    (typeExpression.condition as ((Iterator<ValidType>) -> TypeExpression<BooleanType>)?)?.invoke(
                        iteratorAny,
                    )
                val withAttributeKeysDopeQuery = withKeyExpression?.toDopeQuery(resolver)
                val transformationDopeQuery = transformationExpression.toDopeQuery(resolver)
                val conditionDopeQuery = condExpr?.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = "${typeExpression.transformationType.name} " +
                        withAttributeKeysDopeQuery?.let { "${withAttributeKeysDopeQuery.queryString}:" }.orEmpty() +
                        "${transformationDopeQuery.queryString} FOR `$iteratorVariable` " +
                        "${typeExpression.membershipType.name} ${rangeDopeQuery.queryString} " +
                        conditionDopeQuery?.let { "WHEN ${conditionDopeQuery.queryString} " }.orEmpty() +
                        "END",
                    parameters = rangeDopeQuery.parameters.merge(
                        withAttributeKeysDopeQuery?.parameters,
                        transformationDopeQuery.parameters,
                        conditionDopeQuery?.parameters,
                    ),
                )
            }

            is RangeIndexedLike<*, *, *> -> {
                val rangeQ = typeExpression.range.toDopeQuery(resolver)
                val indexVar = typeExpression.indexName ?: resolver.manager.iteratorManager.getIteratorName()
                val iterVar = typeExpression.iteratorName ?: resolver.manager.iteratorManager.getIteratorName()
                val indexIterator = Iterator<NumberType>(indexVar)
                val valueIterator = Iterator<ValidType>(iterVar)

                @Suppress("UNCHECKED_CAST")
                val withAttributeKeysExpression =
                    (typeExpression.withAttributeKeys as ((Iterator<NumberType>, Iterator<ValidType>) -> TypeExpression<StringType>)?)?.invoke(
                        indexIterator,
                        valueIterator,
                    )

                @Suppress("UNCHECKED_CAST")
                val transformationExpression =
                    (typeExpression.transformation as (Iterator<NumberType>, Iterator<ValidType>) -> TypeExpression<ValidType>)(
                        indexIterator,
                        valueIterator,
                    )

                @Suppress("UNCHECKED_CAST")
                val conditionExpression =
                    (typeExpression.condition as ((Iterator<NumberType>, Iterator<ValidType>) -> TypeExpression<BooleanType>)?)?.invoke(
                        indexIterator,
                        valueIterator,
                    )
                val withAttributeKeysDopeQuery = withAttributeKeysExpression?.toDopeQuery(resolver)
                val transformationDopeQuery = transformationExpression.toDopeQuery(resolver)
                val conditionDopeQuery = conditionExpression?.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = typeExpression.transformationType.name + " " +
                        (withAttributeKeysDopeQuery?.let { "${it.queryString}:" } ?: "") +
                        "${transformationDopeQuery.queryString} FOR `$indexVar`:`$iterVar` " +
                        "${typeExpression.membershipType.name} ${rangeQ.queryString} " +
                        (conditionDopeQuery?.let { "WHEN ${it.queryString} " } ?: "") +
                        "END",
                    parameters = rangeQ.parameters.merge(
                        withAttributeKeysDopeQuery?.parameters,
                        transformationDopeQuery.parameters,
                        conditionDopeQuery?.parameters,
                    ),
                )
            }

            else -> throw UnsupportedOperationException("Not supported: $typeExpression")
        }
}
