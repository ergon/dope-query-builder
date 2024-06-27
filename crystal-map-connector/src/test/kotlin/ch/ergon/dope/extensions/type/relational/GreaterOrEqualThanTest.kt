package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.helper.someCMNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class GreaterOrEqualThanTest {
    @Test
    fun `should support extensions for greater or equals than with CMField`() {
        val expected = DopeQuery(
            queryString = "`CMNumberField` >= `CMNumberField`",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().isGreaterOrEqualThan(someCMNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
