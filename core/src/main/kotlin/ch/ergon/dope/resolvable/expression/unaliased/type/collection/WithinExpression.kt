package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class WithinExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : TypeExpression<BooleanType>, InfixOperator(value, "WITHIN", collection) {
    override fun toDopeQuery() = toInfixDopeQuery()
}

fun <T : ValidType> TypeExpression<T>.withinArray(array: TypeExpression<ArrayType<T>>): WithinExpression<T> =
    WithinExpression(this, array)

fun Number.withinArray(array: TypeExpression<ArrayType<NumberType>>): WithinExpression<NumberType> =
    toDopeType().withinArray(array)

fun String.withinArray(array: TypeExpression<ArrayType<StringType>>): WithinExpression<StringType> =
    toDopeType().withinArray(array)

fun Boolean.withinArray(array: TypeExpression<ArrayType<BooleanType>>): WithinExpression<BooleanType> =
    toDopeType().withinArray(array)

fun <T : ValidType> TypeExpression<T>.withinArray(array: Collection<TypeExpression<T>>): WithinExpression<T> =
    this.withinArray(array.toDopeType())

fun Number.withinArray(array: Collection<TypeExpression<NumberType>>): WithinExpression<NumberType> =
    withinArray(array.toDopeType())

fun String.withinArray(array: Collection<TypeExpression<StringType>>): WithinExpression<StringType> =
    withinArray(array.toDopeType())

fun Boolean.withinArray(array: Collection<TypeExpression<BooleanType>>): WithinExpression<BooleanType> =
    withinArray(array.toDopeType())

class NotWithinExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : TypeExpression<BooleanType>, InfixOperator(value, "NOT WITHIN", collection) {
    override fun toDopeQuery() = toInfixDopeQuery()
}

fun <T : ValidType> TypeExpression<T>.notWithinArray(collection: TypeExpression<ArrayType<T>>) = NotWithinExpression(this, collection)

fun Number.notWithinArray(collection: TypeExpression<ArrayType<NumberType>>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(collection)

fun String.notWithinArray(collection: TypeExpression<ArrayType<StringType>>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(collection)

fun Boolean.notWithinArray(collection: TypeExpression<ArrayType<BooleanType>>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(collection)

fun <T : ValidType> TypeExpression<T>.notWithinArray(collection: Collection<TypeExpression<T>>): NotWithinExpression<T> =
    this.notWithinArray(collection.toDopeType())

fun Number.notWithinArray(collection: Collection<TypeExpression<NumberType>>): NotWithinExpression<NumberType> =
    notWithinArray(collection.toDopeType())

fun String.notWithinArray(collection: Collection<TypeExpression<StringType>>): NotWithinExpression<StringType> =
    notWithinArray(collection.toDopeType())

fun Boolean.notWithinArray(collection: Collection<TypeExpression<BooleanType>>): NotWithinExpression<BooleanType> =
    notWithinArray(collection.toDopeType())
