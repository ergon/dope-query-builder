package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType

data class LikeExpression(
    override val left: TypeExpression<StringType>,
    override val right: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, InfixOperator(left, right)

fun TypeExpression<StringType>.isLike(right: TypeExpression<StringType>) = LikeExpression(this, right)

fun TypeExpression<StringType>.isLike(right: String) = isLike(right.toDopeType())

fun String.isLike(right: TypeExpression<StringType>) = toDopeType().isLike(right)

fun String.isLike(right: String) = toDopeType().isLike(right.toDopeType())

data class NotLikeExpression(
    override val left: TypeExpression<StringType>,
    override val right: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, InfixOperator(left, right)

fun TypeExpression<StringType>.isNotLike(right: TypeExpression<StringType>) = NotLikeExpression(this, right)

fun TypeExpression<StringType>.isNotLike(right: String) = isNotLike(right.toDopeType())

fun String.isNotLike(right: TypeExpression<StringType>) = toDopeType().isNotLike(right)

fun String.isNotLike(right: String) = toDopeType().isNotLike(right.toDopeType())
