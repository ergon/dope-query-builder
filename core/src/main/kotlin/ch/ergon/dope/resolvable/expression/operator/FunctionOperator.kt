package ch.ergon.dope.resolvable.expression.operator

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

interface FunctionOperator<T : ValidType> : TypeExpression<T>
