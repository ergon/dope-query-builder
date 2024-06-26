package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class NotEqualsExpression<T : ValidType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "!=", right) {
    override fun toDopeQuery(): DopeQuery = toInfixDopeQuery()
}

fun <T : ValidType> TypeExpression<T>.isNotEqualTo(right: TypeExpression<T>): NotEqualsExpression<T> =
    NotEqualsExpression(this, right)

fun TypeExpression<NumberType>.isNotEqualTo(right: Number): NotEqualsExpression<NumberType> =
    isNotEqualTo(right.toDopeType())

fun Number.isNotEqualTo(right: TypeExpression<NumberType>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right)

fun Number.isNotEqualTo(right: Number): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

fun TypeExpression<StringType>.isNotEqualTo(right: String): NotEqualsExpression<StringType> =
    isNotEqualTo(right.toDopeType())

fun String.isNotEqualTo(right: TypeExpression<StringType>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right)

fun String.isNotEqualTo(right: String): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

fun TypeExpression<BooleanType>.isNotEqualTo(right: Boolean): NotEqualsExpression<BooleanType> =
    isNotEqualTo(right.toDopeType())

fun Boolean.isNotEqualTo(right: TypeExpression<BooleanType>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right)

fun Boolean.isNotEqualTo(right: Boolean): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())
