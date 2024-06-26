package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class EqualsExpression<T : ValidType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "=", right) {
    override fun toDopeQuery(): DopeQuery = toInfixDopeQuery()
}

fun <T : ValidType> TypeExpression<T>.isEqualTo(right: TypeExpression<T>): EqualsExpression<T> =
    EqualsExpression(this, right)

fun TypeExpression<NumberType>.isEqualTo(right: Number): EqualsExpression<NumberType> =
    isEqualTo(right.toDopeType())

fun Number.isEqualTo(right: TypeExpression<NumberType>): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right)

fun Number.isEqualTo(right: Number): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right.toDopeType())

fun TypeExpression<StringType>.isEqualTo(right: String): EqualsExpression<StringType> =
    isEqualTo(right.toDopeType())

fun String.isEqualTo(right: TypeExpression<StringType>): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right)

fun String.isEqualTo(right: String): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right.toDopeType())

fun TypeExpression<BooleanType>.isEqualTo(right: Boolean): EqualsExpression<BooleanType> =
    isEqualTo(right.toDopeType())

fun Boolean.isEqualTo(right: TypeExpression<BooleanType>): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right)

fun Boolean.isEqualTo(right: Boolean): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right.toDopeType())
