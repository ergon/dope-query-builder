package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class MaskExpression(
    val inStr: TypeExpression<StringType>,
    val options: Map<String, String> = mapOf(),
) : FunctionOperator<StringType>

fun mask(inStr: TypeExpression<StringType>, options: Map<String, String> = mapOf()) =
    MaskExpression(inStr, options)

fun mask(inStr: String, options: Map<String, String> = mapOf()) = mask(inStr.toDopeType(), options)
