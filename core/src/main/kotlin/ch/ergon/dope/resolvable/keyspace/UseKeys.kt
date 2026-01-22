package ch.ergon.dope.resolvable.keyspace

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Updatable
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.resolvable.keyspace.UseKeysClass.Companion.UseKeys
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class UseKeysClass private constructor(
    val useKeys: TypeExpression<out ValidType>,
    val keyspace: Keyspace,
) : Joinable, Deletable, Updatable, Fromable {
    companion object {
        @JvmName("singleUseKeysClauseConstructor")
        fun UseKeys(key: TypeExpression<StringType>, keyspace: Keyspace) =
            UseKeysClass(key, keyspace)

        @JvmName("multipleUseKeysClauseConstructor")
        fun UseKeys(keys: TypeExpression<ArrayType<StringType>>, keyspace: Keyspace) =
            UseKeysClass(keys, keyspace)
    }
}

fun Keyspace.useKeys(key: TypeExpression<StringType>) = UseKeys(key, this)

fun Keyspace.useKeys(key: String) = useKeys(key.toDopeType())

@JvmName("useKeysArray")
fun Keyspace.useKeys(keys: TypeExpression<ArrayType<StringType>>) = UseKeys(keys, this)

fun Keyspace.useKeys(keys: ISelectOffsetClause<StringType>) = UseKeys(keys.asExpression(), this)

fun Keyspace.useKeys(keys: Collection<TypeExpression<StringType>>) = useKeys(keys.toDopeType())

@JvmName("useKeysStringCollection")
fun Keyspace.useKeys(keys: Collection<String>) = useKeys(keys.toDopeType())

fun Keyspace.useKeys(firstKey: String, secondKey: String, vararg additionalKeys: String) =
    useKeys(
        listOf(
            firstKey.toDopeType(),
            secondKey.toDopeType(),
            *additionalKeys.map { it.toDopeType() }.toTypedArray(),
        ).toDopeType(),
    )
