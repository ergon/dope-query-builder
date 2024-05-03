package ch.ergon.dope

import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.OrderByType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class OrderByTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should add an Order By clause to the end`() {
        val expected = "SELECT * FROM someBucket ORDER BY stringField"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).orderBy(
                someStringField(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should add an Order By Ascending clause`() {
        val expected = "SELECT * FROM someBucket ORDER BY stringField ASC"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).orderBy(
                someStringField(),
                OrderByType.ASC,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should add an Order By Descending clause`() {
        val expected = "SELECT * FROM someBucket ORDER BY stringField DESC"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).orderBy(
                someStringField(),
                OrderByType.DESC,
            ).build()

        assertEquals(expected, actual)
    }
}
