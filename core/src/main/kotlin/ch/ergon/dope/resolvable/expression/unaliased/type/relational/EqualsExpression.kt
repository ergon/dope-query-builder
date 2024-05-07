package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class EqualsExpression<T : ValidType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "=", right) {
    override fun toQuery(): DopeQuery = toInfixQuery()
}

fun <T : ValidType> TypeExpression<T>.isEqualTo(right: TypeExpression<T>): EqualsExpression<T> =
    EqualsExpression(this, right)

fun TypeExpression<NumberType>.isEqualTo(right: Number): EqualsExpression<NumberType> =
    isEqualTo(right.toNumberType())

fun Number.isEqualTo(right: TypeExpression<NumberType>): EqualsExpression<NumberType> =
    toNumberType().isEqualTo(right)

fun Number.isEqualTo(right: Number): EqualsExpression<NumberType> =
    toNumberType().isEqualTo(right.toNumberType())

fun TypeExpression<StringType>.isEqualTo(right: String): EqualsExpression<StringType> =
    isEqualTo(right.toStringType())

fun String.isEqualTo(right: TypeExpression<StringType>): EqualsExpression<StringType> =
    toStringType().isEqualTo(right)

fun String.isEqualTo(right: String): EqualsExpression<StringType> =
    toStringType().isEqualTo(right.toStringType())

fun TypeExpression<BooleanType>.isEqualTo(right: Boolean): EqualsExpression<BooleanType> =
    isEqualTo(right.toBooleanType())

fun Boolean.isEqualTo(right: TypeExpression<BooleanType>): EqualsExpression<BooleanType> =
    toBooleanType().isEqualTo(right)

fun Boolean.isEqualTo(right: Boolean): EqualsExpression<BooleanType> =
    toBooleanType().isEqualTo(right.toBooleanType())
