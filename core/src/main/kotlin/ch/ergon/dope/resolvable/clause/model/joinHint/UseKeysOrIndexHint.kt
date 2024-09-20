package ch.ergon.dope.resolvable.clause.model.joinHint

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.model.joinHint.UseKeysHintClass.Companion.UseKeysHint
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.formatListToQueryStringWithBrackets
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.fromable.IndexReference
import ch.ergon.dope.resolvable.fromable.IndexType.USING_FTS
import ch.ergon.dope.resolvable.fromable.IndexType.USING_GSI
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface UseKeysOrIndexHint : Resolvable

class UseKeysHintClass private constructor(
    private val useKeys: TypeExpression<out ValidType>,
) : UseKeysOrIndexHint {
    companion object {
        @JvmName("singleUseKeysHintConstructor")
        fun UseKeysHint(key: TypeExpression<StringType>) =
            UseKeysHintClass(key)

        @JvmName("multipleUseKeysHintConstructor")
        fun UseKeysHint(keys: TypeExpression<ArrayType<StringType>>) =
            UseKeysHintClass(keys)
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val keysDopeQuery = useKeys.toDopeQuery(manager)
        return DopeQuery(
            queryString = "KEYS ${keysDopeQuery.queryString}",
            parameters = keysDopeQuery.parameters,
        )
    }
}

fun useKeys(key: TypeExpression<StringType>) = UseKeysHint(key)

fun useKeys(key: String) = useKeys(key.toDopeType())

@JvmName("useKeysHintArray")
fun useKeys(keys: TypeExpression<ArrayType<StringType>>) = UseKeysHint(keys)

fun useKeys(keys: Collection<TypeExpression<StringType>>) = useKeys(keys.toDopeType())

fun useKeys(firstKey: String, secondKey: String, vararg additionalKeys: String) =
    useKeys(
        listOf(
            firstKey.toDopeType(),
            secondKey.toDopeType(),
            *additionalKeys.map { it.toDopeType() }.toTypedArray(),
        ).toDopeType(),
    )

class UseIndexHint(
    vararg val indexReference: IndexReference,
) : UseKeysOrIndexHint {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val indexReferenceDopeQueries = indexReference.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = formatToQueryString(
                "INDEX",
                formatListToQueryStringWithBrackets(indexReferenceDopeQueries),
            ),
            parameters = indexReferenceDopeQueries.fold(emptyMap()) { indexReferenceParameters, field ->
                indexReferenceParameters + field.parameters
            },
        )
    }
}

fun UseIndexHint.useIndex(indexName: String) = UseIndexHint(*indexReference, IndexReference(indexName))

fun UseIndexHint.useGsiIndex(indexName: String? = null) =
    UseIndexHint(*indexReference, IndexReference(indexName, USING_GSI))

fun UseIndexHint.useFtsIndex(indexName: String? = null) =
    UseIndexHint(*indexReference, IndexReference(indexName, USING_FTS))

fun useIndex(indexName: String? = null) = UseIndexHint(IndexReference(indexName))

fun useGsiIndex(indexName: String? = null) = UseIndexHint(IndexReference(indexName, USING_GSI))

fun useFtsIndex(indexName: String? = null) = UseIndexHint(IndexReference(indexName, USING_FTS))
