package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class LessOrEqualThanExpression<T : ComparableType>(
    override val left: TypeExpression<T>,
    override val right: TypeExpression<T>,
) : InfixOperator<BooleanType>(left, right)

fun <T : ComparableType> TypeExpression<T>.isLessOrEqualThan(right: TypeExpression<T>): LessOrEqualThanExpression<T> =
    LessOrEqualThanExpression(this, right)

fun TypeExpression<NumberType>.isLessOrEqualThan(right: Number): LessOrEqualThanExpression<NumberType> =
    isLessOrEqualThan(right.toDopeType())

fun Number.isLessOrEqualThan(right: TypeExpression<NumberType>): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right)

fun Number.isLessOrEqualThan(right: Number): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

fun TypeExpression<StringType>.isLessOrEqualThan(right: String): LessOrEqualThanExpression<StringType> =
    isLessOrEqualThan(right.toDopeType())

fun String.isLessOrEqualThan(right: TypeExpression<StringType>): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right)

fun String.isLessOrEqualThan(right: String): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())
