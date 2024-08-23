package ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class Nvl2Expression<T : ValidType>(
    private val initialExpression: UnaliasedExpression<out ValidType>,
    private val valueIfExists: UnaliasedExpression<T>,
    private val valueIfNotExists: UnaliasedExpression<T>,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val initialExpressionDopeQuery = initialExpression.toDopeQuery()
        val valueIfExistsDopeQuery = valueIfExists.toDopeQuery()
        val valueIfNotExistsDopeQuery = valueIfNotExists.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(
                "NVL2",
                initialExpressionDopeQuery.queryString,
                valueIfExistsDopeQuery.queryString,
                valueIfNotExistsDopeQuery.queryString,
            ),
            parameters = initialExpressionDopeQuery.parameters + valueIfExistsDopeQuery.parameters +
                valueIfNotExistsDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<T>,
    valueIfNotExists: UnaliasedExpression<T>,
) = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists)

fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<NumberType>,
    valueIfNotExists: Number,
) = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<StringType>,
    valueIfNotExists: String,
) = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<BooleanType>,
    valueIfNotExists: Boolean,
) = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: Number,
    valueIfNotExists: UnaliasedExpression<NumberType>,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: String,
    valueIfNotExists: UnaliasedExpression<StringType>,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: Boolean,
    valueIfNotExists: UnaliasedExpression<BooleanType>,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: Number,
    valueIfNotExists: Number,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: String,
    valueIfNotExists: String,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: Boolean,
    valueIfNotExists: Boolean,
) = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())
