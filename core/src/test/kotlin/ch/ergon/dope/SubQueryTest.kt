package ch.ergon.dope

import ch.ergon.dope.helper.someStringField
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class SubQueryTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support sub select in from`() {
        val expected = "SELECT stringField FROM (SELECT *) AS asdf"

        val actual: String = create
            .select(someStringField())
            .from(create.selectAsterisk().alias("asdf"))
            .build()

        assertEquals(expected, actual)
    }
}
