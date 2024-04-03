package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class GreaterOrEqualThanExpression<T : ComparableType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, ">=", right) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ComparableType> TypeExpression<T>.isGreaterOrEqualThan(right: TypeExpression<T>): GreaterOrEqualThanExpression<T> =
    GreaterOrEqualThanExpression(this, right)

fun TypeExpression<NumberType>.isGreaterOrEqualThan(right: Number): GreaterOrEqualThanExpression<NumberType> =
    isGreaterOrEqualThan(right.toNumberType())

fun Number.isGreaterOrEqualThan(right: TypeExpression<NumberType>): GreaterOrEqualThanExpression<NumberType> =
    toNumberType().isGreaterOrEqualThan(right)

fun Number.isGreaterOrEqualThan(right: Number): GreaterOrEqualThanExpression<NumberType> =
    toNumberType().isGreaterOrEqualThan(right.toNumberType())

fun TypeExpression<StringType>.isGreaterOrEqualThan(right: String): GreaterOrEqualThanExpression<StringType> =
    isGreaterOrEqualThan(right.toStringType())

fun String.isGreaterOrEqualThan(right: TypeExpression<StringType>): GreaterOrEqualThanExpression<StringType> =
    toStringType().isGreaterOrEqualThan(right)

fun String.isGreaterOrEqualThan(right: String): GreaterOrEqualThanExpression<StringType> =
    toStringType().isGreaterOrEqualThan(right.toStringType())
