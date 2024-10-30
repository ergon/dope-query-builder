package ch.ergon.dope.helper

import com.couchbase.client.kotlin.Cluster
import kotlinx.coroutines.runBlocking
import org.testcontainers.couchbase.BucketDefinition
import org.testcontainers.couchbase.CouchbaseContainer
import org.testcontainers.utility.DockerImageName
import kotlin.time.Duration.Companion.seconds

object DBProvider {
    private val container = CouchbaseContainer(
        DockerImageName.parse("couchbase/server:community-7.6.2"),
    )
    var cluster: Cluster

    init {
        container.withBucket(BucketDefinition("testBucket"))
        container.start()
        cluster = Cluster.connect(
            container.connectionString,
            container.username,
            container.password,
        )
        fillDatabase()
    }

    private fun fillDatabase() {
        val collection = cluster.bucket("testBucket").defaultCollection()
        runBlocking {
            cluster.waitUntilReady(10.seconds)
            repeat(10) {
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
                            "items" to listOf("apples", "oranges", "bananas"),
                            "quantities" to listOf(0 + i, 1 + i, 2 + i),
                        ),
                    )
                }
            }
        }
    }
}
