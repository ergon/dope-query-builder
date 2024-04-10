package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsNullExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType> {
    override fun toQueryString(): String = formatToQueryString(field, "IS NULL")
}

fun Field<out ValidType>.isNull() = IsNullExpression(this)
