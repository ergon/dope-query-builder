package ch.ergon.dope.extension.expression.single.type.function.array

import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayConcatExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.arrayConcat
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("stringArrayConcat")
fun arrayConcat(
    firstArray: CMJsonList<String>,
    secondArray: CMJsonList<String>,
    vararg additionalArrays: CMJsonList<String>,
): ArrayConcatExpression<StringType> = arrayConcat(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("numberArrayConcat")
fun arrayConcat(
    firstArray: CMJsonList<Number>,
    secondArray: CMJsonList<Number>,
    vararg additionalArrays: CMJsonList<Number>,
): ArrayConcatExpression<NumberType> = arrayConcat(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("booleanArrayConcat")
fun arrayConcat(
    firstArray: CMJsonList<Boolean>,
    secondArray: CMJsonList<Boolean>,
    vararg additionalArrays: CMJsonList<Boolean>,
): ArrayConcatExpression<BooleanType> = arrayConcat(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("objectArrayConcat")
fun arrayConcat(
    firstArray: CMObjectList<Schema>,
    secondArray: CMObjectList<Schema>,
    vararg additionalArrays: CMObjectList<Schema>,
): ArrayConcatExpression<ObjectType> = arrayConcat(
    firstArray.toDopeType(),
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)
