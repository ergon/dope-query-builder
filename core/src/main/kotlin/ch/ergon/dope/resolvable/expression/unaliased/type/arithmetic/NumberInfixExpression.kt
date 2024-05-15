package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.NumberType

class NumberInfixExpression(
    private val left: TypeExpression<NumberType>,
    private val symbol: String,
    private val right: TypeExpression<NumberType>,
) : TypeExpression<NumberType>, InfixOperator(left, symbol, right) {
    override fun toDopeQuery(): DopeQuery {
        val leftDopeQuery = left.toDopeQuery()
        val rightDopeQuery = right.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithBrackets(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
            parameters = leftDopeQuery.parameters + rightDopeQuery.parameters,
        )
    }
}

fun TypeExpression<NumberType>.add(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    NumberInfixExpression(this, "+", numberExpression)

fun TypeExpression<NumberType>.sub(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    NumberInfixExpression(this, "-", numberExpression)

fun TypeExpression<NumberType>.mul(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    NumberInfixExpression(this, "*", numberExpression)

fun TypeExpression<NumberType>.div(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    NumberInfixExpression(this, "/", numberExpression)

fun TypeExpression<NumberType>.mod(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    NumberInfixExpression(this, "%", numberExpression)

fun TypeExpression<NumberType>.add(number: Number): NumberInfixExpression =
    add(number.toNumberType())

fun Number.add(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toNumberType().add(numberExpression)

fun Number.add(number: Number): NumberInfixExpression =
    this.toNumberType().add(number.toNumberType())

fun TypeExpression<NumberType>.sub(number: Number): NumberInfixExpression =
    sub(number.toNumberType())

fun Number.sub(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toNumberType().sub(numberExpression)

fun Number.sub(number: Number): NumberInfixExpression =
    this.toNumberType().sub(number.toNumberType())

fun TypeExpression<NumberType>.mul(number: Number): NumberInfixExpression =
    mul(number.toNumberType())

fun Number.mul(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toNumberType().mul(numberExpression)

fun Number.mul(number: Number): NumberInfixExpression =
    this.toNumberType().mul(number.toNumberType())

fun TypeExpression<NumberType>.div(number: Number): NumberInfixExpression =
    div(number.toNumberType())

fun Number.div(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toNumberType().div(numberExpression)

fun TypeExpression<NumberType>.mod(number: Number): NumberInfixExpression =
    mod(number.toNumberType())

fun Number.mod(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toNumberType().mod(numberExpression)

fun Number.mod(number: Number): NumberInfixExpression =
    this.toNumberType().mod(number.toNumberType())
