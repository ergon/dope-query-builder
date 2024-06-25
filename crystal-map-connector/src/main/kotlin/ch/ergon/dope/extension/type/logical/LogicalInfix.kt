package ch.ergon.dope.extension.type.logical

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.LogicalInfixExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeField
import ch.ergon.dope.validtype.BooleanType
import com.schwarz.crystalapi.schema.CMField

fun TypeExpression<BooleanType>.or(boolean: CMField<Boolean>): LogicalInfixExpression =
    or(boolean.toDopeField())

fun CMField<Boolean>.or(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    toDopeField().or(booleanExpression)

fun CMField<Boolean>.or(boolean: Boolean): LogicalInfixExpression =
    toDopeField().or(boolean.toDopeType())

fun CMField<Boolean>.or(boolean: CMField<Boolean>): LogicalInfixExpression =
    toDopeField().or(boolean.toDopeField())

fun Boolean.or(booleanExpression: CMField<Boolean>): LogicalInfixExpression =
    toDopeType().or(booleanExpression.toDopeField())

fun TypeExpression<BooleanType>.and(boolean: CMField<Boolean>): LogicalInfixExpression =
    and(boolean.toDopeField())

fun CMField<Boolean>.and(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    toDopeField().and(booleanExpression)

fun CMField<Boolean>.and(boolean: Boolean): LogicalInfixExpression =
    toDopeField().and(boolean.toDopeType())

fun CMField<Boolean>.and(boolean: CMField<Boolean>): LogicalInfixExpression =
    toDopeField().and(boolean.toDopeField())

fun Boolean.and(booleanExpression: CMField<Boolean>): LogicalInfixExpression =
    toDopeType().and(booleanExpression.toDopeField())
