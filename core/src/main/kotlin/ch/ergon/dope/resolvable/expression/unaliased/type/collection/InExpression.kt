package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.DopeQueryManager
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
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
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

class NotInExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : TypeExpression<BooleanType>, InfixOperator(value, "NOT IN", collection) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun <T : ValidType> TypeExpression<T>.notInArray(array: TypeExpression<ArrayType<T>>): NotInExpression<T> =
    NotInExpression(this, array)

fun Number.notInArray(array: TypeExpression<ArrayType<NumberType>>): NotInExpression<NumberType> =
    toDopeType().notInArray(array)

fun String.notInArray(array: TypeExpression<ArrayType<StringType>>): NotInExpression<StringType> =
    toDopeType().notInArray(array)

fun Boolean.notInArray(array: TypeExpression<ArrayType<BooleanType>>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array)

fun <T : ValidType> TypeExpression<T>.notInArray(array: Collection<TypeExpression<T>>): NotInExpression<T> =
    this.notInArray(array.toDopeType())

fun Number.notInArray(array: Collection<TypeExpression<NumberType>>): NotInExpression<NumberType> =
    notInArray(array.toDopeType())

fun String.notInArray(array: Collection<TypeExpression<StringType>>): NotInExpression<StringType> =
    notInArray(array.toDopeType())

fun Boolean.notInArray(array: Collection<TypeExpression<BooleanType>>): NotInExpression<BooleanType> =
    notInArray(array.toDopeType())
