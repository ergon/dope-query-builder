package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.ifMissing
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("ifCMNumberFieldIsMissing")
fun ifMissing(
    firstExpression: CMField<out Number>,
    secondExpression: CMField<out Number>,
    vararg additionalExpressions: CMField<out Number>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringFieldIsMissing")
fun ifMissing(
    firstExpression: CMField<String>,
    secondExpression: CMField<String>,
    vararg additionalExpressions: CMField<String>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanFieldIsMissing")
fun ifMissing(
    firstExpression: CMField<Boolean>,
    secondExpression: CMField<Boolean>,
    vararg additionalExpressions: CMField<Boolean>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMNumberStringIsMissing")
fun ifMissing(
    firstExpression: CMList<out Number>,
    secondExpression: CMList<out Number>,
    vararg additionalExpressions: CMList<out Number>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMStringListIsMissing")
fun ifMissing(
    firstExpression: CMList<String>,
    secondExpression: CMList<String>,
    vararg additionalExpressions: CMList<String>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("ifCMBooleanListIsMissing")
fun ifMissing(
    firstExpression: CMList<Boolean>,
    secondExpression: CMList<Boolean>,
    vararg additionalExpressions: CMList<Boolean>,
) = ifMissing(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
