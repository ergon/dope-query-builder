package ch.ergon.dope.extension.type.logical

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.LogicalInfixExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.validtype.BooleanType
import com.schwarz.crystalapi.schema.CMField

fun TypeExpression<BooleanType>.or(boolean: CMField<Boolean>): LogicalInfixExpression =
    or(boolean.asField())

fun CMField<Boolean>.or(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    asField().or(booleanExpression)

fun CMField<Boolean>.or(boolean: Boolean): LogicalInfixExpression =
    asField().or(boolean.toBooleanType())

fun CMField<Boolean>.or(boolean: CMField<Boolean>): LogicalInfixExpression =
    asField().or(boolean.asField())

fun Boolean.or(booleanExpression: CMField<Boolean>): LogicalInfixExpression =
    toBooleanType().or(booleanExpression.asField())

fun TypeExpression<BooleanType>.and(boolean: CMField<Boolean>): LogicalInfixExpression =
    and(boolean.asField())

fun CMField<Boolean>.and(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    asField().and(booleanExpression)

fun CMField<Boolean>.and(boolean: Boolean): LogicalInfixExpression =
    asField().and(boolean.toBooleanType())

fun CMField<Boolean>.and(boolean: CMField<Boolean>): LogicalInfixExpression =
    asField().and(boolean.asField())

fun Boolean.and(booleanExpression: CMField<Boolean>): LogicalInfixExpression =
    toBooleanType().and(booleanExpression.asField())
