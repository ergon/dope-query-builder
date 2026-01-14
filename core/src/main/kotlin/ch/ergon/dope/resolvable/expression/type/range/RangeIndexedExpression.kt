package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.ValidType

abstract class RangeIndexedExpression<T : ValidType, U : ValidType, V : ValidType> : Resolvable, RangeIndexedLike<T, U, V>
