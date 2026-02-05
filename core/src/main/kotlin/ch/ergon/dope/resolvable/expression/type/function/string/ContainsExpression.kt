package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType

data class ContainsExpression(
    val inStr: TypeExpression<StringType>,
    val searchStr: TypeExpression<StringType>,
) : FunctionExpression<BooleanType>(listOf(inStr, searchStr))

fun TypeExpression<StringType>.contains(searchStr: TypeExpression<StringType>) =
    ContainsExpression(this, searchStr)

fun TypeExpression<StringType>.contains(searchStr: String) = contains(searchStr.toDopeType())

fun String.contains(searchStr: TypeExpression<StringType>) = toDopeType().contains(searchStr)
