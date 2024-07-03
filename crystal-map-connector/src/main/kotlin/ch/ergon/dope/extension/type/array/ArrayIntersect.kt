package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayIntersectExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayIntersect
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMList

@JvmName("stringArrayIntersect")
fun arrayIntersect(
    firstArray: CMList<String>,
    secondArray: CMList<String>,
    vararg arrays: CMList<String>,
): ArrayIntersectExpression<StringType> =
    arrayIntersect(firstArray.toDopeType(), secondArray.toDopeType(), *arrays.map { it.toDopeType() }.toTypedArray())

@JvmName("numberArrayIntersect")
fun arrayIntersect(
    firstArray: CMList<Number>,
    secondArray: CMList<Number>,
    vararg arrays: CMList<Number>,
): ArrayIntersectExpression<NumberType> =
    arrayIntersect(firstArray.toDopeType(), secondArray.toDopeType(), *arrays.map { it.toDopeType() }.toTypedArray())

@JvmName("booleanArrayIntersect")
fun arrayIntersect(
    firstArray: CMList<Boolean>,
    secondArray: CMList<Boolean>,
    vararg arrays: CMList<Boolean>,
): ArrayIntersectExpression<BooleanType> =
    arrayIntersect(firstArray.toDopeType(), secondArray.toDopeType(), *arrays.map { it.toDopeType() }.toTypedArray())
