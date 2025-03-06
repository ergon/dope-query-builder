package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsAtomExpression<T : ValidType>(expression: TypeExpression<T>) : FunctionExpression<
    BooleanType,>(
    "ISATOM",
    expression,
)

fun <T : ValidType> TypeExpression<T>.isAtom() = IsAtomExpression(this)
