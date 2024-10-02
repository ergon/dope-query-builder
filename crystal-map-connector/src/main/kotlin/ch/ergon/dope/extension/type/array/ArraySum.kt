package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArraySumExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySum
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberArraySum")
fun arraySum(array: CMJsonList<Number>): ArraySumExpression<NumberType> =
    arraySum(array.toDopeType())
