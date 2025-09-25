package ch.ergon.dope.mongo

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.build.QueryResolver
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.WhereClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

object MongoOldResolver : QueryResolver<MongoOldDopeQuery> {
    override fun resolve(manager: DopeQueryManager<MongoOldDopeQuery>, resolvable: Resolvable): MongoOldDopeQuery =
        when (resolvable) {
            is SelectClause -> {
                val firstDopeQuery = resolvable.expression.toDopeQuery(manager)
                val restDopeQueries = resolvable.expressions.map { it.toDopeQuery(manager) }
                val all = listOf(firstDopeQuery, *restDopeQueries.toTypedArray())
                MongoOldDopeQuery(
                    // In Mongo, selection maps to projections
                    queryString = "",
                    projections = all.joinToString(
                        prefix = "{ ",
                        postfix = ", \"_id\": 0 }",
                    ) { "${it.queryString}: 1" },
                    namedParams = firstDopeQuery.namedParams.merge(
                        *restDopeQueries.map { it.namedParams }.toTypedArray(),
                    ),
                )
            }

            is WhereClause -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(manager)
                val whereDopeQuery = resolvable.whereExpression.toDopeQuery(manager)
                MongoOldDopeQuery(
                    queryString = whereDopeQuery.queryString,
                    namedParams = parentDopeQuery.namedParams.merge(whereDopeQuery.namedParams),
                    projections = parentDopeQuery.projections,
                )
            }

            is SelectOrderByClause<*> -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(manager)
                val orderExpressionDopeQuery = resolvable.orderExpression.toDopeQuery(manager)
                val additionalOrderExpressionDopeQuery = resolvable.additionalOrderExpressions.map { it.toDopeQuery(manager) }
                val orderExpressions = listOf(orderExpressionDopeQuery) + additionalOrderExpressionDopeQuery
                MongoOldDopeQuery(
                    queryString = parentDopeQuery.queryString,
                    namedParams = parentDopeQuery.namedParams.merge(*orderExpressions.map { it.namedParams }.toTypedArray()),
                    projections = parentDopeQuery.projections,
                    sort = orderExpressions.joinToString(prefix = "{ ", postfix = " }") { it.queryString },
                )
            }

            is OrderExpression -> {
                val expressionDopeQuery = resolvable.expression.toDopeQuery(manager)
                val orderTypeString = when (resolvable.orderByType) {
                    null, OrderType.ASC -> "1"
                    OrderType.DESC -> "-1"
                }
                MongoOldDopeQuery(
                    queryString = "${expressionDopeQuery.queryString} : $orderTypeString",
                    namedParams = expressionDopeQuery.namedParams,
                )
            }

            is EqualsExpression<*> -> {
                val leftDopeQuery = resolvable.left.toDopeQuery(manager)
                val rightDopeQuery = resolvable.right.toDopeQuery(manager)
                MongoOldDopeQuery(
                    queryString = "{ ${leftDopeQuery.queryString} : { \"\$eq\": ${rightDopeQuery.queryString} } }",
                    namedParams = leftDopeQuery.namedParams.merge(rightDopeQuery.namedParams),
                )
            }

            is Field<*> -> {
                MongoOldDopeQuery(queryString = "\"${resolvable.name}\"")
            }

            is NumberPrimitive -> {
                MongoOldDopeQuery(queryString = resolvable.value.toString())
            }

            else -> TODO("not yet implemented: $resolvable")
        }
}

fun main() {
    val uri = "mongodb://root:secret@localhost:27017/?authSource=admin"
    val mongoClient = MongoClients.create(uri)
    val database: MongoDatabase = mongoClient.getDatabase("test")
    val collection: MongoCollection<Document> = database.getCollection("testcol")

    val name = Field<StringType>("name", "")
    val age = Field<NumberType>("age", "")

    val query = QueryBuilder
        .select(name)
        .where(age.isEqualTo(50))
        .orderBy(name, OrderType.DESC)
        .build(MongoOldResolver)
    println(query.queryString)
    println(query.projections)
    println(query.sort)
    val queryDoc = Document.parse(query.queryString)
    val queryPro = Document.parse(query.projections)
    val querySort = Document.parse(query.sort)
    val result = collection
        .find(
            queryDoc,
        ).projection(
            queryPro,
        ).sort(
            querySort,
        )
        .toList()

    println(result)
}
