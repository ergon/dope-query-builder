package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArraySumExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arraySum
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberArraySum")
fun arraySum(array: CMList<Number>): ArraySumExpression<NumberType> =
    arraySum(array.toDopeType())

@JvmName("stringArraySum")
fun arraySum(array: CMList<String>): ArraySumExpression<StringType> =
    arraySum(array.toDopeType())

@JvmName("booleanArraySum")
fun arraySum(array: CMList<Boolean>): ArraySumExpression<BooleanType> =
    arraySum(array.toDopeType())
