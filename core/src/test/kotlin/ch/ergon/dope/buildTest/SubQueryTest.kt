package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someStringField
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SubQueryTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support sub select in from`() {
        val expected = "SELECT `stringField` FROM (SELECT *) AS `asdf`"

        val actual: String = create
            .select(someStringField())
            .from(create.selectAsterisk().alias("asdf"))
            .build().queryString

        assertEquals(expected, actual)
    }
}
