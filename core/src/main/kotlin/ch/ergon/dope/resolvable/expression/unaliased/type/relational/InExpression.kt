package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toArrayType
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class InExpression(
    value: TypeExpression<out ValidType>,
    collection: TypeExpression<out ArrayType<out ValidType>>,
) : TypeExpression<BooleanType>, InfixOperator(value, "IN", collection) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun <T : ValidType> TypeExpression<T>.inArray(array: TypeExpression<out ArrayType<out ValidType>>): InExpression =
    InExpression(this, array)

fun Number.inArray(array: TypeExpression<out ArrayType<out ValidType>>): InExpression =
    toNumberType().inArray(array)

fun String.inArray(array: TypeExpression<out ArrayType<out ValidType>>): InExpression =
    toStringType().inArray(array)

fun Boolean.inArray(array: TypeExpression<out ArrayType<out ValidType>>): InExpression =
    toBooleanType().inArray(array)

fun <T : ValidType> TypeExpression<T>.inArray(array: Collection<TypeExpression<out ValidType>>): InExpression =
    inArray(array.toArrayType())

fun Number.inArray(array: Collection<TypeExpression<out ValidType>>): InExpression =
    inArray(array.toArrayType())

fun String.inArray(array: Collection<TypeExpression<out ValidType>>): InExpression =
    inArray(array.toArrayType())

fun Boolean.inArray(array: Collection<TypeExpression<out ValidType>>): InExpression =
    inArray(array.toArrayType())
