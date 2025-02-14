package ch.ergon.dope.extension.expression.single.type.function.array

import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayLengthExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.arrayLength
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberArrayLength")
fun arrayLength(array: CMJsonList<Number>): ArrayLengthExpression<NumberType> =
    arrayLength(array.toDopeType())

@JvmName("stringArrayLength")
fun arrayLength(array: CMJsonList<String>): ArrayLengthExpression<StringType> =
    arrayLength(array.toDopeType())

@JvmName("booleanArrayLength")
fun arrayLength(array: CMJsonList<Boolean>): ArrayLengthExpression<BooleanType> =
    arrayLength(array.toDopeType())

@JvmName("objectArrayLength")
fun arrayLength(array: CMObjectList<Schema>): ArrayLengthExpression<ObjectType> =
    arrayLength(array.toDopeType())
