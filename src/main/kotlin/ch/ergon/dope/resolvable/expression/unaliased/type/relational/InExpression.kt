package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class InExpression(
    value: TypeExpression<out ValidType>,
    collection: TypeExpression<ArrayType>,
) : TypeExpression<BooleanType>, InfixOperator(value, "IN", collection) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ValidType> TypeExpression<T>.inArray(array: TypeExpression<ArrayType>): InExpression =
    InExpression(this, array)
