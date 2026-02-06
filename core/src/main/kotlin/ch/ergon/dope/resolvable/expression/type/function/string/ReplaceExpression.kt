package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class ReplaceExpression(
    val inStr: TypeExpression<StringType>,
    val searchStr: TypeExpression<StringType>,
    val replace: TypeExpression<StringType>,
    val numberOfInstances: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, searchStr, replace, numberOfInstances))

fun TypeExpression<StringType>.replace(
    searchStr: TypeExpression<StringType>,
    replace: TypeExpression<StringType>,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = ReplaceExpression(this, searchStr, replace, numberOfInstances)

fun TypeExpression<StringType>.replace(
    searchStr: TypeExpression<StringType>,
    replace: TypeExpression<StringType>,
    numberOfInstances: Int,
) = replace(searchStr, replace, numberOfInstances.toDopeType())

fun TypeExpression<StringType>.replace(
    searchStr: TypeExpression<StringType>,
    replace: String,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(searchStr, replace.toDopeType(), numberOfInstances)

fun TypeExpression<StringType>.replace(
    searchStr: TypeExpression<StringType>,
    replace: String,
    numberOfInstances: Int,
) = replace(searchStr, replace.toDopeType(), numberOfInstances.toDopeType())

fun TypeExpression<StringType>.replace(
    searchStr: String,
    replace: TypeExpression<StringType>,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(searchStr.toDopeType(), replace, numberOfInstances)

fun TypeExpression<StringType>.replace(
    searchStr: String,
    replace: TypeExpression<StringType>,
    numberOfInstances: Int,
) = replace(searchStr.toDopeType(), replace, numberOfInstances.toDopeType())

fun TypeExpression<StringType>.replace(
    searchStr: String,
    replace: String,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(searchStr.toDopeType(), replace.toDopeType(), numberOfInstances)

fun TypeExpression<StringType>.replace(
    searchStr: String,
    replace: String,
    numberOfInstances: Int,
) = replace(searchStr.toDopeType(), replace.toDopeType(), numberOfInstances.toDopeType())

fun String.replace(
    searchStr: TypeExpression<StringType>,
    replace: TypeExpression<StringType>,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = toDopeType().replace(searchStr, replace, numberOfInstances)

fun String.replace(
    searchStr: TypeExpression<StringType>,
    replace: TypeExpression<StringType>,
    numberOfInstances: Int,
) = toDopeType().replace(searchStr, replace, numberOfInstances.toDopeType())

fun String.replace(
    searchStr: TypeExpression<StringType>,
    replace: String,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = toDopeType().replace(searchStr, replace.toDopeType(), numberOfInstances)

fun String.replace(
    searchStr: TypeExpression<StringType>,
    replace: String,
    numberOfInstances: Int,
) = toDopeType().replace(searchStr, replace.toDopeType(), numberOfInstances.toDopeType())

fun String.replace(
    searchStr: String,
    replace: TypeExpression<StringType>,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = toDopeType().replace(searchStr.toDopeType(), replace, numberOfInstances)

fun String.replace(
    searchStr: String,
    replace: TypeExpression<StringType>,
    numberOfInstances: Int,
) = toDopeType().replace(searchStr.toDopeType(), replace, numberOfInstances.toDopeType())

fun String.replace(
    searchStr: String,
    replace: String,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = toDopeType().replace(searchStr.toDopeType(), replace.toDopeType(), numberOfInstances)

fun String.replace(
    searchStr: String,
    replace: String,
    numberOfInstances: Int,
) = toDopeType().replace(searchStr.toDopeType(), replace.toDopeType(), numberOfInstances.toDopeType())
