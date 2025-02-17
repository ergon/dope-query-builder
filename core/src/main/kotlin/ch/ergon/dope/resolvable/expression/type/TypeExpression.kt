package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ValidType

interface TypeExpression<T : ValidType> : SingleExpression<T>
