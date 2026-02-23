package ch.ergon.dope.couchbase.resolver.expression

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolvable.expression.type.MetaExpression.MetaField
import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryString
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSeparator
import ch.ergon.dope.merge
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.operator.PostfixOperator
import ch.ergon.dope.resolvable.expression.operator.PrefixOperator
import ch.ergon.dope.resolvable.expression.type.ArrayAccess
import ch.ergon.dope.resolvable.expression.type.ArrayPrimitive
import ch.ergon.dope.resolvable.expression.type.BooleanPrimitive
import ch.ergon.dope.resolvable.expression.type.CaseClass
import ch.ergon.dope.resolvable.expression.type.CaseExpression
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.resolvable.expression.type.ElseCaseExpression
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.MISSING
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.ObjectEntry
import ch.ergon.dope.resolvable.expression.type.ObjectEntryPrimitive
import ch.ergon.dope.resolvable.expression.type.ObjectPrimitive
import ch.ergon.dope.resolvable.expression.type.Parameter
import ch.ergon.dope.resolvable.expression.type.SelectExpression
import ch.ergon.dope.resolvable.expression.type.StringPrimitive
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.ExistsExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.collection.SatisfiesExpression
import ch.ergon.dope.resolvable.expression.type.function.array.UnpackExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponentType
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType
import ch.ergon.dope.resolvable.expression.type.function.token.factory.ContainsTokenOptions
import ch.ergon.dope.resolvable.expression.type.function.token.factory.CustomTokenOptions
import ch.ergon.dope.resolvable.expression.type.range.RangeIndexedLike
import ch.ergon.dope.resolvable.expression.type.range.RangeLike
import ch.ergon.dope.resolvable.expression.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotBetweenExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface TypeExpressionResolver : InfixOperatorResolver, FunctionOperatorResolver {
    fun resolve(typeExpression: TypeExpression<*>) =
        when (typeExpression) {
            is FunctionOperator -> resolve(typeExpression)

            is InfixOperator<*> -> resolve(typeExpression)

            is PrefixOperator<*> -> {
                val arg = typeExpression.argument.toDopeQuery(this)
                val symbolWithSeparator = typeExpression.symbolWithSeparator
                CouchbaseDopeQuery(
                    formatToQueryStringWithSeparator(symbolWithSeparator.first, separator = symbolWithSeparator.second, arg.queryString),
                    arg.parameters,
                )
            }

            is PostfixOperator<*> -> {
                val fieldDopeQuery = typeExpression.argument.toDopeQuery(this)
                CouchbaseDopeQuery(
                    formatToQueryString(fieldDopeQuery.queryString, typeExpression.symbol),
                    fieldDopeQuery.parameters,
                )
            }

            is BetweenExpression<*> -> {
                val left = typeExpression.expression.toDopeQuery(this)
                val lower = typeExpression.start.toDopeQuery(this)
                val upper = typeExpression.end.toDopeQuery(this)
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
                val left = typeExpression.expression.toDopeQuery(this)
                val lower = typeExpression.start.toDopeQuery(this)
                val upper = typeExpression.end.toDopeQuery(this)
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

            is ExistsExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = "EXISTS ${arrayDopeQuery.queryString}",
                    parameters = arrayDopeQuery.parameters,
                )
            }

            is SatisfiesExpression<*> -> {
                val array = typeExpression.arrayExpression.toDopeQuery(this)
                val iteratorName = typeExpression.iteratorName ?: manager.iteratorManager.getIteratorName()

                @Suppress("UNCHECKED_CAST")
                val predicateFunc =
                    typeExpression.predicate as (Iterator<ValidType>) -> TypeExpression<BooleanType>
                val predicate = predicateFunc(Iterator(iteratorName)).toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = "${typeExpression.satisfiesType.queryString} `$iteratorName` " +
                        "IN ${array.queryString} SATISFIES ${predicate.queryString} END",
                    parameters = array.parameters.merge(predicate.parameters),
                )
            }

            is Iterator<*> -> CouchbaseDopeQuery("`${typeExpression.variable}`")

            is SelectExpression<*> -> {
                val inner = typeExpression.selectClause.toDopeQuery(this)
                CouchbaseDopeQuery("(${inner.queryString})", inner.parameters)
            }

            is ArrayAccess<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
                val indexDopeQuery = typeExpression.index.toDopeQuery(this)
                CouchbaseDopeQuery(
                    "${arrayDopeQuery.queryString}[${indexDopeQuery.queryString}]",
                    arrayDopeQuery.parameters.merge(indexDopeQuery.parameters),
                )
            }

            is ObjectEntry<*> -> {
                val objectDopeQuery = typeExpression.objectExpression.toDopeQuery(this)
                CouchbaseDopeQuery("${objectDopeQuery.queryString}.`${typeExpression.key}`", objectDopeQuery.parameters)
            }

            is MetaField<*> -> {
                val meta = typeExpression.metaExpression.toDopeQuery(this)
                CouchbaseDopeQuery("${meta.queryString}.`${typeExpression.name}`", meta.parameters)
            }

            is Parameter<*> -> {
                when (val name = typeExpression.parameterName) {
                    null -> {
                        val placeholder = "$" + manager.parameterManager.count
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
                val items = typeExpression.collection.map { it.toDopeQuery(this) }
                CouchbaseDopeQuery(
                    queryString = formatListToQueryStringWithBrackets(items, prefix = "[", postfix = "]"),
                    parameters = items.map { it.parameters }.merge(),
                )
            }

            is DateUnitType -> CouchbaseDopeQuery("\"${typeExpression.name}\"")
            is DateComponentType -> CouchbaseDopeQuery("\"${typeExpression.name}\"")

            is ObjectPrimitive -> {
                val entries = typeExpression.entries.map { it.toDopeQuery(this) }
                CouchbaseDopeQuery(
                    queryString = "{${entries.joinToString(", ") { it.queryString }}}",
                    parameters = entries.map { it.parameters }.merge(),
                )
            }

            is CaseExpression<*, *> -> {
                val case = typeExpression.case.toDopeQuery(this)
                val pairs = listOf(typeExpression.firstSearchResult) + typeExpression.additionalSearchResults
                val expressionDopeQueries =
                    pairs.map { it.searchExpression.toDopeQuery(this) to it.resultExpression.toDopeQuery(this) }
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
                val case = typeExpression.case.toDopeQuery(this)
                val pairs = listOf(typeExpression.firstSearchResult) + typeExpression.additionalSearchResults
                val expressionDopeQueries =
                    pairs.map { it.searchExpression.toDopeQuery(this) to it.resultExpression.toDopeQuery(this) }
                val elseDopeQuery = typeExpression.elseCase.toDopeQuery(this)
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
                val bucket = typeExpression.bucket
                val fieldQuery = when (bucket) {
                    is AliasedBucket -> "`${bucket.alias}`.`${typeExpression.name}`"

                    is UnaliasedBucket -> {
                        val path = when {
                            bucket.scope != null && bucket.scope?.collection != null -> bucket.scope?.collection!!.name
                            bucket.scope != null -> bucket.scope!!.name
                            else -> bucket.name
                        }
                        "`$path`.`${typeExpression.name}`"
                    }

                    else -> "`${typeExpression.name}`"
                }
                CouchbaseDopeQuery(queryString = fieldQuery)
            }

            is UnpackExpression -> {
                val objectDopeQuery = typeExpression.objectArray.toDopeQuery(this)
                CouchbaseDopeQuery("${objectDopeQuery.queryString}[*]", objectDopeQuery.parameters)
            }

            is DopeVariable<*> -> CouchbaseDopeQuery("`${typeExpression.name}`")

            is RangeLike<*, *, *> -> {
                val rangeDopeQuery = typeExpression.range.toDopeQuery(this)
                val iteratorVariable = typeExpression.iteratorName ?: manager.iteratorManager.getIteratorName()
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
                val withAttributeKeysDopeQuery = withKeyExpression?.toDopeQuery(this)
                val transformationDopeQuery = transformationExpression.toDopeQuery(this)
                val conditionDopeQuery = condExpr?.toDopeQuery(this)
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
                val rangeQ = typeExpression.range.toDopeQuery(this)
                val indexVar = typeExpression.indexName ?: manager.iteratorManager.getIteratorName()
                val iterVar = typeExpression.iteratorName ?: manager.iteratorManager.getIteratorName()
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
                val withAttributeKeysDopeQuery = withAttributeKeysExpression?.toDopeQuery(this)
                val transformationDopeQuery = transformationExpression.toDopeQuery(this)
                val conditionDopeQuery = conditionExpression?.toDopeQuery(this)
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

    fun resolve(objectEntryPrimitiv: ObjectEntryPrimitive<*>): CouchbaseDopeQuery {
        val key = objectEntryPrimitiv.key.toDopeQuery(this)
        val value = objectEntryPrimitiv.value.toDopeQuery(this)
        return CouchbaseDopeQuery(
            queryString = "${key.queryString} : ${value.queryString}",
            parameters = key.parameters.merge(value.parameters),
        )
    }

    fun resolve(caseClass: CaseClass<*>): CouchbaseDopeQuery {
        val casePart = caseClass.case?.toDopeQuery(this)
        return CouchbaseDopeQuery(
            queryString = "CASE" + (casePart?.queryString?.let { " $it" } ?: ""),
            parameters = casePart?.parameters.orEmpty(),
        )
    }

    fun resolve(customTokenOptions: CustomTokenOptions): CouchbaseDopeQuery {
        val options = listOfNotNull(
            customTokenOptions.name?.let { "name" to it },
            customTokenOptions.case?.let { "case" to "\"${it.queryString}\"" },
            customTokenOptions.specials?.let { "specials" to it },
        )
        val queryString = options
            .joinToString(", ", "{", "}") { (key, value) -> "\"$key\": $value" }
            .takeIf { options.isNotEmpty() }
            .orEmpty()

        return CouchbaseDopeQuery(queryString = queryString)
    }

    fun resolve(containsTokenOptions: ContainsTokenOptions): CouchbaseDopeQuery {
        val options = listOfNotNull(
            containsTokenOptions.names?.let { "names" to it },
            containsTokenOptions.case?.let { "case" to "\"${it.queryString}\"" },
            containsTokenOptions.specials?.let { "specials" to it },
            containsTokenOptions.split?.let { "split" to it },
            containsTokenOptions.trim?.let { "trim" to it },
        )
        val queryString = options
            .joinToString(", ", "{", "}") { (key, value) -> "\"$key\": $value" }
            .takeIf { options.isNotEmpty() }
            .orEmpty()

        return CouchbaseDopeQuery(queryString = queryString)
    }
}
