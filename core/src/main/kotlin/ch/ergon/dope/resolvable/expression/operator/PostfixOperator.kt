package ch.ergon.dope.resolvable.expression.operator

import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

abstract class PostfixOperator<T : ValidType>(val argument: IField<out ValidType>) : TypeExpression<T>
