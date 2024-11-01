package ch.ergon.dope.helper

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.DBProvider.cluster
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.couchbase.client.kotlin.query.QueryParameters
import com.couchbase.client.kotlin.query.QueryScanConsistency
import com.couchbase.client.kotlin.query.execute
import kotlinx.coroutines.runBlocking
import java.time.Instant.now
import kotlin.time.Duration.Companion.seconds

abstract class BaseIntegrationTest {
    private val maxTimeout = 15.seconds

    val testBucket = UnaliasedBucket("testBucket")
    val idField = Field<NumberType>("id", testBucket.name)
    val typeField = Field<StringType>("type", testBucket.name)
    val nameField = Field<StringType>("name", testBucket.name)
    val isActiveField = Field<BooleanType>("isActive", testBucket.name)
    val employeeField = Field<StringType>("employee", testBucket.name)
    val clientField = Field<StringType>("client", testBucket.name)
    val orderNumberField = Field<StringType>("orderNumber", testBucket.name)
    val itemsField = Field<ArrayType<StringType>>("items", testBucket.name)
    val quantitiesField = Field<ArrayType<NumberType>>("quantities", testBucket.name)
    val deliveryDateField = Field<StringType>("deliveryDate", testBucket.name)

    fun queryWithoutParameters(dopeQuery: DopeQuery) = runBlocking {
        cluster.waitUntilReady(maxTimeout).query(
            statement = dopeQuery.queryString,
            consistency = QueryScanConsistency.requestPlus(60.seconds),
        ).execute()
    }

    fun queryWithNamedParameters(dopeQuery: DopeQuery) = runBlocking {
        cluster.waitUntilReady(maxTimeout).query(
            statement = dopeQuery.queryString,
            parameters = QueryParameters.named(dopeQuery.parameters.namedParameters),
            consistency = QueryScanConsistency.requestPlus(60.seconds),
        ).execute()
    }

    fun queryWithPositionalParameters(dopeQuery: DopeQuery) = runBlocking {
        cluster.waitUntilReady(maxTimeout).query(
            statement = dopeQuery.queryString,
            parameters = QueryParameters.positional(dopeQuery.parameters.positionalParameters),
            consistency = QueryScanConsistency.requestPlus(60.seconds),
        ).execute()
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
}
