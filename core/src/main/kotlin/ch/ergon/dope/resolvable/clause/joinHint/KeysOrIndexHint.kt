package ch.ergon.dope.resolvable.clause.joinHint

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.IndexReference
import ch.ergon.dope.resolvable.bucket.IndexType.USING_FTS
import ch.ergon.dope.resolvable.bucket.IndexType.USING_GSI
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.clause.joinHint.KeysHintClass.Companion.KeysHint
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed interface KeysOrIndexHint : Resolvable

data class KeysHintClass private constructor(
    val keys: TypeExpression<out ValidType>,
) : KeysOrIndexHint {
    companion object {
        @JvmName("singleKeyHintConstructor")
        fun KeysHint(key: TypeExpression<StringType>) =
            KeysHintClass(key)

        @JvmName("multipleKeysHintConstructor")
        fun KeysHint(keys: TypeExpression<ArrayType<StringType>>) =
            KeysHintClass(keys)
    }
}

fun keysHint(key: TypeExpression<StringType>) = KeysHint(key)

fun keysHint(key: String) = keysHint(key.toDopeType())

@JvmName("keysHintArray")
fun keysHint(keys: TypeExpression<ArrayType<StringType>>) = KeysHint(keys)

fun keysHint(keys: ISelectOffsetClause<StringType>) = KeysHint(keys.asExpression())

fun keysHint(keys: Collection<TypeExpression<StringType>>) = keysHint(keys.toDopeType())

fun keysHint(key: String, vararg additionalKeys: String) =
    keysHint(listOf(key.toDopeType(), *additionalKeys.map { it.toDopeType() }.toTypedArray()).toDopeType())

data class IndexHint(
    val indexReferences: List<IndexReference> = emptyList(),
) : KeysOrIndexHint

fun IndexHint.indexHint(indexName: String) = IndexHint(indexReferences + IndexReference(indexName))

fun IndexHint.gsiIndexHint(indexName: String? = null) =
    IndexHint(indexReferences + IndexReference(indexName, USING_GSI))

fun IndexHint.ftsIndexHint(indexName: String? = null) =
    IndexHint(indexReferences + IndexReference(indexName, USING_FTS))

fun indexHint(indexName: String? = null) = IndexHint(listOf(IndexReference(indexName)))

fun gsiIndexHint(indexName: String? = null) = IndexHint(listOf(IndexReference(indexName, USING_GSI)))

fun ftsIndexHint(indexName: String? = null) = IndexHint(listOf(IndexReference(indexName, USING_FTS)))
