package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ArrayContainsExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
) : TypeExpression<BooleanType>, FunctionOperator

fun <T : ValidType> arrayContains(array: TypeExpression<ArrayType<T>>, value: TypeExpression<T>) =
    ArrayContainsExpression(array, value)

fun arrayContains(array: TypeExpression<ArrayType<StringType>>, value: String) =
    arrayContains(array, value.toDopeType())

fun arrayContains(array: TypeExpression<ArrayType<NumberType>>, value: Number) =
    arrayContains(array, value.toDopeType())

fun arrayContains(array: TypeExpression<ArrayType<BooleanType>>, value: Boolean) =
    arrayContains(array, value.toDopeType())

fun <T : ValidType> arrayContains(selectClause: ISelectOffsetClause<T>, value: TypeExpression<T>) =
    arrayContains(selectClause.asExpression(), value)

fun arrayContains(selectClause: ISelectOffsetClause<StringType>, value: String) =
    arrayContains(selectClause.asExpression(), value.toDopeType())

fun arrayContains(selectClause: ISelectOffsetClause<NumberType>, value: Number) =
    arrayContains(selectClause.asExpression(), value.toDopeType())

fun arrayContains(selectClause: ISelectOffsetClause<BooleanType>, value: Boolean) =
    arrayContains(selectClause.asExpression(), value.toDopeType())
