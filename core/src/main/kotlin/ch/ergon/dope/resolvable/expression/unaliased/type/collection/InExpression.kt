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

class InExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : TypeExpression<BooleanType>, InfixOperator(value, "IN", collection) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun <T : ValidType> TypeExpression<T>.inArray(array: TypeExpression<ArrayType<T>>) = InExpression(this, array)

fun Number.inArray(array: TypeExpression<ArrayType<NumberType>>) = toDopeType().inArray(array)

fun String.inArray(array: TypeExpression<ArrayType<StringType>>) = toDopeType().inArray(array)

fun Boolean.inArray(array: TypeExpression<ArrayType<BooleanType>>) = toDopeType().inArray(array)

fun <T : ValidType> TypeExpression<T>.inArray(array: Collection<TypeExpression<T>>) = inArray(array.toDopeType())

fun Number.inArray(array: Collection<TypeExpression<NumberType>>) = inArray(array.toDopeType())

fun String.inArray(array: Collection<TypeExpression<StringType>>) = inArray(array.toDopeType())

fun Boolean.inArray(array: Collection<TypeExpression<BooleanType>>) = inArray(array.toDopeType())

fun <T : ValidType> TypeExpression<T>.inArray(selectClause: ISelectOffsetClause<T>) =
    inArray(selectClause.asExpression())

fun Number.inArray(selectClause: ISelectOffsetClause<NumberType>) =
    toDopeType().inArray(selectClause.asExpression())

fun String.inArray(selectClause: ISelectOffsetClause<StringType>) =
    toDopeType().inArray(selectClause.asExpression())

fun Boolean.inArray(selectClause: ISelectOffsetClause<BooleanType>) =
    toDopeType().inArray(selectClause.asExpression())

class NotInExpression<T : ValidType>(
    value: TypeExpression<T>,
    collection: TypeExpression<ArrayType<T>>,
) : TypeExpression<BooleanType>, InfixOperator(value, "NOT IN", collection) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(manager = manager)
}

fun <T : ValidType> TypeExpression<T>.notInArray(array: TypeExpression<ArrayType<T>>) = NotInExpression(this, array)

fun Number.notInArray(array: TypeExpression<ArrayType<NumberType>>) = toDopeType().notInArray(array)

fun String.notInArray(array: TypeExpression<ArrayType<StringType>>) = toDopeType().notInArray(array)

fun Boolean.notInArray(array: TypeExpression<ArrayType<BooleanType>>) = toDopeType().notInArray(array)

fun <T : ValidType> TypeExpression<T>.notInArray(array: Collection<TypeExpression<T>>) = notInArray(array.toDopeType())

fun Number.notInArray(array: Collection<TypeExpression<NumberType>>) = notInArray(array.toDopeType())

fun String.notInArray(array: Collection<TypeExpression<StringType>>) = notInArray(array.toDopeType())

fun Boolean.notInArray(array: Collection<TypeExpression<BooleanType>>) = notInArray(array.toDopeType())

fun <T : ValidType> TypeExpression<T>.notInArray(selectClause: ISelectOffsetClause<T>) =
    notInArray(selectClause.asExpression())

fun Number.notInArray(selectClause: ISelectOffsetClause<NumberType>) =
    toDopeType().notInArray(selectClause.asExpression())

fun String.notInArray(selectClause: ISelectOffsetClause<StringType>) =
    toDopeType().notInArray(selectClause.asExpression())

fun Boolean.notInArray(selectClause: ISelectOffsetClause<BooleanType>) =
    toDopeType().notInArray(selectClause.asExpression())
