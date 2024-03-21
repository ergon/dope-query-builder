package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class LessThanExpression<T : ComparableType>(
    left: TypeExpression<out T>,
    right: TypeExpression<out T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "<", right) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ComparableType> TypeExpression<out T>.isLessThan(right: TypeExpression<out T>): LessThanExpression<T> =
    LessThanExpression(this, right)

fun TypeExpression<NumberType>.isLessThan(right: Number): LessThanExpression<ComparableType> =
    isLessThan(right.toNumberType())

fun Number.isLessThan(right: TypeExpression<NumberType>): LessThanExpression<ComparableType> =
    this.toNumberType().isLessThan(right)

fun Number.isLessThan(right: Number): LessThanExpression<ComparableType> =
    this.toNumberType().isLessThan(right.toNumberType())

fun TypeExpression<StringType>.isLessThan(right: String): LessThanExpression<ComparableType> =
    isLessThan(right.toStringType())

fun String.isLessThan(right: TypeExpression<StringType>): LessThanExpression<ComparableType> =
    this.toStringType().isLessThan(right)

fun String.isLessThan(right: String): LessThanExpression<ComparableType> =
    this.toStringType().isLessThan(right.toStringType())
