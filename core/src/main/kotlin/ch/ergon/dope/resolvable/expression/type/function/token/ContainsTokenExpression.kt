package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType

data class ContainsTokenExpression(
    val inputExpression: TypeExpression<StringType>,
    val tokenExpression: TypeExpression<StringType>,
    val options: ContainsTokenOptions? = null,
) : FunctionOperator<BooleanType>

fun TypeExpression<StringType>.containsToken(
    tokenExpression: TypeExpression<StringType>,
    options: ContainsTokenOptions? = null,
) = ContainsTokenExpression(this, tokenExpression, options)

fun TypeExpression<StringType>.containsToken(
    token: String,
    options: ContainsTokenOptions? = null,
) = containsToken(token.toDopeType(), options)
