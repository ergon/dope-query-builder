package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class MaskExpression(
    private val inStr: TypeExpression<StringType>,
    private val options: Map<String, String> = mapOf(),
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(
                "MASK",
                inStrDopeQuery.queryString,
                "{${options.map { "\"${it.key}\": \"${it.value}\"" }.joinToString(", ")}}",
            ),
            parameters = inStrDopeQuery.parameters,
        )
    }
}

fun mask(inStr: TypeExpression<StringType>, options: Map<String, String> = mapOf()): MaskExpression =
    MaskExpression(inStr, options)

fun mask(inStr: String, options: Map<String, String> = mapOf()): MaskExpression = mask(inStr.toDopeType(), options)
