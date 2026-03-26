package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

data class TokensExpression(
    val inString: TypeExpression<ArrayType<StringType>>,
    val options: CustomTokenOptions? = null,
) : FunctionOperator<ArrayType<StringType>>

fun TypeExpression<ArrayType<StringType>>.tokens(options: CustomTokenOptions? = null) = TokensExpression(this, options)

fun List<String>.tokens(options: CustomTokenOptions? = null) = toDopeType().tokens(options)
