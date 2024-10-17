package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayFlattenExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>, depth: TypeExpression<NumberType>) :
    ArrayFunctionExpression<T>("ARRAY_FLATTEN", array, depth)

fun <T : ValidType> arrayFlatten(array: TypeExpression<ArrayType<T>>, depth: TypeExpression<NumberType>) =
    ArrayFlattenExpression(array, depth)

fun <T : ValidType> arrayFlatten(array: TypeExpression<ArrayType<T>>, depth: Number) =
    arrayFlatten(array, depth.toDopeType())

fun <T : ValidType> arrayFlatten(array: ISelectOffsetClause<T>, depth: TypeExpression<NumberType>) =
    arrayFlatten(array.asExpression(), depth)

fun <T : ValidType> arrayFlatten(array: ISelectOffsetClause<T>, depth: Number) =
    arrayFlatten(array.asExpression(), depth.toDopeType())
