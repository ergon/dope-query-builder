package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class NotLikeExpression(
    left: Field<out ValidType>,
    right: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, InfixOperator(left, "NOT LIKE", right) {
    override fun toDopeQuery(): DopeQuery = toInfixDopeQuery()
}

fun Field<out ValidType>.isNotLike(right: TypeExpression<StringType>): NotLikeExpression = NotLikeExpression(this, right)

fun Field<out ValidType>.isNotLike(right: String): NotLikeExpression = this.isNotLike(right.toStringType())
