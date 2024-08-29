package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArrayConcatExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayConcat
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMList

@JvmName("stringArrayConcat")
fun arrayConcat(
    firstArray: CMList<String>,
    secondArray: CMList<String>,
    vararg additionalArrays: CMList<String>,
): ArrayConcatExpression<StringType> = arrayConcat(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("numberArrayConcat")
fun arrayConcat(
    firstArray: CMList<Number>,
    secondArray: CMList<Number>,
    vararg additionalArrays: CMList<Number>,
): ArrayConcatExpression<NumberType> = arrayConcat(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("booleanArrayConcat")
fun arrayConcat(
    firstArray: CMList<Boolean>,
    secondArray: CMList<Boolean>,
    vararg additionalArrays: CMList<Boolean>,
): ArrayConcatExpression<BooleanType> = arrayConcat(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)
