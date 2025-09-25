package ch.ergon.dope.mongo

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.build.QueryResolver
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.mongodb.client.MongoClients
import org.bson.Document

object MongoAggregateResolver : QueryResolver<MongoDopeQuery> {
    override fun resolve(manager: DopeQueryManager<MongoDopeQuery>, resolvable: Resolvable): MongoDopeQuery =
        when (resolvable) {
            is Clause -> ClauseResolver.resolve(manager, resolvable)

            is OrderExpression -> {
                val expressionDopeQuery = resolvable.expression.toDopeQuery(manager)
                val orderTypeString = when (resolvable.orderByType) {
                    null, OrderType.ASC -> "1"
                    OrderType.DESC -> "-1"
                }
                MongoDopeQuery(
                    queryString = "${expressionDopeQuery.queryString} : $orderTypeString",
                    namedParams = expressionDopeQuery.namedParams,
                )
            }

            is EqualsExpression<*> -> {
                val leftDopeQuery = resolvable.left.toDopeQuery(manager)
                val rightDopeQuery = resolvable.right.toDopeQuery(manager)
                val queryString = if (resolvable.left is Field<*> && resolvable.right is Field<*>) {
                    "{ \$expr: { \$eq: [${leftDopeQuery.queryString}, \"$$${rightDopeQuery.queryString.drop(1)}] } }"
                } else {
                    "{ ${leftDopeQuery.queryString} : { \"\$eq\": ${rightDopeQuery.queryString} } }"
                }
                MongoDopeQuery(
                    queryString = queryString,
                    namedParams = leftDopeQuery.namedParams.merge(rightDopeQuery.namedParams),
                )
            }

            is Field<*> -> {
                MongoDopeQuery(queryString = "\"${resolvable.name}\"")
            }

            is NumberPrimitive -> {
                MongoDopeQuery(queryString = resolvable.value.toString())
            }

            is AliasedTypeExpression<*> -> {
                val typeExpressionDopeQuery = resolvable.typeExpression.toDopeQuery(manager)
                MongoDopeQuery(
                    queryString = "${resolvable.alias}: \"$${typeExpressionDopeQuery.queryString.drop(1)}",
                    namedParams = typeExpressionDopeQuery.namedParams,
                )
            }

            else -> TODO("not yet implemented: $resolvable")
        }
}

fun main() {
    val uri = "mongodb://root:secret@localhost:27017/?authSource=admin"
    val mongoClient = MongoClients.create(uri)

    val name = Field<StringType>("name", "")
    val age = Field<NumberType>("age", "")
    val age2 = Field<NumberType>("age2", "")
    val fromable = UnaliasedBucket("testcol")
    val to = UnaliasedBucket("testcol2")

    val query = QueryBuilder
        .select(name, age)
        .from(fromable)
        .join(to, condition = age2.isEqualTo(age))
//        .where(age.isEqualTo(50))
//        .orderBy(name, OrderType.DESC)
//        .limit(1)
//        .offset(1)
        .build(MongoAggregateResolver)
    println(query.queryString.split(",\n"))
    val queryDoc = query.queryString.split(",\n").map { Document.parse(it) }
    val result = mongoClient.getDatabase("test").getCollection(query.bucket!!.name)
        .aggregate(queryDoc)
        .toList()

    println(result)
}
