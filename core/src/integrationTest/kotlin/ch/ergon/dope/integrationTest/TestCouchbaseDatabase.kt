package ch.ergon.dope.integrationTest

import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.couchbase.client.kotlin.Cluster
import com.couchbase.client.kotlin.query.QueryResult
import com.couchbase.client.kotlin.query.execute
import kotlinx.coroutines.runBlocking
import org.testcontainers.couchbase.BucketDefinition
import org.testcontainers.couchbase.CouchbaseContainer
import org.testcontainers.utility.DockerImageName
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

const val BUCKET = "testBucket"
const val MAX_RETRIES = 5
const val MAX_TIMEOUT_IN_SECONDS = 15

object TestCouchbaseDatabase {
    private val container = CouchbaseContainer(
        DockerImageName.parse("couchbase/server:latest"),
    )
    val cluster: Cluster
    val testBucket = UnaliasedBucket(BUCKET)
    val idField = Field<NumberType>("id", testBucket.name)
    val typeField = Field<StringType>("type", testBucket.name)
    val nameField = Field<StringType>("name", testBucket.name)
    val isActiveField = Field<BooleanType>("isActive", testBucket.name)
    val orderNumberField = Field<StringType>("orderNumber", testBucket.name)
    val deliveryDateField = Field<StringType>("deliveryDate", testBucket.name)
    val quantitiesField = Field<ArrayType<NumberType>>("quantities", testBucket.name)

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
            cluster.waitUntilReady(MAX_TIMEOUT_IN_SECONDS.seconds).query("DELETE FROM $BUCKET").execute()
        }
        initDatabase()
    }

    private fun initContainer() {
        container.withStartupAttempts(MAX_RETRIES).withBucket(BucketDefinition(BUCKET))
        container.start()
    }

    private fun initDatabase() {
        val collection = cluster.bucket(BUCKET).defaultCollection()
        tryUntil {
            runBlocking {
                cluster.waitUntilReady(MAX_TIMEOUT_IN_SECONDS.seconds)
                (1..5).forEach { i ->
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
                            "quantities" to listOf(1, 2, 3),
                        ),
                    )
                }
                assertEquals(15, cluster.query("SELECT COUNT(*) FROM $BUCKET").execute().valueAs<Number>())
            }
        }
    }
}

fun QueryResult.toMapValues(rowNumber: Int = 0, isSelectAsterisk: Boolean = false, bucket: Bucket = testBucket) =
    if (isSelectAsterisk) {
        this.rows.map { it.contentAs<Map<String, Map<String, Any>>>()[bucket.name]!! }[rowNumber]
    } else {
        this.rows.map { it.contentAs<Map<String, Any>>() }[rowNumber]
    }

fun QueryResult.toRawValues(rowNumber: Int = 0) = this.rows.map { it.contentAs<Any>() }[rowNumber]

fun QueryResult.toSingleValue() = this.valueAs<Any>()
