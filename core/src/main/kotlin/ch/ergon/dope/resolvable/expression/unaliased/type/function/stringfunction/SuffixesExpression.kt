package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class SuffixesExpression(inStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>("SUFFIXES", inStr)

fun suffixes(inStr: TypeExpression<StringType>) = SuffixesExpression(inStr)

fun suffixes(inStr: String) = SuffixesExpression(inStr.toDopeType())
