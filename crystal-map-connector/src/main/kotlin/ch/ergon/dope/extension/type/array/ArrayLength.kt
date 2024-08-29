package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArrayLengthExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayLength
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberArrayLength")
fun arrayLength(array: CMJsonList<Number>): ArrayLengthExpression<NumberType> =
    arrayLength(array.toDopeType())

@JvmName("stringArrayLength")
fun arrayLength(array: CMJsonList<String>): ArrayLengthExpression<StringType> =
    arrayLength(array.toDopeType())

@JvmName("booleanArrayLength")
fun arrayLength(array: CMJsonList<Boolean>): ArrayLengthExpression<BooleanType> =
    arrayLength(array.toDopeType())
