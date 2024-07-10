package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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
    override fun toDopeQuery() = toInfixDopeQuery()
}

fun <T : ValidType> TypeExpression<T>.inArray(array: TypeExpression<ArrayType<T>>): InExpression<T> =
    InExpression(this, array)

fun Number.inArray(array: TypeExpression<ArrayType<NumberType>>): InExpression<NumberType> =
    toDopeType().inArray(array)

fun String.inArray(array: TypeExpression<ArrayType<StringType>>): InExpression<StringType> =
    toDopeType().inArray(array)

fun Boolean.inArray(array: TypeExpression<ArrayType<BooleanType>>): InExpression<BooleanType> =
    toDopeType().inArray(array)

fun <T : ValidType> TypeExpression<T>.inArray(array: Collection<TypeExpression<T>>): InExpression<T> =
    this.inArray(array.toDopeType())

fun Number.inArray(array: Collection<TypeExpression<NumberType>>): InExpression<NumberType> =
    inArray(array.toDopeType())

fun String.inArray(array: Collection<TypeExpression<StringType>>): InExpression<StringType> =
    inArray(array.toDopeType())

fun Boolean.inArray(array: Collection<TypeExpression<BooleanType>>): InExpression<BooleanType> =
    inArray(array.toDopeType())
