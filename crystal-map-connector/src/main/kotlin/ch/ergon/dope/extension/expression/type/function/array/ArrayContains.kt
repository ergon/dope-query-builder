package ch.ergon.dope.extension.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.function.array.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.type.function.array.contains
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberArrayContains")
fun CMJsonList<Number>.contains(value: CMJsonField<Number>): ArrayContainsExpression<NumberType> =
    toDopeType().contains(value.toDopeType())

fun CMJsonList<Number>.contains(value: Number): ArrayContainsExpression<NumberType> =
    toDopeType().contains(value.toDopeType())

@JvmName("numberArrayContains")
fun <Convertable : Any, JsonType : Number> CMConverterList<Convertable, JsonType>.contains(value: Convertable):
    ArrayContainsExpression<NumberType> = toDopeType().contains(value.toDopeType(this))

@JvmName("stringArrayContains")
fun CMJsonList<String>.contains(value: CMJsonField<String>): ArrayContainsExpression<StringType> =
    toDopeType().contains(value.toDopeType())

fun CMJsonList<String>.contains(value: String): ArrayContainsExpression<StringType> =
    toDopeType().contains(value.toDopeType())

@JvmName("stringArrayContains")
fun <Convertable : Any> CMConverterList<Convertable, String>.contains(value: Convertable):
    ArrayContainsExpression<StringType> = toDopeType().contains(value.toDopeType(this))

@JvmName("booleanArrayContains")
fun CMJsonList<Boolean>.contains(value: CMJsonField<Boolean>): ArrayContainsExpression<BooleanType> =
    toDopeType().contains(value.toDopeType())

fun CMJsonList<Boolean>.contains(value: Boolean): ArrayContainsExpression<BooleanType> =
    toDopeType().contains(value.toDopeType())

@JvmName("booleanArrayContains")
fun <Convertable : Any> CMConverterList<Convertable, Boolean>.contains(value: Convertable):
    ArrayContainsExpression<BooleanType> = toDopeType().contains(value.toDopeType(this))
