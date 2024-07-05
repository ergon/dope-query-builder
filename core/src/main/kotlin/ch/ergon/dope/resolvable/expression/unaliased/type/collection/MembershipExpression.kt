package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

sealed class MembershipExpression<T : ValidType>(
    value: TypeExpression<T>,
    symbol: String,
    collection: TypeExpression<ArrayType<T>>,
) : TypeExpression<BooleanType>, InfixOperator(value, symbol, collection) {
    override fun toDopeQuery() = toInfixDopeQuery()
}
