package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

data class SuffixesExpression(val inStr: TypeExpression<StringType>) :
    FunctionExpression<ArrayType<StringType>>(listOf(inStr))

fun TypeExpression<StringType>.suffixes() = SuffixesExpression(this)

fun String.suffixes() = toDopeType().suffixes()
