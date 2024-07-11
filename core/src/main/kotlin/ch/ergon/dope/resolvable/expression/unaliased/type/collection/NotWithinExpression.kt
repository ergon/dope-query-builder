package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class NotWithinExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : MembershipExpression<T>(value, "NOT WITHIN", collection)

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
