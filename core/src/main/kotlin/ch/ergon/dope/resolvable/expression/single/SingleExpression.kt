package ch.ergon.dope.resolvable.expression.single

import ch.ergon.dope.resolvable.Returnable
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ValidType

interface SingleExpression<T : ValidType> : Expression<T>, Returnable
