package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayContains
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberArrayContains")
fun arrayContains(array: CMList<Number>, value: CMField<Number>): ArrayContainsExpression<NumberType> =
    arrayContains(array.toDopeType(), value.toDopeType())

fun arrayContains(array: CMList<Number>, value: Number): ArrayContainsExpression<NumberType> =
    arrayContains(array.toDopeType(), value.toDopeType())

@JvmName("stringArrayContains")
fun arrayContains(array: CMList<String>, value: CMField<String>): ArrayContainsExpression<StringType> =
    arrayContains(array.toDopeType(), value.toDopeType())

fun arrayContains(array: CMList<String>, value: String): ArrayContainsExpression<StringType> =
    arrayContains(array.toDopeType(), value.toDopeType())

@JvmName("booleanArrayContains")
fun arrayContains(array: CMList<Boolean>, value: CMField<Boolean>): ArrayContainsExpression<BooleanType> =
    arrayContains(array.toDopeType(), value.toDopeType())

fun arrayContains(array: CMList<Boolean>, value: Boolean): ArrayContainsExpression<BooleanType> =
    arrayContains(array.toDopeType(), value.toDopeType())
