package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
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
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun <T : ValidType> TypeExpression<T>.withinArray(array: TypeExpression<ArrayType<T>>) =
    WithinExpression(this, array)

fun Number.withinArray(array: TypeExpression<ArrayType<NumberType>>) = toDopeType().withinArray(array)

fun String.withinArray(array: TypeExpression<ArrayType<StringType>>) = toDopeType().withinArray(array)

fun Boolean.withinArray(array: TypeExpression<ArrayType<BooleanType>>) = toDopeType().withinArray(array)

fun <T : ValidType> TypeExpression<T>.withinArray(array: Collection<TypeExpression<T>>) = withinArray(array.toDopeType())

fun Number.withinArray(array: Collection<TypeExpression<NumberType>>) = withinArray(array.toDopeType())

fun String.withinArray(array: Collection<TypeExpression<StringType>>) = withinArray(array.toDopeType())

fun Boolean.withinArray(array: Collection<TypeExpression<BooleanType>>) = withinArray(array.toDopeType())

fun <T : ValidType> TypeExpression<T>.withinArray(array: ISelectOffsetClause<T>) = withinArray(array.asExpression())

fun Number.withinArray(array: ISelectOffsetClause<NumberType>) = toDopeType().withinArray(array.asExpression())

fun String.withinArray(array: ISelectOffsetClause<StringType>) = toDopeType().withinArray(array.asExpression())

fun Boolean.withinArray(array: ISelectOffsetClause<BooleanType>) = toDopeType().withinArray(array.asExpression())

class NotWithinExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : TypeExpression<BooleanType>, InfixOperator(value, "NOT WITHIN", collection) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun <T : ValidType> TypeExpression<T>.notWithinArray(collection: TypeExpression<ArrayType<T>>) =
    NotWithinExpression(this, collection)

fun Number.notWithinArray(collection: TypeExpression<ArrayType<NumberType>>) = toDopeType().notWithinArray(collection)

fun String.notWithinArray(collection: TypeExpression<ArrayType<StringType>>) = toDopeType().notWithinArray(collection)

fun Boolean.notWithinArray(collection: TypeExpression<ArrayType<BooleanType>>) = toDopeType().notWithinArray(collection)

fun <T : ValidType> TypeExpression<T>.notWithinArray(collection: Collection<TypeExpression<T>>) =
    notWithinArray(collection.toDopeType())

fun Number.notWithinArray(collection: Collection<TypeExpression<NumberType>>) = notWithinArray(collection.toDopeType())

fun String.notWithinArray(collection: Collection<TypeExpression<StringType>>) = notWithinArray(collection.toDopeType())

fun Boolean.notWithinArray(collection: Collection<TypeExpression<BooleanType>>) = notWithinArray(collection.toDopeType())

fun <T : ValidType> TypeExpression<T>.notWithinArray(collection: ISelectOffsetClause<T>) =
    notWithinArray(collection.asExpression())

fun Number.notWithinArray(collection: ISelectOffsetClause<NumberType>) =
    toDopeType().notWithinArray(collection.asExpression())

fun String.notWithinArray(collection: ISelectOffsetClause<StringType>) =
    toDopeType().notWithinArray(collection.asExpression())

fun Boolean.notWithinArray(collection: ISelectOffsetClause<BooleanType>) =
    toDopeType().notWithinArray(collection.asExpression())
