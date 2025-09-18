package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class AliasedTypeExpression<T : ValidType>(
    val typeExpression: TypeExpression<T>,
    val alias: String,
) : SingleExpression<T>

fun <T : ValidType> TypeExpression<T>.alias(alias: String): AliasedTypeExpression<T> =
    AliasedTypeExpression(this, alias)

fun Number.alias(alias: String): AliasedTypeExpression<NumberType> = toDopeType().alias(alias)

fun String.alias(alias: String): AliasedTypeExpression<StringType> = toDopeType().alias(alias)

fun Boolean.alias(alias: String): AliasedTypeExpression<BooleanType> = toDopeType().alias(alias)

fun <V> Map<String, V>.alias(alias: String): AliasedTypeExpression<ObjectType> = toDopeType().alias(alias)
