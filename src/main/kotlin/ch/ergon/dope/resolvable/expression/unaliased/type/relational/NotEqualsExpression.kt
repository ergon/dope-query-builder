package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class NotEqualsExpression<T : ValidType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "!=", right) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ValidType> TypeExpression<T>.isNotEqualTo(right: TypeExpression<T>): NotEqualsExpression<T> =
    NotEqualsExpression(this, right)
