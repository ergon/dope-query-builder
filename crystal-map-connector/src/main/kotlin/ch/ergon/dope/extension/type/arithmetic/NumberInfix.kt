package ch.ergon.dope.extension.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.NumberInfixExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.div
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun CMField<out Number>.add(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeType().add(numberExpression)

fun CMField<out Number>.add(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeType().add(number.toDopeType())

fun TypeExpression<NumberType>.add(number: CMField<out Number>): NumberInfixExpression =
    this.add(number.toDopeType())

fun Number.add(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().add(number.toDopeType())

fun CMField<out Number>.add(number: Number): NumberInfixExpression =
    this.toDopeType().add(number.toDopeType())

fun CMField<out Number>.sub(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeType().sub(numberExpression)

fun CMField<out Number>.sub(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeType().sub(number.toDopeType())

fun TypeExpression<NumberType>.sub(number: CMField<out Number>): NumberInfixExpression =
    this.sub(number.toDopeType())

fun Number.sub(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().sub(number.toDopeType())

fun CMField<out Number>.sub(number: Number): NumberInfixExpression =
    this.toDopeType().sub(number.toDopeType())

fun CMField<out Number>.mul(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeType().mul(numberExpression)

fun CMField<out Number>.mul(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeType().mul(number.toDopeType())

fun TypeExpression<NumberType>.mul(number: CMField<out Number>): NumberInfixExpression =
    this.mul(number.toDopeType())

fun Number.mul(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().mul(number.toDopeType())

fun CMField<out Number>.mul(number: Number): NumberInfixExpression =
    this.toDopeType().mul(number.toDopeType())

fun CMField<out Number>.div(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeType().div(numberExpression)

fun CMField<out Number>.div(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeType().div(number.toDopeType())

fun TypeExpression<NumberType>.div(number: CMField<out Number>): NumberInfixExpression =
    this.div(number.toDopeType())

fun Number.div(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().div(number.toDopeType())

fun CMField<out Number>.div(number: Number): NumberInfixExpression =
    this.toDopeType().div(number.toDopeType())

fun CMField<out Number>.mod(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeType().mod(numberExpression)

fun CMField<out Number>.mod(number: CMField<out Number>): NumberInfixExpression =
    this.toDopeType().mod(number.toDopeType())

fun TypeExpression<NumberType>.mod(number: CMField<out Number>): NumberInfixExpression =
    this.mod(number.toDopeType())

fun Number.mod(number: CMField<out Number>): NumberInfixExpression =
    toDopeType().mod(number.toDopeType())

fun CMField<out Number>.mod(number: Number): NumberInfixExpression =
    this.toDopeType().mod(number.toDopeType())
