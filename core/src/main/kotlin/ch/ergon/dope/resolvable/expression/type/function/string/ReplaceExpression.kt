package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class ReplaceExpression(
    inStr: TypeExpression<StringType>,
    searchStr: TypeExpression<StringType>,
    replace: TypeExpression<StringType>,
    numberOfInstances: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>(
    "REPLACE",
    inStr,
    searchStr,
    replace,
    numberOfInstances,
)

fun replace(
    inStr: TypeExpression<StringType>,
    searchStr: TypeExpression<StringType>,
    replace: TypeExpression<StringType>,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = ReplaceExpression(inStr, searchStr, replace, numberOfInstances)

fun replace(
    inStr: TypeExpression<StringType>,
    searchStr: TypeExpression<StringType>,
    replace: TypeExpression<StringType>,
    numberOfInstances: Int,
) = replace(inStr, searchStr, replace, numberOfInstances.toDopeType())

fun replace(
    inStr: TypeExpression<StringType>,
    searchStr: TypeExpression<StringType>,
    replace: String,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(inStr, searchStr, replace.toDopeType(), numberOfInstances)

fun replace(
    inStr: TypeExpression<StringType>,
    searchStr: TypeExpression<StringType>,
    replace: String,
    numberOfInstances: Int,
) = replace(inStr, searchStr, replace.toDopeType(), numberOfInstances.toDopeType())

fun replace(
    inStr: TypeExpression<StringType>,
    searchStr: String,
    replace: TypeExpression<StringType>,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(inStr, searchStr.toDopeType(), replace, numberOfInstances)

fun replace(
    inStr: TypeExpression<StringType>,
    searchStr: String,
    replace: TypeExpression<StringType>,
    numberOfInstances: Int,
) = replace(inStr, searchStr.toDopeType(), replace, numberOfInstances.toDopeType())

fun replace(
    inStr: TypeExpression<StringType>,
    searchStr: String,
    replace: String,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(inStr, searchStr.toDopeType(), replace.toDopeType(), numberOfInstances)

fun replace(
    inStr: TypeExpression<StringType>,
    searchStr: String,
    replace: String,
    numberOfInstances: Int,
) = replace(inStr, searchStr.toDopeType(), replace.toDopeType(), numberOfInstances.toDopeType())

fun replace(
    inStr: String,
    searchStr: TypeExpression<StringType>,
    replace: TypeExpression<StringType>,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(inStr.toDopeType(), searchStr, replace, numberOfInstances)

fun replace(
    inStr: String,
    searchStr: TypeExpression<StringType>,
    replace: TypeExpression<StringType>,
    numberOfInstances: Int,
) = replace(inStr.toDopeType(), searchStr, replace, numberOfInstances.toDopeType())

fun replace(
    inStr: String,
    searchStr: TypeExpression<StringType>,
    replace: String,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(inStr.toDopeType(), searchStr, replace.toDopeType(), numberOfInstances)

fun replace(
    inStr: String,
    searchStr: TypeExpression<StringType>,
    replace: String,
    numberOfInstances: Int,
) = replace(inStr.toDopeType(), searchStr, replace.toDopeType(), numberOfInstances.toDopeType())

fun replace(
    inStr: String,
    searchStr: String,
    replace: TypeExpression<StringType>,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(inStr.toDopeType(), searchStr.toDopeType(), replace, numberOfInstances)

fun replace(
    inStr: String,
    searchStr: String,
    replace: TypeExpression<StringType>,
    numberOfInstances: Int,
) = replace(inStr.toDopeType(), searchStr.toDopeType(), replace, numberOfInstances.toDopeType())

fun replace(
    inStr: String,
    searchStr: String,
    replace: String,
    numberOfInstances: TypeExpression<NumberType>? = null,
) = replace(inStr.toDopeType(), searchStr.toDopeType(), replace.toDopeType(), numberOfInstances)

fun replace(
    inStr: String,
    searchStr: String,
    replace: String,
    numberOfInstances: Int,
) = replace(inStr.toDopeType(), searchStr.toDopeType(), replace.toDopeType(), numberOfInstances.toDopeType())
