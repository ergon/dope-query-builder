package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.ifNull
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("ifCMNumberFieldIsNull")
fun ifNull(
    firstExpression: CMField<out Number>,
    secondExpression: CMField<out Number>,
    vararg additionalExpressions: CMField<out Number>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringFieldIsNull")
fun ifNull(
    firstExpression: CMField<String>,
    secondExpression: CMField<String>,
    vararg additionalExpressions: CMField<String>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanFieldIsNull")
fun ifNull(
    firstExpression: CMField<Boolean>,
    secondExpression: CMField<Boolean>,
    vararg additionalExpressions: CMField<Boolean>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMNumberListIsNull")
fun ifNull(
    firstExpression: CMList<out Number>,
    secondExpression: CMList<out Number>,
    vararg additionalExpressions: CMList<out Number>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringListIsNull")
fun ifNull(
    firstExpression: CMList<String>,
    secondExpression: CMList<String>,
    vararg additionalExpressions: CMList<String>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanListIsNull")
fun ifNull(
    firstExpression: CMList<Boolean>,
    secondExpression: CMList<Boolean>,
    vararg additionalExpressions: CMList<Boolean>,
) = ifNull(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
