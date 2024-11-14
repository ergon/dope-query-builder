package ch.ergon.dope.integrationTest

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.couchbase.client.kotlin.Cluster
import com.couchbase.client.kotlin.query.execute
import kotlinx.coroutines.runBlocking
import org.testcontainers.couchbase.BucketDefinition
import org.testcontainers.couchbase.CouchbaseContainer
import org.testcontainers.utility.DockerImageName
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

const val BUCKET = "testBucket"

object TestCouchbaseDatabase {
    private val container = CouchbaseContainer(
        DockerImageName.parse("couchbase/server:community-7.6.2"),
    )
    val cluster: Cluster
    val testBucket = UnaliasedBucket(BUCKET)
    val idField = Field<NumberType>("id", testBucket.name)
    val typeField = Field<StringType>("type", testBucket.name)
    val nameField = Field<StringType>("name", testBucket.name)
    val isActiveField = Field<BooleanType>("isActive", testBucket.name)
    val orderNumberField = Field<StringType>("orderNumber", testBucket.name)
    val deliveryDateField = Field<StringType>("deliveryDate", testBucket.name)

    init {
        initContainer()
        cluster = Cluster.connect(
            container.connectionString,
            container.username,
            container.password,
        )
        initDatabase()
    }

    fun resetDatabase() {
        runBlocking {
            cluster.waitUntilReady(15.seconds).query("DELETE FROM $BUCKET").execute()
        }
        initDatabase()
    }

    private fun initContainer() {
        container.withBucket(BucketDefinition(BUCKET))
        container.start()
    }

    private fun initDatabase() {
        val collection = cluster.bucket(BUCKET).defaultCollection()
        tryUntil {
            runBlocking {
                cluster.waitUntilReady(15.seconds)
                for (i in 1..5) {
                    collection.upsert(
                        id = "employee:$i",
                        mapOf(
                            "id" to i,
                            "type" to "employee",
                            "name" to "employee$i",
                            "isActive" to true,
                        ),
                    )
                    collection.upsert(
                        id = "client:$i",
                        mapOf(
                            "id" to i,
                            "type" to "client",
                            "name" to "client$i",
                            "isActive" to (i % 2 == 0), // clients with even numbers are active
                        ),
                    )
                    collection.upsert(
                        id = "order:$i",
                        mapOf(
                            "id" to i,
                            "orderNumber" to "order$i",
                            "type" to "order",
                            "client" to "client:$i",
                            "employee" to "employee:$i",
                            "deliveryDate" to null,
                        ),
                    )
                }
                assertEquals(15, cluster.query("SELECT COUNT(*) FROM $BUCKET").execute().valueAs<Number>())
            }
        }
    }
}
