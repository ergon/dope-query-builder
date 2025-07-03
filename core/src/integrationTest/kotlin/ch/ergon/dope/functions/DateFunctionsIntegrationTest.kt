package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.collection.inArray
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.DAY
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.MONTH
import ch.ergon.dope.resolvable.expression.type.function.date.dateRangeBy
import ch.ergon.dope.resolvable.expression.type.function.date.differenceIn
import ch.ergon.dope.resolvable.expression.type.function.date.localClockString
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.Test
import kotlin.test.assertEquals

class DateFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use string functions to create a new string`() {
        val dopeQuery = QueryBuilder()
            .select(
                localClockString("YYYY-MM-DD").alias("localClock"),
                "2023-10-05".differenceIn("2023-08-05", MONTH).add(3).alias("differenceInDays"),
                "2025-09-09".inArray("2025-08-08".dateRangeBy("2025-10-10", DAY)).alias("inArray"),
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), result["localClock"])
        assertEquals(2 + 3, result["differenceInDays"])
        assertEquals(true, result["inArray"])
    }
}
