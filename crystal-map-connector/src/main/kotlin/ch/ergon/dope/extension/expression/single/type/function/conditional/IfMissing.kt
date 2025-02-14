package ch.ergon.dope.extension.expression.single.type.function.conditional

import ch.ergon.dope.resolvable.expression.single.type.function.conditional.ifMissing
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("ifCMNumberFieldIsMissing")
fun ifMissing(
    firstExpression: CMJsonField<out Number>,
    secondExpression: CMJsonField<out Number>,
    vararg additionalExpressions: CMJsonField<out Number>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringFieldIsMissing")
fun ifMissing(
    firstExpression: CMJsonField<String>,
    secondExpression: CMJsonField<String>,
    vararg additionalExpressions: CMJsonField<String>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanFieldIsMissing")
fun ifMissing(
    firstExpression: CMJsonField<Boolean>,
    secondExpression: CMJsonField<Boolean>,
    vararg additionalExpressions: CMJsonField<Boolean>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMObjectFieldIsMissing")
fun ifMissing(
    firstExpression: CMObjectField<Schema>,
    secondExpression: CMObjectField<Schema>,
    vararg additionalExpressions: CMObjectField<Schema>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMNumberStringIsMissing")
fun ifMissing(
    firstExpression: CMJsonList<out Number>,
    secondExpression: CMJsonList<out Number>,
    vararg additionalExpressions: CMJsonList<out Number>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringListIsMissing")
fun ifMissing(
    firstExpression: CMJsonList<String>,
    secondExpression: CMJsonList<String>,
    vararg additionalExpressions: CMJsonList<String>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanListIsMissing")
fun ifMissing(
    firstExpression: CMJsonList<Boolean>,
    secondExpression: CMJsonList<Boolean>,
    vararg additionalExpressions: CMJsonList<Boolean>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMObjectListIsMissing")
fun ifMissing(
    firstExpression: CMObjectList<Schema>,
    secondExpression: CMObjectList<Schema>,
    vararg additionalExpressions: CMObjectList<Schema>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
