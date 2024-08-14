package ch.ergon.dope.extension

import ch.ergon.dope.resolvable.WhenThenCondition
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.case
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMType

fun case(
    expression: CMType,
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: UnaliasedExpression<out ValidType>? = null,
) = case(
    expression = expression.toDopeType(),
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase,
)

fun case(
    expression: UnaliasedExpression<out ValidType>,
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: CMType? = null,
) = case(
    expression = expression,
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase?.toDopeType(),
)

fun case(
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: CMType? = null,
) = case(
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase?.toDopeType(),
)

fun case(
    expression: CMType,
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: CMType? = null,
) = case(
    expression = expression.toDopeType(),
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase?.toDopeType(),
)
