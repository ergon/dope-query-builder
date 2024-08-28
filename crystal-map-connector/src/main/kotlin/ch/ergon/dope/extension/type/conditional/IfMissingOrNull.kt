package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.coalesce
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.ifMissingOrNull
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("ifCMNumberFieldIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMField<out Number>,
    secondExpression: CMField<out Number>,
    vararg additionalExpressions: CMField<out Number>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringFieldIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMField<String>,
    secondExpression: CMField<String>,
    vararg additionalExpressions: CMField<String>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanFieldIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMField<Boolean>,
    secondExpression: CMField<Boolean>,
    vararg additionalExpressions: CMField<Boolean>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMNumberListIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMList<out Number>,
    secondExpression: CMList<out Number>,
    vararg additionalExpressions: CMList<out Number>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringListIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMList<String>,
    secondExpression: CMList<String>,
    vararg additionalExpressions: CMList<String>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanListIsMissingOrNull")
fun ifMissingOrNull(
    firstExpression: CMList<Boolean>,
    secondExpression: CMList<Boolean>,
    vararg additionalExpressions: CMList<Boolean>,
) = ifMissingOrNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMNumberField")
fun coalesce(
    firstExpression: CMField<out Number>,
    secondExpression: CMField<out Number>,
    vararg additionalExpressions: CMField<out Number>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMStringField")
fun coalesce(
    firstExpression: CMField<String>,
    secondExpression: CMField<String>,
    vararg additionalExpressions: CMField<String>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMBooleanField")
fun coalesce(
    firstExpression: CMField<Boolean>,
    secondExpression: CMField<Boolean>,
    vararg additionalExpressions: CMField<Boolean>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMNumberList")
fun coalesce(
    firstExpression: CMList<out Number>,
    secondExpression: CMList<out Number>,
    vararg additionalExpressions: CMList<out Number>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMStringList")
fun coalesce(
    firstExpression: CMList<String>,
    secondExpression: CMList<String>,
    vararg additionalExpressions: CMList<String>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("coalesceCMBooleanField")
fun coalesce(
    firstExpression: CMList<Boolean>,
    secondExpression: CMList<Boolean>,
    vararg additionalExpressions: CMList<Boolean>,
) = coalesce(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
