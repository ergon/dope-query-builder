package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class NotInExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : MembershipExpression<T>(value, "NOT IN", collection)

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
