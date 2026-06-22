package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.logic.AndExpression
import ch.ergon.dope.resolvable.expression.type.logic.NotExpression
import ch.ergon.dope.resolvable.expression.type.logic.OrExpression

internal fun ExpressionResolver.resolveLogic(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is AndExpression -> {
            val left = render(expression.left)
            val right = render(expression.right)
            fragment("{ \"\$and\": [${left.queryString}, ${right.queryString}] }")
        }

        is OrExpression -> {
            val left = render(expression.left)
            val right = render(expression.right)
            fragment("{ \"\$or\": [${left.queryString}, ${right.queryString}] }")
        }

        is NotExpression -> {
            val inner = render(expression.expression)
            fragment("{ \"\$not\": [${inner.queryString}] }")
        }

        else -> null
    }
