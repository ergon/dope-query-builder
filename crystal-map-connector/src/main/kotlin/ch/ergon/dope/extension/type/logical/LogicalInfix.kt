package ch.ergon.dope.extension.type.logical

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import com.schwarz.crystalapi.schema.CMJsonField

fun TypeExpression<BooleanType>.or(boolean: CMJsonField<Boolean>) = or(boolean.toDopeType())

fun CMJsonField<Boolean>.or(booleanExpression: TypeExpression<BooleanType>) = toDopeType().or(booleanExpression)

fun CMJsonField<Boolean>.or(boolean: Boolean) = toDopeType().or(boolean.toDopeType())

fun CMJsonField<Boolean>.or(boolean: CMJsonField<Boolean>) = toDopeType().or(boolean.toDopeType())

fun Boolean.or(booleanExpression: CMJsonField<Boolean>) = toDopeType().or(booleanExpression.toDopeType())

fun TypeExpression<BooleanType>.and(boolean: CMJsonField<Boolean>) = and(boolean.toDopeType())

fun CMJsonField<Boolean>.and(booleanExpression: TypeExpression<BooleanType>) = toDopeType().and(booleanExpression)

fun CMJsonField<Boolean>.and(boolean: Boolean) = toDopeType().and(boolean.toDopeType())

fun CMJsonField<Boolean>.and(boolean: CMJsonField<Boolean>) = toDopeType().and(boolean.toDopeType())

fun Boolean.and(booleanExpression: CMJsonField<Boolean>) = toDopeType().and(booleanExpression.toDopeType())
