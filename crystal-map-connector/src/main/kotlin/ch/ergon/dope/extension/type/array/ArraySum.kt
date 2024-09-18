package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArraySumExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySum
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberArraySum")
fun arraySum(array: CMJsonList<Number>): ArraySumExpression<NumberType> =
    arraySum(array.toDopeType())

@JvmName("stringArraySum")
fun arraySum(array: CMJsonList<String>): ArraySumExpression<StringType> =
    arraySum(array.toDopeType())

@JvmName("booleanArraySum")
fun arraySum(array: CMJsonList<Boolean>): ArraySumExpression<BooleanType> =
    arraySum(array.toDopeType())
