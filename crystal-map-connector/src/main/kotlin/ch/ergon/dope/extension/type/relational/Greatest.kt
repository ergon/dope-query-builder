package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.greatestOf
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

@JvmName("greatestOfCMNumberField")
fun greatestOf(
    firstExpression: CMField<out Number>,
    secondExpression: CMField<out Number>,
    vararg additionalExpressions: CMField<out Number>,
) = greatestOf(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)

@JvmName("greatestOfCMStringField")
fun greatestOf(
    firstExpression: CMField<String>,
    secondExpression: CMField<String>,
    vararg additionalExpressions: CMField<String>,
) = greatestOf(
    firstExpression.toDopeType(),
    secondExpression.toDopeType(),
    *additionalExpressions.map { it.toDopeType() }.toTypedArray(),
)
