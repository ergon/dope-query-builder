package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType

class LessOrEqualThanExpression<T : ComparableType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "<=", right) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ComparableType> TypeExpression<T>.isLessOrEqualThan(right: TypeExpression<T>): LessOrEqualThanExpression<T> =
    LessOrEqualThanExpression(this, right)
