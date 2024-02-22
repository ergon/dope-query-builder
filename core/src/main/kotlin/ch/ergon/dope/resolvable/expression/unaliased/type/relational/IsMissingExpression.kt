package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsMissingExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType>, IsExpression {
    override fun toQueryString(): String = format(field, "IS MISSING")
}

fun Field<out ValidType>.isMissing() = IsMissingExpression(this)
