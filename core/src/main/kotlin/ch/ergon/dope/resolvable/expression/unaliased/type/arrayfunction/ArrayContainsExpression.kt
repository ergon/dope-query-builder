package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayContainsExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>, value: TypeExpression<T>) :
    ArrayFunctionNumberExpression<T>("ARRAY_CONTAINS", array, value)

fun <T : ValidType> arrayContains(array: TypeExpression<ArrayType<T>>, value: TypeExpression<T>) =
    ArrayContainsExpression(array, value)

fun arrayContains(array: TypeExpression<ArrayType<StringType>>, value: String) =
    arrayContains(array, value.toDopeType())

fun arrayContains(array: TypeExpression<ArrayType<NumberType>>, value: Number) =
    arrayContains(array, value.toDopeType())

fun arrayContains(array: TypeExpression<ArrayType<BooleanType>>, value: Boolean) =
    arrayContains(array, value.toDopeType())
