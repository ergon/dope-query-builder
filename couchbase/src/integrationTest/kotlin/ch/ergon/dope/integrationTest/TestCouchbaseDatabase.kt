package ch.ergon.dope.integrationTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.bucket.BucketScope
import ch.ergon.dope.resolvable.bucket.ScopeCollection
import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.countAsterisk
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
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
const val COUCHBASE_IMAGE = "couchbase/server:7.6.4"

object TestCouchbaseDatabase {
    val container = CouchbaseContainer(DockerImageName.parse(COUCHBASE_IMAGE))
    val cluster: Cluster
    val testBucket = UnaliasedBucket(BUCKET)
    val testAppOrderAuditBucket = UnaliasedBucket(BUCKET, BucketScope("app", ScopeCollection("order_audit")))
    val idField = Field<NumberType>("id", testBucket)
    val typeField = Field<StringType>("type", testBucket)
    val nameField = Field<StringType>("name", testBucket)
    val isActiveField = Field<BooleanType>("isActive", testBucket)
    val orderNumberField = Field<StringType>("orderNumber", testBucket)
    val deliveryDateField = Field<StringType>("deliveryDate", testBucket)
    val quantitiesField = Field<ArrayType<NumberType>>("quantities", testBucket)
    val detailsField = Field<ObjectType>("details", testBucket)

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

    private fun ensureAuditCollections() = runBlocking {
        cluster.waitUntilReady(MAX_TIMEOUT_IN_SECONDS.seconds)
        cluster.waitUntilReady(MAX_TIMEOUT_IN_SECONDS.seconds)
        cluster.query("CREATE SCOPE `$BUCKET`.`app` IF NOT EXISTS").execute()
        cluster.query("CREATE COLLECTION `$BUCKET`.`app`.`order_audit` IF NOT EXISTS").execute()
    }

    private fun ensureIndexes() = runBlocking {
        cluster.waitUntilReady(MAX_TIMEOUT_IN_SECONDS.seconds)
        cluster.query("CREATE PRIMARY INDEX IF NOT EXISTS ON `$BUCKET`").execute()
        retryOnFailure { cluster.query("CREATE PRIMARY INDEX IF NOT EXISTS ON `$BUCKET`.`app`.`order_audit`").execute() }
        cluster.query("CREATE INDEX IF NOT EXISTS `ix_order_employee` ON `$BUCKET`(`employee`) WHERE `type` = \"order\"").execute()
        cluster.query("CREATE INDEX IF NOT EXISTS `ix_order_client` ON `$BUCKET`(`client`) WHERE `type` = \"order\"").execute()
    }

    private suspend fun <T> retryOnFailure(maxRetries: Int = MAX_RETRIES, delayMs: Long = 2000, action: suspend () -> T): T {
        repeat(maxRetries - 1) {
            try {
                return action()
            } catch (_: Exception) {
                Thread.sleep(delayMs)
            }
        }
        return action()
    }

    private fun initDatabase() {
        val bucket = cluster.bucket(BUCKET)
        val collection = bucket.defaultCollection()

        ensureAuditCollections()
        ensureIndexes()
        val orderAuditCollection = bucket.scope("app").collection("order_audit")

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
                            "details" to mapOf(
                                "position" to "Engineer",
                                "department" to "Engineering",
                                "email" to "employee$i@company.com",
                            ),
                        ),
                    )

                    collection.upsert(
                        id = "client:$i",
                        mapOf(
                            "id" to i,
                            "type" to "client",
                            "name" to "client$i",
                            "isActive" to (i % 2 == 0), // even-numbered clients are active
                            "contacts" to listOf(
                                mapOf("name" to "Contact A", "email" to "contact.a@client.com"),
                                mapOf("name" to "Contact B", "email" to "contact.b@client.com"),
                            ),
                        ),
                    )

                    val quantities = listOf(1, 2, 3)
                    collection.upsert(
                        id = "order:$i",
                        mapOf(
                            "id" to i,
                            "orderNumber" to "order$i",
                            "type" to "order",
                            "client" to "client:$i",
                            "employee" to "employee:$i",
                            "deliveryDate" to null,
                            "quantities" to quantities,
                        ),
                    )

                    val totalQty = quantities.sum()
                    orderAuditCollection.upsert(
                        id = "order_audit:$i",
                        mapOf(
                            "type" to "order_audit",
                            "orderRef" to "order:$i",
                            "orderNumber" to "order$i",
                            "status" to "CREATED",
                            "changedBy" to "employee:$i",
                            "changedAt" to java.time.Instant.now().toString(),
                            "metrics" to mapOf(
                                "itemCount" to quantities.size,
                                "totalQuantity" to totalQty,
                            ),
                            "events" to listOf(
                                mapOf(
                                    "ts" to java.time.Instant.now().toString(),
                                    "action" to "CREATED",
                                    "by" to "employee:$i",
                                    "note" to "Order initialized",
                                ),
                            ),
                        ),
                    )
                }

                assertEquals(
                    15,
                    cluster.query(
                        QueryBuilder.select(
                            countAsterisk(),
                        ).from(testBucket).build(CouchbaseResolver()).queryString,
                    ).execute().valueAs<Number>(),
                )

                assertEquals(
                    5,
                    cluster.query(
                        QueryBuilder.select(
                            countAsterisk(),
                        ).from(testAppOrderAuditBucket).build(CouchbaseResolver()).queryString,
                    ).execute().valueAs<Number>(),
                )
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
