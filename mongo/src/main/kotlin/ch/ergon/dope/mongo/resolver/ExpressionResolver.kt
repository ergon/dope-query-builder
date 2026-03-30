package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.mongo.queryString
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.StringPrimitive
import ch.ergon.dope.resolvable.expression.type.logic.AndExpression
import ch.ergon.dope.resolvable.expression.type.logic.OrExpression
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotNullExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNullExpression
import ch.ergon.dope.resolvable.expression.type.relational.LikeExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotEqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotLikeExpression

interface ExpressionResolver : AbstractMongoResolver {
    fun resolve(expression: Expression<*>): MongoDopeQuery =
        when (expression) {
            is EqualsExpression<*> -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                val qs = if (expression.left is Field<*> && expression.right is Field<*>) {
                    val leftName = (expression.left as Field<*>).name
                    val rightName = (expression.right as Field<*>).name
                    "{ \$expr: { \$eq: [\"\$$leftName\", \"\$$rightName\"] } }"
                } else {
                    "{ ${leftDopeQuery.queryString} : { \"\$eq\": ${rightDopeQuery.queryString} } }"
                }
                MongoDopeQuery.ExpressionFragment(
                    queryString = qs,
                    parameters = leftDopeQuery.parameters.merge(rightDopeQuery.parameters),
                )
            }

            is NotEqualsExpression<*> -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                val qs = if (expression.left is Field<*> && expression.right is Field<*>) {
                    val leftName = (expression.left as Field<*>).name
                    val rightName = (expression.right as Field<*>).name
                    "{ \$expr: { \$ne: [\"\$$leftName\", \"\$$rightName\"] } }"
                } else {
                    "{ ${leftDopeQuery.queryString} : { \"\$ne\": ${rightDopeQuery.queryString} } }"
                }
                MongoDopeQuery.ExpressionFragment(
                    queryString = qs,
                    parameters = leftDopeQuery.parameters.merge(rightDopeQuery.parameters),
                )
            }

            is LikeExpression -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                MongoDopeQuery.ExpressionFragment(
                    queryString = "{ ${leftDopeQuery.queryString} : { \"\$regex\": ${rightDopeQuery.queryString} } }",
                    parameters = leftDopeQuery.parameters.merge(rightDopeQuery.parameters),
                )
            }

            is NotLikeExpression -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                MongoDopeQuery.ExpressionFragment(
                    queryString = "{ ${leftDopeQuery.queryString} : { \"\$not\": { \"\$regex\": ${rightDopeQuery.queryString} } } }",
                    parameters = leftDopeQuery.parameters.merge(rightDopeQuery.parameters),
                )
            }

            is IsNullExpression -> {
                MongoDopeQuery.ExpressionFragment(queryString = "{ \"${expression.field.name}\" : null }")
            }

            is IsNotNullExpression -> {
                MongoDopeQuery.ExpressionFragment(queryString = "{ \"${expression.field.name}\" : { \"\$ne\": null } }")
            }

            is Field<*> -> {
                MongoDopeQuery.ExpressionFragment(queryString = "\"${expression.name}\"")
            }

            is NumberPrimitive -> {
                MongoDopeQuery.ExpressionFragment(queryString = expression.value.toString())
            }

            is AliasedTypeExpression<*> -> {
                val typeExpressionDopeQuery = expression.typeExpression.toDopeQuery(this)
                val string = when (expression.typeExpression) {
                    is Field<*> -> "\"\$${(expression.typeExpression as Field<*>).name}\""
                    else -> typeExpressionDopeQuery.queryString
                }
                MongoDopeQuery.ExpressionFragment(
                    queryString = "\"${expression.alias}\": $string",
                    parameters = typeExpressionDopeQuery.parameters,
                )
            }

            is AndExpression -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                MongoDopeQuery.ExpressionFragment(
                    queryString = "{ \"\$and\": [${leftDopeQuery.queryString}, ${rightDopeQuery.queryString}] }",
                    parameters = leftDopeQuery.parameters.merge(rightDopeQuery.parameters),
                )
            }

            is OrExpression -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                MongoDopeQuery.ExpressionFragment(
                    queryString = "{ \"\$or\": [${leftDopeQuery.queryString}, ${rightDopeQuery.queryString}] }",
                    parameters = leftDopeQuery.parameters.merge(rightDopeQuery.parameters),
                )
            }

            is StringPrimitive -> {
                val escaped = expression.value.replace("\\", "\\\\")
                MongoDopeQuery.ExpressionFragment(queryString = "\"$escaped\"")
            }

            else -> TODO("not yet implemented: $expression")
        }
}
