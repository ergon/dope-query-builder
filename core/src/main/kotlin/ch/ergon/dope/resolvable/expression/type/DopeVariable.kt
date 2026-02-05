package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class DopeVariable<T : ValidType>(val name: String, val value: TypeExpression<T>) : TypeExpression<T>

fun <T : ValidType> String.assignTo(expression: TypeExpression<T>): DopeVariable<T> = DopeVariable(this, expression)

fun String.assignTo(expression: Number): DopeVariable<NumberType> = assignTo(expression.toDopeType())

fun String.assignTo(expression: String): DopeVariable<StringType> = assignTo(expression.toDopeType())

fun String.assignTo(expression: Boolean): DopeVariable<BooleanType> = assignTo(expression.toDopeType())

fun <T : ValidType> String.assignTo(selectClause: ISelectOffsetClause<T>) = assignTo(selectClause.asExpression())
