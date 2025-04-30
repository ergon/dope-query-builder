package ch.ergon.dope.extension.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.function.array.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.type.function.array.arrayContains
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberArrayContains")
fun arrayContains(array: CMJsonList<Number>, value: CMJsonField<Number>): ArrayContainsExpression<NumberType> =
    arrayContains(array.toDopeType(), value.toDopeType())

fun arrayContains(array: CMJsonList<Number>, value: Number): ArrayContainsExpression<NumberType> =
    arrayContains(array.toDopeType(), value.toDopeType())

@JvmName("numberArrayContains")
fun <Convertable : Any, JsonType : Number> arrayContains(array: CMConverterList<Convertable, JsonType>, value: Convertable):
    ArrayContainsExpression<NumberType> = arrayContains(array.toDopeType(), value.toDopeType(array))

@JvmName("stringArrayContains")
fun arrayContains(array: CMJsonList<String>, value: CMJsonField<String>): ArrayContainsExpression<StringType> =
    arrayContains(array.toDopeType(), value.toDopeType())

fun arrayContains(array: CMJsonList<String>, value: String): ArrayContainsExpression<StringType> =
    arrayContains(array.toDopeType(), value.toDopeType())

@JvmName("stringArrayContains")
fun <Convertable : Any> arrayContains(array: CMConverterList<Convertable, String>, value: Convertable):
    ArrayContainsExpression<StringType> = arrayContains(array.toDopeType(), value.toDopeType(array))

@JvmName("booleanArrayContains")
fun arrayContains(array: CMJsonList<Boolean>, value: CMJsonField<Boolean>): ArrayContainsExpression<BooleanType> =
    arrayContains(array.toDopeType(), value.toDopeType())

fun arrayContains(array: CMJsonList<Boolean>, value: Boolean): ArrayContainsExpression<BooleanType> =
    arrayContains(array.toDopeType(), value.toDopeType())

@JvmName("booleanArrayContains")
fun <Convertable : Any> arrayContains(array: CMConverterList<Convertable, Boolean>, value: Convertable):
    ArrayContainsExpression<BooleanType> = arrayContains(array.toDopeType(), value.toDopeType(array))
