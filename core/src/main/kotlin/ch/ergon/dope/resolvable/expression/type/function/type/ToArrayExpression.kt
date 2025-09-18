package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.AtomType

data class ToArrayExpression<T : AtomType>(val expression: TypeExpression<T>) :
    FunctionExpression<ArrayType<T>>(listOf(expression))

fun <T : AtomType> TypeExpression<T>.toArray() = ToArrayExpression(this)
