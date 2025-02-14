package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

class SplitExpression(inStr: TypeExpression<StringType>, inSubstring: TypeExpression<StringType>? = null) :
    FunctionExpression<ArrayType<StringType>>(
        "SPLIT",
        inStr,
        inSubstring,
    )

fun split(inStr: TypeExpression<StringType>, inSubstring: TypeExpression<StringType>? = null) =
    SplitExpression(inStr, inSubstring)

fun split(inStr: TypeExpression<StringType>, inSubstring: String) = split(inStr, inSubstring.toDopeType())

fun split(inStr: String, inSubstring: TypeExpression<StringType>? = null) = split(inStr.toDopeType(), inSubstring)

fun split(inStr: String, inSubstring: String) = split(inStr.toDopeType(), inSubstring.toDopeType())
