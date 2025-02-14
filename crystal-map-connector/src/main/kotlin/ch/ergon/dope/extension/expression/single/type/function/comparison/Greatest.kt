package ch.ergon.dope.extension.expression.single.type.function.comparison

import ch.ergon.dope.resolvable.expression.single.type.function.comparison.greatestOf
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("greatestOfCMNumberField")
fun greatestOf(
    firstExpression: CMJsonField<out Number>,
    secondExpression: CMJsonField<out Number>,
    vararg additionalExpressions: CMJsonField<out Number>,
) = greatestOf(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("greatestOfCMStringField")
fun greatestOf(
    firstExpression: CMJsonField<String>,
    secondExpression: CMJsonField<String>,
    vararg additionalExpressions: CMJsonField<String>,
) = greatestOf(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
