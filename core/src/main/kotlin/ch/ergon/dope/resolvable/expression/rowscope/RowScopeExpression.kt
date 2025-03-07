package ch.ergon.dope.resolvable.expression.rowscope

import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ValidType

interface RowScopeExpression<T : ValidType> : Expression<T>
