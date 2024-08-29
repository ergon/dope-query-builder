package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class LikeExpression(
    left: Field<out ValidType>,
    right: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, InfixOperator(left, "LIKE", right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun Field<out ValidType>.isLike(right: TypeExpression<StringType>) = LikeExpression(this, right)

fun Field<out ValidType>.isLike(right: String) = isLike(right.toDopeType())

class NotLikeExpression(
    left: Field<out ValidType>,
    right: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, InfixOperator(left, "NOT LIKE", right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun Field<out ValidType>.isNotLike(right: TypeExpression<StringType>) = NotLikeExpression(this, right)

fun Field<out ValidType>.isNotLike(right: String) = isNotLike(right.toDopeType())
