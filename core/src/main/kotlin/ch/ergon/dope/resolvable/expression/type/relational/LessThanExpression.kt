package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class LessThanExpression<T : ComparableType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "<", right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun <T : ComparableType> TypeExpression<T>.isLessThan(right: TypeExpression<T>): LessThanExpression<T> =
    LessThanExpression(this, right)

fun TypeExpression<NumberType>.isLessThan(right: Number): LessThanExpression<NumberType> =
    isLessThan(right.toDopeType())

fun Number.isLessThan(right: TypeExpression<NumberType>): LessThanExpression<NumberType> =
    this.toDopeType().isLessThan(right)

fun Number.isLessThan(right: Number): LessThanExpression<NumberType> =
    this.toDopeType().isLessThan(right.toDopeType())

fun TypeExpression<StringType>.isLessThan(right: String): LessThanExpression<StringType> =
    isLessThan(right.toDopeType())

fun String.isLessThan(right: TypeExpression<StringType>): LessThanExpression<StringType> =
    this.toDopeType().isLessThan(right)

fun String.isLessThan(right: String): LessThanExpression<StringType> =
    this.toDopeType().isLessThan(right.toDopeType())
