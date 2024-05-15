package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class GreaterThanExpression<T : ComparableType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, ">", right) {
    override fun toDopeQuery(): DopeQuery = toInfixDopeQuery()
}

fun <T : ComparableType> TypeExpression<T>.isGreaterThan(right: TypeExpression<T>): GreaterThanExpression<T> =
    GreaterThanExpression(this, right)

fun TypeExpression<NumberType>.isGreaterThan(right: Number): GreaterThanExpression<NumberType> =
    isGreaterThan(right.toNumberType())

fun Number.isGreaterThan(right: TypeExpression<NumberType>): GreaterThanExpression<NumberType> =
    this.toNumberType().isGreaterThan(right)

fun Number.isGreaterThan(right: Number): GreaterThanExpression<NumberType> =
    this.toNumberType().isGreaterThan(right.toNumberType())

fun TypeExpression<StringType>.isGreaterThan(right: String): GreaterThanExpression<StringType> =
    isGreaterThan(right.toStringType())

fun String.isGreaterThan(right: TypeExpression<StringType>): GreaterThanExpression<StringType> =
    this.toStringType().isGreaterThan(right)

fun String.isGreaterThan(right: String): GreaterThanExpression<StringType> =
    this.toStringType().isGreaterThan(right.toStringType())
