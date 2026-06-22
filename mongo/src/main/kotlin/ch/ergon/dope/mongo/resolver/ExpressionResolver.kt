package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.ArrayPrimitive
import ch.ergon.dope.resolvable.expression.type.BooleanPrimitive
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.MISSING
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.ObjectPrimitive
import ch.ergon.dope.resolvable.expression.type.StringPrimitive
import ch.ergon.dope.resolvable.expression.type.TRUE

interface ExpressionResolver : AbstractMongoResolver {
    fun resolve(expression: Expression<*>): MongoDopeQuery =
        resolveLeaf(expression)
            ?: resolveRelational(expression)
            ?: resolveLogic(expression)
            ?: resolveArithmetic(expression)
            ?: resolveStringFunction(expression)
            ?: resolveNumericFunction(expression)
            ?: resolveConditionalFunction(expression)
            ?: resolveComparisonFunction(expression)
            ?: resolveArrayFunction(expression)
            ?: resolveTypeFunction(expression)
            ?: resolveCollection(expression)
            ?: resolveDateFunction(expression)
            ?: resolveObjectFunction(expression)
            ?: TODO("Mongo resolver does not support expression: $expression")

    private fun resolveLeaf(expression: Expression<*>): MongoDopeQuery? =
        when (expression) {
            is Field<*> -> fragment(fieldPath(expression.name))

            is NumberPrimitive -> fragment(expression.value.toString())

            is StringPrimitive -> fragment(stringLiteral(expression.value))

            is BooleanPrimitive -> fragment(expression.value.toString())

            TRUE -> fragment("true")

            FALSE -> fragment("false")

            NULL -> fragment("null")

            MISSING -> fragment("\"\$\$REMOVE\"")

            is ArrayPrimitive<*> -> {
                val constant = constantJson(expression)
                if (constant != null) {
                    fragment("{ \"\$literal\": $constant }")
                } else {
                    val elements = expression.collection.map { render(it) }
                    fragment("[ " + elements.joinToString(", ") { it.queryString } + " ]")
                }
            }

            is ObjectPrimitive -> {
                val constant = constantJson(expression)
                if (constant != null) {
                    fragment("{ \"\$literal\": $constant }")
                } else {
                    val entries = expression.entries.map { entry ->
                        val key = (entry.key as? StringPrimitive)?.value
                            ?: error("Mongo object keys must be string literals")
                        val value = render(entry.value)
                        fieldKey(key) + ": " + value.queryString
                    }
                    fragment("{ " + entries.joinToString(", ") + " }")
                }
            }

            is AliasedTypeExpression<*> -> {
                val inner = render(expression.typeExpression)
                fragment("${fieldKey(expression.alias)}: ${inner.queryString}")
            }

            else -> null
        }
}
