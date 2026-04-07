package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ContainsTokenRegexpExpression(
    val inputObject: TypeExpression<out ValidType>,
    val regexExpression: TypeExpression<StringType>,
    val tokenOptions: ContainsTokenOptions? = null,
) : FunctionOperator<BooleanType>

fun TypeExpression<out ValidType>.containsTokenRegexp(
    regexExpression: TypeExpression<StringType>,
    tokenOptions: ContainsTokenOptions? = null,
) = ContainsTokenRegexpExpression(this, regexExpression, tokenOptions)

fun TypeExpression<out ValidType>.containsTokenRegexp(
    regexExpression: String,
    tokenOptions: ContainsTokenOptions? = null,
) = containsTokenRegexp(regexExpression.toDopeType(), tokenOptions)
