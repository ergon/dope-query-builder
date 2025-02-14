package ch.ergon.dope.resolvable.expression.single.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.util.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class GreaterOrEqualThanExpression<T : ComparableType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, ">=", right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

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
