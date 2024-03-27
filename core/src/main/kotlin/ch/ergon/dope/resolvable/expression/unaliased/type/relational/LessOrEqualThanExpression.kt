package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class LessOrEqualThanExpression<T : ComparableType>(
    left: TypeExpression<out T>,
    right: TypeExpression<out T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "<=", right) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ComparableType> TypeExpression<out T>.isLessOrEqualThan(right: TypeExpression<out T>): LessOrEqualThanExpression<T> =
    LessOrEqualThanExpression(this, right)

fun <T : NumberType> TypeExpression<T>.isLessOrEqualThan(right: Number): LessOrEqualThanExpression<ComparableType> =
    isLessOrEqualThan(right.toNumberType())

fun <T : NumberType> Number.isLessOrEqualThan(right: TypeExpression<T>): LessOrEqualThanExpression<ComparableType> =
    this.toNumberType().isLessOrEqualThan(right)

fun <T : StringType> TypeExpression<T>.isLessOrEqualThan(right: String): LessOrEqualThanExpression<ComparableType> =
    isLessOrEqualThan(right.toStringType())

fun <T : StringType> String.isLessOrEqualThan(right: TypeExpression<T>): LessOrEqualThanExpression<ComparableType> =
    this.toStringType().isLessOrEqualThan(right)
