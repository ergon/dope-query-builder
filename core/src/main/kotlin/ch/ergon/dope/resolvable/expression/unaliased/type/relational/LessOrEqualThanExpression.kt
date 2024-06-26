package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class LessOrEqualThanExpression<T : ComparableType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "<=", right) {
    override fun toDopeQuery(): DopeQuery = toInfixDopeQuery()
}

fun <T : ComparableType> TypeExpression<T>.isLessOrEqualThan(right: TypeExpression<T>): LessOrEqualThanExpression<T> =
    LessOrEqualThanExpression(this, right)

fun TypeExpression<NumberType>.isLessOrEqualThan(right: Number): LessOrEqualThanExpression<NumberType> =
    isLessOrEqualThan(right.toDopeType())

fun Number.isLessOrEqualThan(right: TypeExpression<NumberType>): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right)

fun TypeExpression<StringType>.isLessOrEqualThan(right: String): LessOrEqualThanExpression<StringType> =
    isLessOrEqualThan(right.toDopeType())

fun String.isLessOrEqualThan(right: TypeExpression<StringType>): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right)
