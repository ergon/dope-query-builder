package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.function.numeric.AbsoluteExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcCosineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcSineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcTangent2Expression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcTangentExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.CeilingExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.CosineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.DegreesExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.EulerExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ExponentExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.FloorExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.LogExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.LogNaturalisExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.PiExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.PowerExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.RadiansExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.RandomExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.RoundExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.SignExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.SineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.SquareRootExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.TangentExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.TruncationExpression

internal fun ExpressionResolver.resolveNumericFunction(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is AbsoluteExpression -> unaryOperator("\$abs", expression.value)
        is ArcCosineExpression -> unaryOperator("\$acos", expression.value)
        is ArcSineExpression -> unaryOperator("\$asin", expression.value)
        is ArcTangentExpression -> unaryOperator("\$atan", expression.value)
        is CeilingExpression -> unaryOperator("\$ceil", expression.value)
        is CosineExpression -> unaryOperator("\$cos", expression.value)
        is DegreesExpression -> unaryOperator("\$radiansToDegrees", expression.value)
        is ExponentExpression -> unaryOperator("\$exp", expression.value)
        is FloorExpression -> unaryOperator("\$floor", expression.value)
        is LogExpression -> unaryOperator("\$log10", expression.value)
        is LogNaturalisExpression -> unaryOperator("\$ln", expression.value)
        is RadiansExpression -> unaryOperator("\$degreesToRadians", expression.value)
        is SineExpression -> unaryOperator("\$sin", expression.value)
        is SquareRootExpression -> unaryOperator("\$sqrt", expression.value)
        is TangentExpression -> unaryOperator("\$tan", expression.value)

        is ArcTangent2Expression -> binaryOperator("\$atan2", expression.divisor, expression.dividend)
        is PowerExpression -> binaryOperator("\$pow", expression.base, expression.exponent)

        is RoundExpression -> placeOperator("\$round", expression.value, expression.digits)
        is TruncationExpression -> placeOperator("\$trunc", expression.value, expression.digits)

        is EulerExpression -> fragment("{ \"\$exp\": 1 }")
        is PiExpression -> fragment("{ \"\$literal\": 3.141592653589793 }")

        is SignExpression -> {
            val valueFragment = render(expression.value)
            fragment("{ \"\$cmp\": [${valueFragment.queryString}, 0] }")
        }
        is RandomExpression -> fragment("{ \"\$rand\": {} }")

        else -> null
    }

private fun ExpressionResolver.unaryOperator(
    operator: String,
    value: Expression<*>,
): MongoDopeQuery.ExpressionFragment {
    val valueFragment = render(value)
    return fragment("{ \"$operator\": ${valueFragment.queryString} }")
}

private fun ExpressionResolver.placeOperator(
    operator: String,
    value: Expression<*>,
    digits: Expression<*>?,
): MongoDopeQuery.ExpressionFragment {
    val valueFragment = render(value)
    return if (digits == null) {
        fragment("{ \"$operator\": [${valueFragment.queryString}] }")
    } else {
        val digitsFragment = render(digits)
        fragment("{ \"$operator\": [${valueFragment.queryString}, ${digitsFragment.queryString}] }")
    }
}
