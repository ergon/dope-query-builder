package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ToArrayExpression<T : ValidType>(expression: TypeExpression<T>) : TypeFunction<T, ArrayType<T>>(expression, "TOARRAY")

fun <T : ValidType> TypeExpression<T>.toArray() = ToArrayExpression(this)
