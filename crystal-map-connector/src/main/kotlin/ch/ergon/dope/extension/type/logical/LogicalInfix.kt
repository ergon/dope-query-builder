package ch.ergon.dope.extension.type.logical

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.LogicalInfixExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import com.schwarz.crystalapi.schema.CMField

fun TypeExpression<BooleanType>.or(boolean: CMField<Boolean>): LogicalInfixExpression =
    or(boolean.toDopeType())

fun CMField<Boolean>.or(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    toDopeType().or(booleanExpression)

fun CMField<Boolean>.or(boolean: Boolean): LogicalInfixExpression =
    toDopeType().or(boolean.toDopeType())

fun CMField<Boolean>.or(boolean: CMField<Boolean>): LogicalInfixExpression =
    toDopeType().or(boolean.toDopeType())

fun Boolean.or(booleanExpression: CMField<Boolean>): LogicalInfixExpression =
    toDopeType().or(booleanExpression.toDopeType())

fun TypeExpression<BooleanType>.and(boolean: CMField<Boolean>): LogicalInfixExpression =
    and(boolean.toDopeType())

fun CMField<Boolean>.and(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    toDopeType().and(booleanExpression)

fun CMField<Boolean>.and(boolean: Boolean): LogicalInfixExpression =
    toDopeType().and(boolean.toDopeType())

fun CMField<Boolean>.and(boolean: CMField<Boolean>): LogicalInfixExpression =
    toDopeType().and(boolean.toDopeType())

fun Boolean.and(booleanExpression: CMField<Boolean>): LogicalInfixExpression =
    toDopeType().and(booleanExpression.toDopeType())
