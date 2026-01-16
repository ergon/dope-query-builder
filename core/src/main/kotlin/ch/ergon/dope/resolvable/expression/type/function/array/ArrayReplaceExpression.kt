package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ArrayReplaceExpression<T : ValidType>(
    override val array: TypeExpression<ArrayType<T>>,
    val toReplace: TypeExpression<T>,
    val replaceWith: TypeExpression<T>,
    val max: TypeExpression<NumberType>? = null,
) : ArrayFunctionExpression<T>(array, listOfNotNull(toReplace, replaceWith, max))
fun <T : ValidType> TypeExpression<ArrayType<T>>.replace(
    toReplace: TypeExpression<T>,
    replaceWith: TypeExpression<T>,
    max: TypeExpression<NumberType>? = null,
) = ArrayReplaceExpression(this, toReplace, replaceWith, max)

fun TypeExpression<ArrayType<StringType>>.replace(
    toReplace: TypeExpression<StringType>,
    replaceWith: String,
    max: Number? = null,
) = replace(toReplace, replaceWith.toDopeType(), max?.toDopeType())

fun TypeExpression<ArrayType<StringType>>.replace(
    toReplace: String,
    replaceWith: TypeExpression<StringType>,
    max: Number? = null,
) = replace(toReplace.toDopeType(), replaceWith, max?.toDopeType())

fun TypeExpression<ArrayType<StringType>>.replace(
    toReplace: String,
    replaceWith: String,
    max: Number? = null,
) = replace(toReplace.toDopeType(), replaceWith.toDopeType(), max?.toDopeType())

fun TypeExpression<ArrayType<NumberType>>.replace(
    toReplace: TypeExpression<NumberType>,
    replaceWith: Number,
    max: Number? = null,
) = replace(toReplace, replaceWith.toDopeType(), max?.toDopeType())

fun TypeExpression<ArrayType<NumberType>>.replace(
    toReplace: Number,
    replaceWith: TypeExpression<NumberType>,
    max: Number? = null,
) = replace(toReplace.toDopeType(), replaceWith, max?.toDopeType())

fun TypeExpression<ArrayType<NumberType>>.replace(
    toReplace: Number,
    replaceWith: Number,
    max: Number? = null,
) = replace(toReplace.toDopeType(), replaceWith.toDopeType(), max?.toDopeType())

fun TypeExpression<ArrayType<BooleanType>>.replace(
    toReplace: TypeExpression<BooleanType>,
    replaceWith: Boolean,
    max: Number? = null,
) = replace(toReplace, replaceWith.toDopeType(), max?.toDopeType())

fun TypeExpression<ArrayType<BooleanType>>.replace(
    toReplace: Boolean,
    replaceWith: TypeExpression<BooleanType>,
    max: Number? = null,
) = replace(toReplace.toDopeType(), replaceWith, max?.toDopeType())

fun TypeExpression<ArrayType<BooleanType>>.replace(
    toReplace: Boolean,
    replaceWith: Boolean,
    max: Number? = null,
) = replace(toReplace.toDopeType(), replaceWith.toDopeType(), max?.toDopeType())

fun <T : ValidType> ISelectOffsetClause<T>.replace(
    toReplace: TypeExpression<T>,
    replaceWith: TypeExpression<T>,
    max: TypeExpression<NumberType>? = null,
) = asExpression().replace(toReplace, replaceWith, max)

fun ISelectOffsetClause<StringType>.replace(
    toReplace: TypeExpression<StringType>,
    replaceWith: String,
    max: Number? = null,
) = asExpression().replace(toReplace, replaceWith.toDopeType(), max?.toDopeType())

fun ISelectOffsetClause<StringType>.replace(
    toReplace: String,
    replaceWith: TypeExpression<StringType>,
    max: Number? = null,
) = asExpression().replace(toReplace.toDopeType(), replaceWith, max?.toDopeType())

fun ISelectOffsetClause<StringType>.replace(
    toReplace: String,
    replaceWith: String,
    max: Number? = null,
) = asExpression().replace(toReplace.toDopeType(), replaceWith.toDopeType(), max?.toDopeType())

fun ISelectOffsetClause<NumberType>.replace(
    toReplace: TypeExpression<NumberType>,
    replaceWith: Number,
    max: Number? = null,
) = asExpression().replace(toReplace, replaceWith.toDopeType(), max?.toDopeType())

fun ISelectOffsetClause<NumberType>.replace(
    toReplace: Number,
    replaceWith: TypeExpression<NumberType>,
    max: Number? = null,
) = asExpression().replace(toReplace.toDopeType(), replaceWith, max?.toDopeType())

fun ISelectOffsetClause<NumberType>.replace(
    toReplace: Number,
    replaceWith: Number,
    max: Number? = null,
) = asExpression().replace(toReplace.toDopeType(), replaceWith.toDopeType(), max?.toDopeType())

fun ISelectOffsetClause<BooleanType>.replace(
    toReplace: TypeExpression<BooleanType>,
    replaceWith: Boolean,
    max: Number? = null,
) = asExpression().replace(toReplace, replaceWith.toDopeType(), max?.toDopeType())

fun ISelectOffsetClause<BooleanType>.replace(
    toReplace: Boolean,
    replaceWith: TypeExpression<BooleanType>,
    max: Number? = null,
) = asExpression().replace(toReplace.toDopeType(), replaceWith, max?.toDopeType())

fun ISelectOffsetClause<BooleanType>.replace(
    toReplace: Boolean,
    replaceWith: Boolean,
    max: Number? = null,
) = asExpression().replace(toReplace.toDopeType(), replaceWith.toDopeType(), max?.toDopeType())
