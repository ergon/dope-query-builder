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

class NotEqualsExpression<T : ValidType>(
    left: TypeExpression<T>,
    right: TypeExpression<T>,
) : TypeExpression<BooleanType>, InfixOperator(left, "!=", right) {
    override fun toQuery(): DopeQuery = toInfixQuery()
}

fun <T : ValidType> TypeExpression<T>.isNotEqualTo(right: TypeExpression<T>): NotEqualsExpression<T> =
    NotEqualsExpression(this, right)

fun TypeExpression<NumberType>.isNotEqualTo(right: Number): NotEqualsExpression<NumberType> =
    isNotEqualTo(right.toNumberType())

fun Number.isNotEqualTo(right: TypeExpression<NumberType>): NotEqualsExpression<NumberType> =
    toNumberType().isNotEqualTo(right)

fun Number.isNotEqualTo(right: Number): NotEqualsExpression<NumberType> =
    toNumberType().isNotEqualTo(right.toNumberType())

fun TypeExpression<StringType>.isNotEqualTo(right: String): NotEqualsExpression<StringType> =
    isNotEqualTo(right.toStringType())

fun String.isNotEqualTo(right: TypeExpression<StringType>): NotEqualsExpression<StringType> =
    toStringType().isNotEqualTo(right)

fun String.isNotEqualTo(right: String): NotEqualsExpression<StringType> =
    toStringType().isNotEqualTo(right.toStringType())

fun TypeExpression<BooleanType>.isNotEqualTo(right: Boolean): NotEqualsExpression<BooleanType> =
    isNotEqualTo(right.toBooleanType())

fun Boolean.isNotEqualTo(right: TypeExpression<BooleanType>): NotEqualsExpression<BooleanType> =
    toBooleanType().isNotEqualTo(right)

fun Boolean.isNotEqualTo(right: Boolean): NotEqualsExpression<BooleanType> =
    toBooleanType().isNotEqualTo(right.toBooleanType())
