package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class GreaterOrEqualThanExpression<T : ComparableType>(
    override val left: TypeExpression<T>,
    override val right: TypeExpression<T>,
) : InfixOperator<BooleanType>(left, right)

fun <T : ComparableType> TypeExpression<T>.isGreaterOrEqualThan(right: TypeExpression<T>): GreaterOrEqualThanExpression<T> =
    GreaterOrEqualThanExpression(this, right)

fun TypeExpression<NumberType>.isGreaterOrEqualThan(right: Number): GreaterOrEqualThanExpression<NumberType> =
    isGreaterOrEqualThan(right.toDopeType())

fun Number.isGreaterOrEqualThan(right: TypeExpression<NumberType>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right)

fun Number.isGreaterOrEqualThan(right: Number): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

fun TypeExpression<StringType>.isGreaterOrEqualThan(right: String): GreaterOrEqualThanExpression<StringType> =
    isGreaterOrEqualThan(right.toDopeType())

fun String.isGreaterOrEqualThan(right: TypeExpression<StringType>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right)

fun String.isGreaterOrEqualThan(right: String): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())
