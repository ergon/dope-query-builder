package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType

data class BetweenExpression<T : ComparableType>(
    val expression: TypeExpression<T>,
    val start: TypeExpression<T>,
    val end: TypeExpression<T>,
) : TypeExpression<BooleanType>

fun <T : ComparableType> TypeExpression<T>.between(start: TypeExpression<T>, end: TypeExpression<T>) =
    BetweenExpression(this, start, end)

data class NotBetweenExpression<T : ComparableType>(
    val expression: TypeExpression<T>,
    val start: TypeExpression<T>,
    val end: TypeExpression<T>,
) : TypeExpression<BooleanType>

fun <T : ComparableType> TypeExpression<T>.notBetween(start: TypeExpression<T>, end: TypeExpression<T>) =
    NotBetweenExpression(this, start, end)
