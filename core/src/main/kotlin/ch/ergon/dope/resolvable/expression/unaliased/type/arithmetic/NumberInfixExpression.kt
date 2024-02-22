package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.NumberType

class NumberInfixExpression(
    private val left: TypeExpression<NumberType>,
    private val symbol: String,
    private val right: TypeExpression<NumberType>,
) : TypeExpression<NumberType>, InfixOperator(left, symbol, right) {
    override fun toQueryString(): String = formatToQueryStringWithBrackets(left.toQueryString(), symbol, right.toQueryString())
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
