package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class NumberFunctionsTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = QueryBuilder()
    }

    @Test
    fun `should support numberType alias`() {
        val expected = "SELECT 12 AS `someNumber`"

        val actual: String = create
            .select(
                12.toDopeType().alias("someNumber"),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
