package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.ifNull
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("ifCMNumberFieldIsNull")
fun ifNull(
    firstExpression: CMJsonField<out Number>,
    secondExpression: CMJsonField<out Number>,
    vararg additionalExpressions: CMJsonField<out Number>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringFieldIsNull")
fun ifNull(
    firstExpression: CMJsonField<String>,
    secondExpression: CMJsonField<String>,
    vararg additionalExpressions: CMJsonField<String>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanFieldIsNull")
fun ifNull(
    firstExpression: CMJsonField<Boolean>,
    secondExpression: CMJsonField<Boolean>,
    vararg additionalExpressions: CMJsonField<Boolean>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanFieldIsNull")
fun ifNull(
    firstExpression: CMObjectField<Schema>,
    secondExpression: CMObjectField<Schema>,
    vararg additionalExpressions: CMObjectField<Schema>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMNumberListIsNull")
fun ifNull(
    firstExpression: CMJsonList<out Number>,
    secondExpression: CMJsonList<out Number>,
    vararg additionalExpressions: CMJsonList<out Number>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringListIsNull")
fun ifNull(
    firstExpression: CMJsonList<String>,
    secondExpression: CMJsonList<String>,
    vararg additionalExpressions: CMJsonList<String>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanListIsNull")
fun ifNull(
    firstExpression: CMJsonList<Boolean>,
    secondExpression: CMJsonList<Boolean>,
    vararg additionalExpressions: CMJsonList<Boolean>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMObjectListIsNull")
fun ifNull(
    firstExpression: CMObjectList<Schema>,
    secondExpression: CMObjectList<Schema>,
    vararg additionalExpressions: CMObjectList<Schema>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
