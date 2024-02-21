package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsNotValuedExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType>, IsExpression {
    override fun toQueryString(): String = format(field, "IS NOT VALUED")
}

fun Field<out ValidType>.isNotValued() = IsNotValuedExpression(this)
