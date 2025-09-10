package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class LessThanExpression<T : ComparableType>(
    override val left: TypeExpression<T>,
    override val right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "<", right)

fun <T : ComparableType> TypeExpression<T>.isLessThan(right: TypeExpression<T>): LessThanExpression<T> =
    LessThanExpression(this, right)

fun TypeExpression<NumberType>.isLessThan(right: Number): LessThanExpression<NumberType> =
    isLessThan(right.toDopeType())

fun Number.isLessThan(right: TypeExpression<NumberType>): LessThanExpression<NumberType> =
    this.toDopeType().isLessThan(right)

fun Number.isLessThan(right: Number): LessThanExpression<NumberType> =
    this.toDopeType().isLessThan(right.toDopeType())

fun TypeExpression<StringType>.isLessThan(right: String): LessThanExpression<StringType> =
    isLessThan(right.toDopeType())

fun String.isLessThan(right: TypeExpression<StringType>): LessThanExpression<StringType> =
    this.toDopeType().isLessThan(right)

fun String.isLessThan(right: String): LessThanExpression<StringType> =
    this.toDopeType().isLessThan(right.toDopeType())
