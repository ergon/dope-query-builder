package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

data class ArrayFlattenExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val depth: TypeExpression<NumberType>,
) : FunctionExpression<ArrayType<T>>(listOf(array, depth))

fun <T : ValidType> TypeExpression<ArrayType<T>>.flatten(depth: TypeExpression<NumberType>) =
    ArrayFlattenExpression(this, depth)

fun <T : ValidType> TypeExpression<ArrayType<T>>.flatten(depth: Number) =
    flatten(depth.toDopeType())

fun <T : ValidType> ISelectOffsetClause<T>.flatten(depth: TypeExpression<NumberType>) =
    asExpression().flatten(depth)

fun <T : ValidType> ISelectOffsetClause<T>.flatten(depth: Number) =
    asExpression().flatten(depth)
