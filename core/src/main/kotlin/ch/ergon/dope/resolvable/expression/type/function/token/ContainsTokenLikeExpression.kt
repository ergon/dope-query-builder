package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.token.factory.ContainsTokenOptions
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ContainsTokenLikeExpression(
    val inputObject: TypeExpression<out ValidType>,
    val tokenExpression: TypeExpression<StringType>,
    val options: ContainsTokenOptions? = null,
) : FunctionOperator<BooleanType>

fun TypeExpression<out ValidType>.containsTokenLike(
    tokenExpression: TypeExpression<StringType>,
    options: ContainsTokenOptions? = null,
) = ContainsTokenLikeExpression(this, tokenExpression, options)

fun TypeExpression<out ValidType>.containsTokenLike(
    tokenExpression: String,
    options: ContainsTokenOptions? = null,
) = containsTokenLike(tokenExpression.toDopeType(), options)
