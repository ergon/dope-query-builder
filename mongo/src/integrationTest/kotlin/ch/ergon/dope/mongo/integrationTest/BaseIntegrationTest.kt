package ch.ergon.dope.mongo.integrationTest

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.mongo.resolver.MongoResolver
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.database
import org.bson.Document

abstract class BaseIntegrationTest {
    fun executeQuery(query: MongoDopeQuery): List<Document> =
        database.getCollection(query.bucket!!.name)
            .aggregate(query.queryString.split(",\n").map { Document.parse(it) })
            .toList()

    val resolver = MongoResolver()
}
