package ch.ergon.dope.resolvable.expression.single.type.function.type

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.AtomType

class ToArrayExpression<T : AtomType>(expression: TypeExpression<T>) : FunctionExpression<
    ArrayType<T>,>(
    "TOARRAY",
    expression,
)

fun <T : AtomType> TypeExpression<T>.toArray() = ToArrayExpression(this)
