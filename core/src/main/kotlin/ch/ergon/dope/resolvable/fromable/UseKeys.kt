package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.fromable.UseKeysClass.Companion.UseKeys
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private const val USE_KEYS = "USE KEYS"

class UseKeysClass private constructor(
    private val useKeys: TypeExpression<out ValidType>,
    private val bucket: Bucket,
) : Joinable, Deletable, Updatable, Fromable {
    companion object {
        @JvmName("singleUseKeysClauseConstructor")
        fun UseKeys(key: TypeExpression<StringType>, bucket: Bucket) =
            UseKeysClass(key, bucket)

        @JvmName("multipleUseKeysClauseConstructor")
        fun UseKeys(keys: TypeExpression<ArrayType<StringType>>, bucket: Bucket) =
            UseKeysClass(keys, bucket)
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val bucketDopeQuery = when (bucket) {
            is AliasedBucket -> bucket.asBucketDefinition().toDopeQuery(manager)
            else -> bucket.toDopeQuery(manager)
        }
        val keysDopeQuery = when (useKeys) {
            is ISelectOffsetClause<*> -> useKeys.asSelectWithParentheses().toDopeQuery(manager)
            else -> useKeys.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(bucketDopeQuery.queryString, USE_KEYS, keysDopeQuery.queryString),
            parameters = bucketDopeQuery.parameters + keysDopeQuery.parameters,
        )
    }
}

fun Bucket.useKeys(key: TypeExpression<StringType>) = UseKeys(key, this)

fun Bucket.useKeys(key: String) = useKeys(key.toDopeType())

@JvmName("useKeysArray")
fun Bucket.useKeys(keys: TypeExpression<ArrayType<StringType>>) = UseKeys(keys, this)

fun Bucket.useKeys(keys: Collection<TypeExpression<StringType>>) = useKeys(keys.toDopeType())

fun Bucket.useKeys(firstKey: String, secondKey: String, vararg additionalKeys: String) =
    useKeys(listOf(firstKey.toDopeType(), secondKey.toDopeType(), *additionalKeys.map { it.toDopeType() }.toTypedArray()).toDopeType())
