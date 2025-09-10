package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class GreaterThanExpression<T : ComparableType>(
    override val left: TypeExpression<T>,
    override val right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, ">", right)

fun <T : ComparableType> TypeExpression<T>.isGreaterThan(right: TypeExpression<T>): GreaterThanExpression<T> =
    GreaterThanExpression(this, right)

fun TypeExpression<NumberType>.isGreaterThan(right: Number): GreaterThanExpression<NumberType> =
    isGreaterThan(right.toDopeType())

fun Number.isGreaterThan(right: TypeExpression<NumberType>): GreaterThanExpression<NumberType> =
    this.toDopeType().isGreaterThan(right)

fun Number.isGreaterThan(right: Number): GreaterThanExpression<NumberType> =
    this.toDopeType().isGreaterThan(right.toDopeType())

fun TypeExpression<StringType>.isGreaterThan(right: String): GreaterThanExpression<StringType> =
    isGreaterThan(right.toDopeType())

fun String.isGreaterThan(right: TypeExpression<StringType>): GreaterThanExpression<StringType> =
    this.toDopeType().isGreaterThan(right)

fun String.isGreaterThan(right: String): GreaterThanExpression<StringType> =
    this.toDopeType().isGreaterThan(right.toDopeType())
