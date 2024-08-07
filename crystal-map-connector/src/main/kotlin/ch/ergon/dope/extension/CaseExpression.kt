package ch.ergon.dope.extension

import ch.ergon.dope.resolvable.WhenThenCondition
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.case
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMType

fun case(
    expression: CMType,
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: Expression? = null,
) = case(
    expression = expression.toDopeType(),
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase,
)

fun case(
    expression: Expression,
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
    expression: CMType? = null,
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: CMType? = null,
) = case(
    expression = expression?.toDopeType(),
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase?.toDopeType(),
)
