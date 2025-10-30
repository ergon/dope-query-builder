package ch.ergon.dope.mongo.integrationTest

import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

const val DATABASE_NAME = "testdb"

object TestMongoDatabase {
    private val container = MongoDBContainer(DockerImageName.parse("mongo:7.0")).apply {
        start()
    }

    val mongoClient = MongoClients.create(container.connectionString)
    val database: MongoDatabase = mongoClient.getDatabase(DATABASE_NAME)

    val users = UnaliasedBucket("users")
    val orders = UnaliasedBucket("orders")

    val name = Field<StringType>("name", users)
    val age = Field<NumberType>("age", users)
    val email = Field<StringType>("email", users)
    val id = Field<StringType>("_id", users)
    val userId = Field<StringType>("userId", orders)
    val status = Field<StringType>("status", orders)

    init {
        initDatabase()
    }

    fun resetDatabase() {
        database.getCollection(users.name).drop()
        database.getCollection(orders.name).drop()
        initDatabase()
    }

    private fun initDatabase() {
        val usersCollection = database.getCollection(users.name)
        val ordersCollection = database.getCollection(orders.name)

        usersCollection.insertMany(
            listOf(
                Document(
                    mapOf(
                        "name" to "John Smith",
                        "age" to 30,
                        "email" to "john@gmail.com",
                        "role" to "admin",
                        "city" to "Zurich",
                    ),
                ),
                Document(
                    mapOf(
                        "name" to "Alice Brown",
                        "age" to 25,
                        "email" to "alice@example.com",
                        "role" to "user",
                        "city" to "Berlin",
                    ),
                ),
                Document(
                    mapOf(
                        "name" to "Mike Taylor",
                        "age" to 35,
                        "email" to "mike@example.com",
                        "role" to "user",
                        "city" to "Paris",
                    ),
                ),
            ),
        )

        val aliceId = usersCollection.find(Document("name", "Alice Brown")).first()!!["_id"]
        val mikeId = usersCollection.find(Document("name", "Mike Taylor")).first()!!["_id"]

        ordersCollection.insertMany(
            listOf(
                Document(
                    mapOf(
                        "userId" to aliceId,
                        "product" to "Laptop",
                        "amount" to 1200,
                        "status" to "shipped",
                    ),
                ),
                Document(
                    mapOf(
                        "userId" to mikeId,
                        "product" to "Phone",
                        "amount" to 800,
                        "status" to null,
                    ),
                ),
            ),
        )
    }
}
