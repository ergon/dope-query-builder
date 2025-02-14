package ch.ergon.dope.resolvable.expression.single.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.util.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class EqualsExpression<T : ValidType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "=", right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
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

class NotEqualsExpression<T : ValidType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "!=", right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
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
