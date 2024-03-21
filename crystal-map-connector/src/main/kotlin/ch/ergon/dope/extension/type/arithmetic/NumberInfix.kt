package ch.ergon.dope.extension.type.arithmetic

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.NumberInfixExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.div
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun CMField<Number>.add(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().add(numberExpression)

fun CMField<Number>.add(number: CMField<Number>): NumberInfixExpression =
    this.asField().add(number.asField())

fun TypeExpression<NumberType>.add(number: CMField<Number>): NumberInfixExpression =
    this.add(number.asField())

fun Number.add(number: CMField<Number>): NumberInfixExpression =
    toNumberType().add(number.asField())

fun CMField<Number>.add(number: Number): NumberInfixExpression =
    this.asField().add(number.toNumberType())

fun CMField<Number>.sub(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().sub(numberExpression)

fun CMField<Number>.sub(number: CMField<Number>): NumberInfixExpression =
    this.asField().sub(number.asField())

fun TypeExpression<NumberType>.sub(number: CMField<Number>): NumberInfixExpression =
    this.sub(number.asField())

fun Number.sub(number: CMField<Number>): NumberInfixExpression =
    toNumberType().sub(number.asField())

fun CMField<Number>.sub(number: Number): NumberInfixExpression =
    this.asField().sub(number.toNumberType())

fun CMField<Number>.mul(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().mul(numberExpression)

fun CMField<Number>.mul(number: CMField<Number>): NumberInfixExpression =
    this.asField().mul(number.asField())

fun TypeExpression<NumberType>.mul(number: CMField<Number>): NumberInfixExpression =
    this.mul(number.asField())

fun Number.mul(number: CMField<Number>): NumberInfixExpression =
    toNumberType().mul(number.asField())

fun CMField<Number>.mul(number: Number): NumberInfixExpression =
    this.asField().mul(number.toNumberType())

fun CMField<Number>.div(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().div(numberExpression)

fun CMField<Number>.div(number: CMField<Number>): NumberInfixExpression =
    this.asField().div(number.asField())

fun TypeExpression<NumberType>.div(number: CMField<Number>): NumberInfixExpression =
    this.div(number.asField())

fun Number.div(number: CMField<Number>): NumberInfixExpression =
    toNumberType().div(number.asField())

fun CMField<Number>.div(number: Number): NumberInfixExpression =
    this.asField().div(number.toNumberType())

fun CMField<Number>.mod(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().mod(numberExpression)

fun CMField<Number>.mod(number: CMField<Number>): NumberInfixExpression =
    this.asField().mod(number.asField())

fun TypeExpression<NumberType>.mod(number: CMField<Number>): NumberInfixExpression =
    this.mod(number.asField())

fun Number.mod(number: CMField<Number>): NumberInfixExpression =
    toNumberType().mod(number.asField())

fun CMField<Number>.mod(number: Number): NumberInfixExpression =
    this.asField().mod(number.toNumberType())
