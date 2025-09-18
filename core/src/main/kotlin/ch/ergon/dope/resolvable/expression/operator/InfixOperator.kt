package ch.ergon.dope.resolvable.expression.operator

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

open class InfixOperator(
    open val left: TypeExpression<out ValidType>,
    open val right: TypeExpression<out ValidType>,
)
