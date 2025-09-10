package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class TypeOfExpression<T : ValidType>(val expression: TypeExpression<T>) :
    FunctionExpression<StringType>("TYPE", listOf(expression))

fun <T : ValidType> typeOf(expression: TypeExpression<T>) = TypeOfExpression(expression)
