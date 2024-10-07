package ch.ergon.dope.resolvable.clause.model.joinHint

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.model.joinHint.KeysHintClass.Companion.KeysHint
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

interface KeysOrIndexHint : Resolvable

class KeysHintClass private constructor(
    private val keys: TypeExpression<out ValidType>,
) : KeysOrIndexHint {
    companion object {
        @JvmName("singleKeyHintConstructor")
        fun KeysHint(key: TypeExpression<StringType>) =
            KeysHintClass(key)

        @JvmName("multipleKeysHintConstructor")
        fun KeysHint(keys: TypeExpression<ArrayType<StringType>>) =
            KeysHintClass(keys)
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val keysDopeQuery = keys.toDopeQuery(manager)
        return DopeQuery(
            queryString = "KEYS ${keysDopeQuery.queryString}",
            parameters = keysDopeQuery.parameters,
        )
    }
}

fun keysHint(key: TypeExpression<StringType>) = KeysHint(key)

fun keysHint(key: String) = keysHint(key.toDopeType())

@JvmName("keysHintArray")
fun keysHint(keys: TypeExpression<ArrayType<StringType>>) = KeysHint(keys)

fun keysHint(keys: Collection<TypeExpression<StringType>>) = keysHint(keys.toDopeType())

fun keysHint(key: String, vararg additionalKeys: String) =
    keysHint(listOf(key.toDopeType(), *additionalKeys.map { it.toDopeType() }.toTypedArray()).toDopeType())

class IndexHint(
    vararg val indexReference: IndexReference,
) : KeysOrIndexHint {
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

fun IndexHint.indexHint(indexName: String) = IndexHint(*indexReference, IndexReference(indexName))

fun IndexHint.gsiIndexHint(indexName: String? = null) =
    IndexHint(*indexReference, IndexReference(indexName, USING_GSI))

fun IndexHint.ftsIndexHint(indexName: String? = null) =
    IndexHint(*indexReference, IndexReference(indexName, USING_FTS))

fun indexHint(indexName: String? = null) = IndexHint(IndexReference(indexName))

fun gsiIndexHint(indexName: String? = null) = IndexHint(IndexReference(indexName, USING_GSI))

fun ftsIndexHint(indexName: String? = null) = IndexHint(IndexReference(indexName, USING_FTS))
