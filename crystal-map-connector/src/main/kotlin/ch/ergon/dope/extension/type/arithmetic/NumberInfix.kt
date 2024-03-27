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

fun CMField<out Number>.add(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().add(numberExpression)

fun CMField<out Number>.add(number: CMField<out Number>): NumberInfixExpression =
    this.asField().add(number.asField())

fun TypeExpression<NumberType>.add(number: CMField<out Number>): NumberInfixExpression =
    this.add(number.asField())

fun Number.add(number: CMField<out Number>): NumberInfixExpression =
    toNumberType().add(number.asField())

fun CMField<out Number>.add(number: Number): NumberInfixExpression =
    this.asField().add(number.toNumberType())

fun CMField<out Number>.sub(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().sub(numberExpression)

fun CMField<out Number>.sub(number: CMField<out Number>): NumberInfixExpression =
    this.asField().sub(number.asField())

fun TypeExpression<NumberType>.sub(number: CMField<out Number>): NumberInfixExpression =
    this.sub(number.asField())

fun Number.sub(number: CMField<out Number>): NumberInfixExpression =
    toNumberType().sub(number.asField())

fun CMField<out Number>.sub(number: Number): NumberInfixExpression =
    this.asField().sub(number.toNumberType())

fun CMField<out Number>.mul(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().mul(numberExpression)

fun CMField<out Number>.mul(number: CMField<out Number>): NumberInfixExpression =
    this.asField().mul(number.asField())

fun TypeExpression<NumberType>.mul(number: CMField<out Number>): NumberInfixExpression =
    this.mul(number.asField())

fun Number.mul(number: CMField<out Number>): NumberInfixExpression =
    toNumberType().mul(number.asField())

fun CMField<out Number>.mul(number: Number): NumberInfixExpression =
    this.asField().mul(number.toNumberType())

fun CMField<out Number>.div(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().div(numberExpression)

fun CMField<out Number>.div(number: CMField<out Number>): NumberInfixExpression =
    this.asField().div(number.asField())

fun TypeExpression<NumberType>.div(number: CMField<out Number>): NumberInfixExpression =
    this.div(number.asField())

fun Number.div(number: CMField<out Number>): NumberInfixExpression =
    toNumberType().div(number.asField())

fun CMField<out Number>.div(number: Number): NumberInfixExpression =
    this.asField().div(number.toNumberType())

fun CMField<out Number>.mod(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.asField().mod(numberExpression)

fun CMField<out Number>.mod(number: CMField<out Number>): NumberInfixExpression =
    this.asField().mod(number.asField())

fun TypeExpression<NumberType>.mod(number: CMField<out Number>): NumberInfixExpression =
    this.mod(number.asField())

fun Number.mod(number: CMField<out Number>): NumberInfixExpression =
    toNumberType().mod(number.asField())

fun CMField<out Number>.mod(number: Number): NumberInfixExpression =
    this.asField().mod(number.toNumberType())
