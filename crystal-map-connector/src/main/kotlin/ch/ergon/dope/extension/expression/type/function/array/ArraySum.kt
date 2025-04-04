package ch.ergon.dope.extension.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.function.array.ArraySumExpression
import ch.ergon.dope.resolvable.expression.type.function.array.arraySum
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberArraySum")
fun arraySum(array: CMJsonList<Number>): ArraySumExpression<NumberType> =
    arraySum(array.toDopeType())
