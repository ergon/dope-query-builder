package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArrayIntersectExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayIntersect
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("stringArrayIntersect")
fun arrayIntersect(
    firstArray: CMJsonList<String>,
    secondArray: CMJsonList<String>,
    vararg additionalArrays: CMJsonList<String>,
): ArrayIntersectExpression<StringType> = arrayIntersect(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("numberArrayIntersect")
fun arrayIntersect(
    firstArray: CMJsonList<Number>,
    secondArray: CMJsonList<Number>,
    vararg additionalArrays: CMJsonList<Number>,
): ArrayIntersectExpression<NumberType> = arrayIntersect(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("booleanArrayIntersect")
fun arrayIntersect(
    firstArray: CMJsonList<Boolean>,
    secondArray: CMJsonList<Boolean>,
    vararg additionalArrays: CMJsonList<Boolean>,
): ArrayIntersectExpression<BooleanType> = arrayIntersect(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)
