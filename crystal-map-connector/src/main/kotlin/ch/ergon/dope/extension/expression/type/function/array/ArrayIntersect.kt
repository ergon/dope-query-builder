package ch.ergon.dope.extension.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.function.array.ArrayIntersectExpression
import ch.ergon.dope.resolvable.expression.type.function.array.intersect
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("stringArrayIntersect")
fun CMJsonList<String>.intersect(
    secondArray: CMJsonList<String>,
    vararg additionalArrays: CMJsonList<String>,
): ArrayIntersectExpression<StringType> = toDopeType().intersect(
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("numberArrayIntersect")
fun CMJsonList<Number>.intersect(
    secondArray: CMJsonList<Number>,
    vararg additionalArrays: CMJsonList<Number>,
): ArrayIntersectExpression<NumberType> = toDopeType().intersect(
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("booleanArrayIntersect")
fun CMJsonList<Boolean>.intersect(
    secondArray: CMJsonList<Boolean>,
    vararg additionalArrays: CMJsonList<Boolean>,
): ArrayIntersectExpression<BooleanType> = toDopeType().intersect(
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)
