package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class GreaterThanExpression<T : ComparableType>(
    left: TypeExpression<out T>,
    right: TypeExpression<out T>,
) : TypeExpression<BooleanType>, InfixOperator(left, ">", right) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ComparableType> TypeExpression<out T>.isGreaterThan(right: TypeExpression<out T>): GreaterThanExpression<T> =
    GreaterThanExpression(this, right)

fun TypeExpression<NumberType>.isGreaterThan(right: Number): GreaterThanExpression<ComparableType> =
    isGreaterThan(right.toNumberType())

fun Number.isGreaterThan(right: TypeExpression<NumberType>): GreaterThanExpression<ComparableType> =
    this.toNumberType().isGreaterThan(right)

fun Number.isGreaterThan(right: Number): GreaterThanExpression<ComparableType> =
    this.toNumberType().isGreaterThan(right.toNumberType())

fun TypeExpression<StringType>.isGreaterThan(right: String): GreaterThanExpression<ComparableType> =
    isGreaterThan(right.toStringType())

fun String.isGreaterThan(right: TypeExpression<StringType>): GreaterThanExpression<ComparableType> =
    this.toStringType().isGreaterThan(right)

fun String.isGreaterThan(right: String): GreaterThanExpression<ComparableType> =
    this.toStringType().isGreaterThan(right.toStringType())
