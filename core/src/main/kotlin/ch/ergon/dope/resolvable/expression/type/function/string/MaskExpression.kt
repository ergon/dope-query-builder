package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class MaskExpression(
    private val inStr: TypeExpression<StringType>,
    private val options: Map<String, String> = mapOf(),
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
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

fun mask(inStr: TypeExpression<StringType>, options: Map<String, String> = mapOf()) =
    MaskExpression(inStr, options)

fun mask(inStr: String, options: Map<String, String> = mapOf()) = mask(inStr.toDopeType(), options)
