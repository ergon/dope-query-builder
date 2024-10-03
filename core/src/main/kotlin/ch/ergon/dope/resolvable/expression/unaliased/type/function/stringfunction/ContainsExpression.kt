package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType

class ContainsExpression(
    inStr: TypeExpression<StringType>,
    searchStr: TypeExpression<StringType>,
) : FunctionExpression<BooleanType>("CONTAINS", inStr, searchStr)

fun contains(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    ContainsExpression(inStr, searchStr)

fun contains(inStr: String, searchStr: String) = contains(inStr.toDopeType(), searchStr.toDopeType())

fun contains(inStr: TypeExpression<StringType>, searchStr: String) = contains(inStr, searchStr.toDopeType())

fun contains(inStr: String, searchStr: TypeExpression<StringType>) = contains(inStr.toDopeType(), searchStr)
