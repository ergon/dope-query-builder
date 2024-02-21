package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class EqualsExpression<T : ValidType>(
    val left: TypeExpression<T>,
    val right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "=", right) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ValidType> TypeExpression<T>.isEqualTo(right: TypeExpression<T>): EqualsExpression<T> =
    EqualsExpression(this, right)
