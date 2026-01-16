package ch.ergon.dope.extension.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.function.array.ArrayConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.array.concat
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("stringArrayConcat")
fun CMJsonList<String>.concat(
    secondArray: CMJsonList<String>,
    vararg additionalArrays: CMJsonList<String>,
): ArrayConcatExpression<StringType> = toDopeType().concat(
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("numberArrayConcat")
fun CMJsonList<Number>.concat(
    secondArray: CMJsonList<Number>,
    vararg additionalArrays: CMJsonList<Number>,
): ArrayConcatExpression<NumberType> = toDopeType().concat(
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("booleanArrayConcat")
fun CMJsonList<Boolean>.concat(
    secondArray: CMJsonList<Boolean>,
    vararg additionalArrays: CMJsonList<Boolean>,
): ArrayConcatExpression<BooleanType> = toDopeType().concat(
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("objectArrayConcat")
fun CMObjectList<Schema>.concat(
    secondArray: CMObjectList<Schema>,
    vararg additionalArrays: CMObjectList<Schema>,
): ArrayConcatExpression<ObjectType> = toDopeType().concat(
    secondArray.toDopeType(),
    *additionalArrays.map { it.toDopeType() }.toTypedArray(),
)
