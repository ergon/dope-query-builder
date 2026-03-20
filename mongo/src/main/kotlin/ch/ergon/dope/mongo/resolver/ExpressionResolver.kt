package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
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
                val queryString = if (expression.left is Field<*> && expression.right is Field<*>) {
                    val leftName = (expression.left as Field<*>).name
                    val rightName = (expression.right as Field<*>).name
                    "{ \$expr: { \$eq: [\"\$$leftName\", \"\$$rightName\"] } }"
                } else {
                    "{ ${leftDopeQuery.queryString} : { \"\$eq\": ${rightDopeQuery.queryString} } }"
                }
                MongoDopeQuery(
                    queryString = queryString,
                    namedParameters = leftDopeQuery.namedParameters.merge(rightDopeQuery.namedParameters),
                )
            }

            is NotEqualsExpression<*> -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                val queryString = if (expression.left is Field<*> && expression.right is Field<*>) {
                    val leftName = (expression.left as Field<*>).name
                    val rightName = (expression.right as Field<*>).name
                    "{ \$expr: { \$ne: [\"\$$leftName\", \"\$$rightName\"] } }"
                } else {
                    "{ ${leftDopeQuery.queryString} : { \"\$ne\": ${rightDopeQuery.queryString} } }"
                }
                MongoDopeQuery(
                    queryString = queryString,
                    namedParameters = leftDopeQuery.namedParameters.merge(rightDopeQuery.namedParameters),
                )
            }

            is LikeExpression -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                MongoDopeQuery(
                    queryString = "{ ${leftDopeQuery.queryString} : { \"\$regex\": ${rightDopeQuery.queryString} } }",
                    namedParameters = leftDopeQuery.namedParameters.merge(rightDopeQuery.namedParameters),
                )
            }

            is NotLikeExpression -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                MongoDopeQuery(
                    queryString = "{ ${leftDopeQuery.queryString} : { \"\$not\": { \"\$regex\": ${rightDopeQuery.queryString} } } }",
                    namedParameters = leftDopeQuery.namedParameters.merge(rightDopeQuery.namedParameters),
                )
            }

            is IsNullExpression -> {
                MongoDopeQuery(queryString = "{ \"${expression.field.name}\" : null }")
            }

            is IsNotNullExpression -> {
                MongoDopeQuery(queryString = "{ \"${expression.field.name}\" : { \"\$ne\": null } }")
            }

            is Field<*> -> {
                MongoDopeQuery(queryString = "\"${expression.name}\"")
            }

            is NumberPrimitive -> {
                MongoDopeQuery(queryString = expression.value.toString())
            }

            is AliasedTypeExpression<*> -> {
                val typeExpressionDopeQuery = expression.typeExpression.toDopeQuery(this)
                val string = when (expression.typeExpression) {
                    is Field<*> -> "\"\$${(expression.typeExpression as Field<*>).name}\""
                    else -> typeExpressionDopeQuery.queryString
                }
                MongoDopeQuery(
                    queryString = "\"${expression.alias}\": $string",
                    namedParameters = typeExpressionDopeQuery.namedParameters,
                )
            }

            is AndExpression -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                MongoDopeQuery(
                    queryString = "{ \"\$and\": [${leftDopeQuery.queryString}, ${rightDopeQuery.queryString}] }",
                    namedParameters = leftDopeQuery.namedParameters.merge(rightDopeQuery.namedParameters),
                )
            }

            is OrExpression -> {
                val leftDopeQuery = expression.left.toDopeQuery(this)
                val rightDopeQuery = expression.right.toDopeQuery(this)
                MongoDopeQuery(
                    queryString = "{ \"\$or\": [${leftDopeQuery.queryString}, ${rightDopeQuery.queryString}] }",
                    namedParameters = leftDopeQuery.namedParameters.merge(rightDopeQuery.namedParameters),
                )
            }

            is StringPrimitive -> {
                val escaped = expression.value.replace("\\", "\\\\")
                MongoDopeQuery(queryString = "\"$escaped\"")
            }

            else -> TODO("not yet implemented: $expression")
        }
}
