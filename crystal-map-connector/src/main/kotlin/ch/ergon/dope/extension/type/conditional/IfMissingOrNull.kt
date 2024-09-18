package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.coalesce
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.ifMissingOrNull
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("ifCMNumberFieldIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMJsonField<out Number>,
    secondExpression: CMJsonField<out Number>,
    vararg additionalExpressions: CMJsonField<out Number>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringFieldIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMJsonField<String>,
    secondExpression: CMJsonField<String>,
    vararg additionalExpressions: CMJsonField<String>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanFieldIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMJsonField<Boolean>,
    secondExpression: CMJsonField<Boolean>,
    vararg additionalExpressions: CMJsonField<Boolean>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMNumberListIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMJsonList<out Number>,
    secondExpression: CMJsonList<out Number>,
    vararg additionalExpressions: CMJsonList<out Number>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringListIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMJsonList<String>,
    secondExpression: CMJsonList<String>,
    vararg additionalExpressions: CMJsonList<String>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanListIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMJsonList<Boolean>,
    secondExpression: CMJsonList<Boolean>,
    vararg additionalExpressions: CMJsonList<Boolean>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMNumberField")
fun coalesce(
    firstExpression: CMJsonField<out Number>,
    secondExpression: CMJsonField<out Number>,
    vararg additionalExpressions: CMJsonField<out Number>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMStringField")
fun coalesce(
    firstExpression: CMJsonField<String>,
    secondExpression: CMJsonField<String>,
    vararg additionalExpressions: CMJsonField<String>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMBooleanField")
fun coalesce(
    firstExpression: CMJsonField<Boolean>,
    secondExpression: CMJsonField<Boolean>,
    vararg additionalExpressions: CMJsonField<Boolean>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMNumberList")
fun coalesce(
    firstExpression: CMJsonList<out Number>,
    secondExpression: CMJsonList<out Number>,
    vararg additionalExpressions: CMJsonList<out Number>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMStringList")
fun coalesce(
    firstExpression: CMJsonList<String>,
    secondExpression: CMJsonList<String>,
    vararg additionalExpressions: CMJsonList<String>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMBooleanField")
fun coalesce(
    firstExpression: CMJsonList<Boolean>,
    secondExpression: CMJsonList<Boolean>,
    vararg additionalExpressions: CMJsonList<Boolean>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
