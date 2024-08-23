package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.function.comparison.leastOf
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

@JvmName("leastOfCMNumberField")
fun leastOf(
    firstExpression: CMField<out Number>,
    secondExpression: CMField<out Number>,
    vararg additionalExpressions: CMField<out Number>,
) = leastOf(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("leastOfCMStringField")
fun leastOf(
    firstExpression: CMField<String>,
    secondExpression: CMField<String>,
    vararg additionalExpressions: CMField<String>,
) = leastOf(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
