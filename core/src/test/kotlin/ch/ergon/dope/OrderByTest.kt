package ch.ergon.dope

import ch.ergon.dope.resolvable.clause.select.factory.OrderByType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class OrderByTest {
    private lateinit var create: DSLContext

    @BeforeTest
    fun setup() {
        create = DSLContext()
    }

    @Test
    fun `should add an Order By clause to the end`() {
        val expected = "SELECT * FROM someBucket ORDER BY strField"

        val actual: String = create
            .selectAll()
            .from(
                someBucket(),
            ).orderBy(
                someStringField(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should add an Order By Ascending clause`() {
        val expected = "SELECT * FROM someBucket ORDER BY strField ASC"

        val actual: String = create
            .selectAll()
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
        val expected = "SELECT * FROM someBucket ORDER BY strField DESC"

        val actual: String = create
            .selectAll()
            .from(
                someBucket(),
            ).orderBy(
                someStringField(),
                OrderByType.DESC,
            ).build()

        assertEquals(expected, actual)
    }
}
