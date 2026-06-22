package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.arithmetic.AdditionExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.DivisionExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.ModuloExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.MultiplicationExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.NegationExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.SubtractionExpression

internal fun ExpressionResolver.resolveArithmetic(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is AdditionExpression -> binaryOperator("\$add", expression.left, expression.right)
        is SubtractionExpression -> binaryOperator("\$subtract", expression.left, expression.right)
        is MultiplicationExpression -> binaryOperator("\$multiply", expression.left, expression.right)
        is DivisionExpression -> binaryOperator("\$divide", expression.left, expression.right)
        is ModuloExpression -> binaryOperator("\$mod", expression.left, expression.right)

        is NegationExpression -> {
            val operand = render(expression.numberExpression)
            fragment("{ \"\$multiply\": [${operand.queryString}, -1] }")
        }

        else -> null
    }
