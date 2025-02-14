package ch.ergon.dope.extension.expression.single.type.function.comparison

import ch.ergon.dope.resolvable.expression.single.type.function.comparison.leastOf
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("leastOfCMNumberField")
fun leastOf(
    firstExpression: CMJsonField<out Number>,
    secondExpression: CMJsonField<out Number>,
    vararg additionalExpressions: CMJsonField<out Number>,
) = leastOf(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("leastOfCMStringField")
fun leastOf(
    firstExpression: CMJsonField<String>,
    secondExpression: CMJsonField<String>,
    vararg additionalExpressions: CMJsonField<String>,
) = leastOf(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
