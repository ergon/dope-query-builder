package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

class SuffixesExpression(inStr: TypeExpression<StringType>) :
    FunctionExpression<ArrayType<StringType>>("SUFFIXES", inStr)

fun suffixes(inStr: TypeExpression<StringType>) = SuffixesExpression(inStr)

fun suffixes(inStr: String) = suffixes(inStr.toDopeType())
