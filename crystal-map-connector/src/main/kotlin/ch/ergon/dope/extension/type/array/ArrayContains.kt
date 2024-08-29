package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayContains
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberArrayContains")
fun arrayContains(array: CMJsonList<Number>, value: CMJsonField<Number>): ArrayContainsExpression<NumberType> =
    arrayContains(array.toDopeType(), value.toDopeType())

fun arrayContains(array: CMJsonList<Number>, value: Number): ArrayContainsExpression<NumberType> =
    arrayContains(array.toDopeType(), value.toDopeType())

@JvmName("stringArrayContains")
fun arrayContains(array: CMJsonList<String>, value: CMJsonField<String>): ArrayContainsExpression<StringType> =
    arrayContains(array.toDopeType(), value.toDopeType())

fun arrayContains(array: CMJsonList<String>, value: String): ArrayContainsExpression<StringType> =
    arrayContains(array.toDopeType(), value.toDopeType())

@JvmName("booleanArrayContains")
fun arrayContains(array: CMJsonList<Boolean>, value: CMJsonField<Boolean>): ArrayContainsExpression<BooleanType> =
    arrayContains(array.toDopeType(), value.toDopeType())

fun arrayContains(array: CMJsonList<Boolean>, value: Boolean): ArrayContainsExpression<BooleanType> =
    arrayContains(array.toDopeType(), value.toDopeType())
