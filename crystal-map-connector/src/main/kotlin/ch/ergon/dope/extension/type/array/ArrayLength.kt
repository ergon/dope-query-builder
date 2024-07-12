package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayLengthExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayLength
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberArrayLength")
fun arrayLength(array: CMList<Number>): ArrayLengthExpression<NumberType> =
    arrayLength(array.toDopeType())

@JvmName("stringArrayLength")
fun arrayLength(array: CMList<String>): ArrayLengthExpression<StringType> =
    arrayLength(array.toDopeType())

@JvmName("booleanArrayLength")
fun arrayLength(array: CMList<Boolean>): ArrayLengthExpression<BooleanType> =
    arrayLength(array.toDopeType())
