package ch.ergon.dope.extension.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.NumberInfixExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.div
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeField
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun CMField<out Number>.add(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeField().add(numberExpression)

fun CMField<out Number>.add(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeField().add(number.toDopeField())

fun TypeExpression<NumberType>.add(number: CMField<out Number>): NumberInfixExpression =
    this.add(number.toDopeField())

fun Number.add(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().add(number.toDopeField())

fun CMField<out Number>.add(number: Number): NumberInfixExpression =
    this.toDopeField().add(number.toDopeType())

fun CMField<out Number>.sub(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeField().sub(numberExpression)

fun CMField<out Number>.sub(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeField().sub(number.toDopeField())

fun TypeExpression<NumberType>.sub(number: CMField<out Number>): NumberInfixExpression =
    this.sub(number.toDopeField())

fun Number.sub(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().sub(number.toDopeField())

fun CMField<out Number>.sub(number: Number): NumberInfixExpression =
    this.toDopeField().sub(number.toDopeType())

fun CMField<out Number>.mul(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeField().mul(numberExpression)

fun CMField<out Number>.mul(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeField().mul(number.toDopeField())

fun TypeExpression<NumberType>.mul(number: CMField<out Number>): NumberInfixExpression =
    this.mul(number.toDopeField())

fun Number.mul(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().mul(number.toDopeField())

fun CMField<out Number>.mul(number: Number): NumberInfixExpression =
    this.toDopeField().mul(number.toDopeType())

fun CMField<out Number>.div(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeField().div(numberExpression)

fun CMField<out Number>.div(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeField().div(number.toDopeField())

fun TypeExpression<NumberType>.div(number: CMField<out Number>): NumberInfixExpression =
    this.div(number.toDopeField())

fun Number.div(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().div(number.toDopeField())

fun CMField<out Number>.div(number: Number): NumberInfixExpression =
    this.toDopeField().div(number.toDopeType())

fun CMField<out Number>.mod(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeField().mod(numberExpression)

fun CMField<out Number>.mod(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeField().mod(number.toDopeField())

fun TypeExpression<NumberType>.mod(number: CMField<out Number>): NumberInfixExpression =
    this.mod(number.toDopeField())

fun Number.mod(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().mod(number.toDopeField())

fun CMField<out Number>.mod(number: Number): NumberInfixExpression =
    this.toDopeField().mod(number.toDopeType())
