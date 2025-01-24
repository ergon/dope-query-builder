package ch.ergon.dope.integrationTest

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.cluster
import com.couchbase.client.kotlin.query.QueryParameters
import com.couchbase.client.kotlin.query.QueryScanConsistency
import com.couchbase.client.kotlin.query.execute
import kotlinx.coroutines.runBlocking
import java.time.Instant.now
import kotlin.time.Duration.Companion.seconds

val CLUSTER_TIMEOUT = 15.seconds
val SCAN_CONSISTENCY_TIMEOUT = 60.seconds

abstract class BaseIntegrationTest {
    fun queryWithoutParameters(dopeQuery: DopeQuery) =
        queryExecution(
            queryString = dopeQuery.queryString,
        )

    fun queryWithNamedParameters(dopeQuery: DopeQuery) =
        queryExecution(
            queryString = dopeQuery.queryString,
            queryParameters = QueryParameters.named(dopeQuery.parameters.namedParameters),
        )

    fun queryWithPositionalParameters(dopeQuery: DopeQuery) =
        queryExecution(
            queryString = dopeQuery.queryString,
            queryParameters = QueryParameters.positional(dopeQuery.parameters.positionalParameters),
        )

    private fun queryExecution(queryString: String, queryParameters: QueryParameters = QueryParameters.None) =
        runBlocking {
            cluster.waitUntilReady(CLUSTER_TIMEOUT).query(
                statement = queryString,
                parameters = queryParameters,
                consistency = QueryScanConsistency.requestPlus(SCAN_CONSISTENCY_TIMEOUT),
            ).execute()
        }
}

fun <T> tryUntil(assertion: () -> T): T {
    val start = now()
    val end = start.plusSeconds(120L)
    var lastException: Throwable? = null

    while (now().isBefore(end)) {
        try {
            return assertion()
        } catch (e: AssertionError) {
            lastException = e
            Thread.sleep(1000)
        }
    }
    throw lastException ?: Error("")
}
