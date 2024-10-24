package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType

class LikeExpression(
    left: TypeExpression<StringType>,
    right: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, InfixOperator(left, "LIKE", right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun TypeExpression<StringType>.isLike(right: TypeExpression<StringType>) = LikeExpression(this, right)

fun TypeExpression<StringType>.isLike(right: String) = isLike(right.toDopeType())

fun String.isLike(right: TypeExpression<StringType>) = toDopeType().isLike(right)

fun String.isLike(right: String) = toDopeType().isLike(right.toDopeType())

class NotLikeExpression(
    left: TypeExpression<StringType>,
    right: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, InfixOperator(left, "NOT LIKE", right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun TypeExpression<StringType>.isNotLike(right: TypeExpression<StringType>) = NotLikeExpression(this, right)

fun TypeExpression<StringType>.isNotLike(right: String) = isNotLike(right.toDopeType())

fun String.isNotLike(right: TypeExpression<StringType>) = toDopeType().isNotLike(right)

fun String.isNotLike(right: String) = toDopeType().isNotLike(right.toDopeType())
