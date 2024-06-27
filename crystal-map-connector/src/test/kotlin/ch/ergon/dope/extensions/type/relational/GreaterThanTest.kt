package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.relational.isGreaterThan
import ch.ergon.dope.helper.someCMNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class GreaterThanTest {
    @Test
    fun `should support extensions for greater than with CMField`() {
        val expected = DopeQuery(
            queryString = "`CMNumberField` > `CMNumberField`",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().isGreaterThan(someCMNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
