package ch.ergon.dope.mongo.integrationTest

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.database
import ch.ergon.dope.mongo.resolver.MongoResolver
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import org.bson.Document

abstract class BaseIntegrationTest {
    val resolver = MongoResolver()

    fun executeQuery(query: MongoDopeQuery.Aggregation): List<Document> =
        database.getCollection(query.bucket!!.name)
            .aggregate(query.stages.map { Document.parse(it) })
            .toList()

    fun executeUpdate(query: MongoDopeQuery.Update): UpdateResult =
        database.getCollection(query.bucket.name)
            .updateMany(Document.parse(query.filter), Document.parse(query.updateDocument))

    fun executeDelete(query: MongoDopeQuery.Delete): DeleteResult =
        database.getCollection(query.bucket.name)
            .deleteMany(Document.parse(query.filter))
}
