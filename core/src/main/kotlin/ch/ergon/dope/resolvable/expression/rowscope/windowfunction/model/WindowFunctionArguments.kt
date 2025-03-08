package ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

class WindowFunctionArguments(
    private val firstArg: TypeExpression<out ValidType>? = null,
    private val secondArg: TypeExpression<out ValidType>? = null,
    private val thirdArg: TypeExpression<out ValidType>? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val firstArgDopeQuery = firstArg?.toDopeQuery(manager)
        val secondArgDopeQuery = secondArg?.toDopeQuery(manager)
        val thirdArgDopeQuery = thirdArg?.toDopeQuery(manager)
        return DopeQuery(
            queryString = buildString {
                if (firstArgDopeQuery != null) {
                    append(firstArgDopeQuery.queryString)
                }
                if (secondArgDopeQuery != null) {
                    if (isNotEmpty()) append(", ")
                    append(secondArgDopeQuery.queryString)
                }
                if (thirdArgDopeQuery != null) {
                    if (isNotEmpty()) append(", ")
                    append(thirdArgDopeQuery.queryString)
                }
            },
            parameters = (firstArgDopeQuery?.parameters ?: DopeParameters()).merge(
                secondArgDopeQuery?.parameters,
                thirdArgDopeQuery?.parameters,
            ),
        )
    }
}
