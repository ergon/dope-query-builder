package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class DopeVariable<T : ValidType>(private val name: String, private val value: TypeExpression<T>) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        return DopeQuery(
            queryString = "`$name`",
        )
    }

    fun toLetDefinitionDopeQuery(manager: DopeQueryManager): DopeQuery {
        val valueDopeQuery = value.toDopeQuery(manager)
        return DopeQuery(
            queryString = "`$name` = ${valueDopeQuery.queryString}",
            parameters = valueDopeQuery.parameters,
        )
    }

    fun toWithDefinitionDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = value.toDopeQuery(manager)
        return DopeQuery(
            queryString = "`$name` AS (${expressionDopeQuery.queryString})",
            parameters = expressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> String.assignTo(expression: TypeExpression<T>): DopeVariable<T> = DopeVariable(this, expression)

fun String.assignTo(expression: Number): DopeVariable<NumberType> = assignTo(expression.toDopeType())

fun String.assignTo(expression: String): DopeVariable<StringType> = assignTo(expression.toDopeType())

fun String.assignTo(expression: Boolean): DopeVariable<BooleanType> = assignTo(expression.toDopeType())

fun <T : ValidType> String.assignTo(selectClause: ISelectOffsetClause<T>) = assignTo(selectClause.asExpression())
