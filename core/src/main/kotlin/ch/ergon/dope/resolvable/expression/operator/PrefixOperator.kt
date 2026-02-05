package ch.ergon.dope.resolvable.expression.operator

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

abstract class PrefixOperator<T : ValidType>(val argument: Resolvable) : TypeExpression<T>
