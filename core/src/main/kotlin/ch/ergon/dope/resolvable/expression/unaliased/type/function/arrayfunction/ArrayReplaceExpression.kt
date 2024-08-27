package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayReplaceExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    toReplace: TypeExpression<T>,
    replaceWith: TypeExpression<T>,
    max: TypeExpression<NumberType>? = null,
) : ArrayFunctionExpression<T>("ARRAY_REPLACE", array, *listOfNotNull(toReplace, replaceWith, max).toTypedArray())

fun <T : ValidType> arrayReplace(
    array: TypeExpression<ArrayType<T>>,
    toReplace: TypeExpression<T>,
    replaceWith: TypeExpression<T>,
    max: TypeExpression<NumberType>? = null,
) = ArrayReplaceExpression(array, toReplace, replaceWith, max)

fun arrayReplace(
    array: TypeExpression<ArrayType<StringType>>,
    toReplace: TypeExpression<StringType>,
    replaceWith: String,
    max: Number? = null,
) = arrayReplace(array, toReplace, replaceWith.toDopeType(), max?.toDopeType())

fun arrayReplace(
    array: TypeExpression<ArrayType<StringType>>,
    toReplace: String,
    replaceWith: TypeExpression<StringType>,
    max: Number? = null,
) = arrayReplace(array, toReplace.toDopeType(), replaceWith, max?.toDopeType())

fun arrayReplace(
    array: TypeExpression<ArrayType<StringType>>,
    toReplace: String,
    replaceWith: String,
    max: Number? = null,
) = arrayReplace(array, toReplace.toDopeType(), replaceWith.toDopeType(), max?.toDopeType())

fun arrayReplace(
    array: TypeExpression<ArrayType<NumberType>>,
    toReplace: TypeExpression<NumberType>,
    replaceWith: Number,
    max: Number? = null,
) = arrayReplace(array, toReplace, replaceWith.toDopeType(), max?.toDopeType())

fun arrayReplace(
    array: TypeExpression<ArrayType<NumberType>>,
    toReplace: Number,
    replaceWith: TypeExpression<NumberType>,
    max: Number? = null,
) = arrayReplace(array, toReplace.toDopeType(), replaceWith, max?.toDopeType())

fun arrayReplace(
    array: TypeExpression<ArrayType<NumberType>>,
    toReplace: Number,
    replaceWith: Number,
    max: Number? = null,
) = arrayReplace(array, toReplace.toDopeType(), replaceWith.toDopeType(), max?.toDopeType())

fun arrayReplace(
    array: TypeExpression<ArrayType<BooleanType>>,
    toReplace: TypeExpression<BooleanType>,
    replaceWith: Boolean,
    max: Number? = null,
) = arrayReplace(array, toReplace, replaceWith.toDopeType(), max?.toDopeType())

fun arrayReplace(
    array: TypeExpression<ArrayType<BooleanType>>,
    toReplace: Boolean,
    replaceWith: TypeExpression<BooleanType>,
    max: Number? = null,
) = arrayReplace(array, toReplace.toDopeType(), replaceWith, max?.toDopeType())

fun arrayReplace(
    array: TypeExpression<ArrayType<BooleanType>>,
    toReplace: Boolean,
    replaceWith: Boolean,
    max: Number? = null,
) = arrayReplace(array, toReplace.toDopeType(), replaceWith.toDopeType(), max?.toDopeType())
