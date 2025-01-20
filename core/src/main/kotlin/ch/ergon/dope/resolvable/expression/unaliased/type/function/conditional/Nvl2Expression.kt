package ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class Nvl2Expression<T : ValidType>(
    private val initialExpression: TypeExpression<out ValidType>,
    private val valueIfExists: TypeExpression<T>,
    private val valueIfNotExists: TypeExpression<T>,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val initialExpressionDopeQuery = initialExpression.toDopeQuery(manager)
        val valueIfExistsDopeQuery = valueIfExists.toDopeQuery(manager)
        val valueIfNotExistsDopeQuery = valueIfNotExists.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(
                "NVL2",
                initialExpressionDopeQuery.queryString,
                valueIfExistsDopeQuery.queryString,
                valueIfNotExistsDopeQuery.queryString,
            ),
            parameters = initialExpressionDopeQuery.parameters.merge(
                valueIfExistsDopeQuery.parameters,
                valueIfNotExistsDopeQuery.parameters,
            ),
        )
    }
}

fun <T : ValidType> nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: TypeExpression<T>,
    valueIfNotExists: TypeExpression<T>,
) = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists)

fun nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: TypeExpression<NumberType>,
    valueIfNotExists: Number,
) = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: TypeExpression<StringType>,
    valueIfNotExists: String,
) = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: TypeExpression<BooleanType>,
    valueIfNotExists: Boolean,
) = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: Number,
    valueIfNotExists: TypeExpression<NumberType>,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

fun nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: String,
    valueIfNotExists: TypeExpression<StringType>,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

fun nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: Boolean,
    valueIfNotExists: TypeExpression<BooleanType>,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

fun nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: Number,
    valueIfNotExists: Number,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: String,
    valueIfNotExists: String,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: TypeExpression<out ValidType>,
    valueIfExists: Boolean,
    valueIfNotExists: Boolean,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())
