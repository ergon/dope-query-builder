package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class WithinExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : MembershipExpression<T>(value, "WITHIN", collection)

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
