package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectLetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class LetClause<T : ValidType>(
    private val dopeVariable: DopeVariable<out ValidType>,
    private vararg val dopeVariables: DopeVariable<out ValidType>,
    private val parentClause: ISelectLetClause<T>,
) : ISelectLetClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val dopeVariableQuery = dopeVariable.toDefinitionDopeQuery(manager)
        val dopeVariablesQueries = dopeVariables.map { it.toDefinitionDopeQuery(manager) }

        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                "LET",
                dopeVariableQuery.queryString,
                *dopeVariablesQueries.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentDopeQuery.parameters.merge(
                dopeVariableQuery.parameters,
                *dopeVariablesQueries.map { it.parameters }.toTypedArray(),
            ),
        )
    }
}

class DopeVariable<T : ValidType>(private val name: String, private val value: TypeExpression<T>) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        return DopeQuery(
            queryString = "`$name`",
        )
    }

    fun toDefinitionDopeQuery(manager: DopeQueryManager): DopeQuery {
        val valueDopeQuery = value.toDopeQuery(manager)
        return DopeQuery(
            queryString = "`$name` = ${valueDopeQuery.queryString}",
            parameters = valueDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> String.assignTo(expression: TypeExpression<T>): DopeVariable<T> = DopeVariable(this, expression)

fun String.assignTo(expression: Number): DopeVariable<NumberType> = assignTo(expression.toDopeType())

fun String.assignTo(expression: String): DopeVariable<StringType> = assignTo(expression.toDopeType())

fun String.assignTo(expression: Boolean): DopeVariable<BooleanType> = assignTo(expression.toDopeType())
