package ch.ergon.dope.integrationTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testKeySpace
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.countAsterisk
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.keyspace.KeySpace
import ch.ergon.dope.resolvable.keyspace.UnaliasedKeySpace
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

object TestCouchbaseDatabase {
    val container = CouchbaseContainer(
        DockerImageName.parse("couchbase/server:latest"),
    )
    val cluster: Cluster
    val testKeySpace = UnaliasedKeySpace(BUCKET)
    val testAppOrderAuditKeySpace = UnaliasedKeySpace(BUCKET, "app", "order_audit")
    val idField = Field<NumberType>("id", testKeySpace)
    val typeField = Field<StringType>("type", testKeySpace)
    val nameField = Field<StringType>("name", testKeySpace)
    val isActiveField = Field<BooleanType>("isActive", testKeySpace)
    val orderNumberField = Field<StringType>("orderNumber", testKeySpace)
    val deliveryDateField = Field<StringType>("deliveryDate", testKeySpace)
    val quantitiesField = Field<ArrayType<NumberType>>("quantities", testKeySpace)
    val detailsField = Field<ObjectType>("details", testKeySpace)

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
        cluster.query("CREATE SCOPE `$BUCKET`.`app` IF NOT EXISTS").execute()
        cluster.query("CREATE COLLECTION `$BUCKET`.`app`.`order_audit` IF NOT EXISTS").execute()
    }

    private fun initDatabase() {
        val bucket = cluster.bucket(BUCKET)
        val collection = bucket.defaultCollection()

        ensureAuditCollections()
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
                        ).from(testKeySpace).build(CouchbaseResolver()).queryString,
                    ).execute().valueAs<Number>(),
                )
            }
        }
    }
}

fun QueryResult.toMapValues(rowNumber: Int = 0, isSelectAsterisk: Boolean = false, keyspace: KeySpace = testKeySpace) =
    if (isSelectAsterisk) {
        this.rows.map { it.contentAs<Map<String, Map<String, Any>>>()[keyspace.bucket]!! }[rowNumber]
    } else {
        this.rows.map { it.contentAs<Map<String, Any>>() }[rowNumber]
    }

fun QueryResult.toRawValues(rowNumber: Int = 0) = this.rows.map { it.contentAs<Any>() }[rowNumber]

fun QueryResult.toSingleValue() = this.valueAs<Any>()
