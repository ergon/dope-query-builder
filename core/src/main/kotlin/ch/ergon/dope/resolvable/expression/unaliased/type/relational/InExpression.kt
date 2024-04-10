package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toArrayType
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class InExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : TypeExpression<BooleanType>, InfixOperator(value, "IN", collection) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ValidType> TypeExpression<T>.inArray(array: TypeExpression<ArrayType<T>>): InExpression<T> =
    InExpression(this, array)

fun Number.inArray(array: TypeExpression<ArrayType<NumberType>>): InExpression<NumberType> =
    toNumberType().inArray(array)

fun String.inArray(array: TypeExpression<ArrayType<StringType>>): InExpression<StringType> =
    toStringType().inArray(array)

fun Boolean.inArray(array: TypeExpression<ArrayType<BooleanType>>): InExpression<BooleanType> =
    toBooleanType().inArray(array)

fun <T : ValidType> TypeExpression<T>.inArray(array: Collection<TypeExpression<T>>): InExpression<T> =
    this.inArray(array.toArrayType())

fun Number.inArray(array: Collection<TypeExpression<NumberType>>): InExpression<NumberType> =
    inArray(array.toArrayType())

fun String.inArray(array: Collection<TypeExpression<StringType>>): InExpression<StringType> =
    inArray(array.toArrayType())

fun Boolean.inArray(array: Collection<TypeExpression<BooleanType>>): InExpression<BooleanType> =
    inArray(array.toArrayType())
