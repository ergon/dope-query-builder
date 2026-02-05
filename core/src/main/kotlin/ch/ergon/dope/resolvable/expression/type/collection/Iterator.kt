package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

data class Iterator<T : ValidType>(val variable: String) : TypeExpression<T>
