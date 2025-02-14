package ch.ergon.dope.resolvable.expression.single.type

import ch.ergon.dope.resolvable.expression.single.SingleExpression
import ch.ergon.dope.validtype.ValidType

interface TypeExpression<T : ValidType> : SingleExpression<T>
