package ch.ergon.dope.extension

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.whenThen
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMType

fun whenThen(condition: CMField<Boolean>, expression: UnaliasedExpression<out ValidType>) = whenThen(condition.toDopeType(), expression)

fun whenThen(condition: TypeExpression<BooleanType>, expression: CMType) = whenThen(condition, expression.toDopeType())

fun whenThen(condition: CMField<Boolean>, expression: CMType) = whenThen(condition.toDopeType(), expression.toDopeType())
