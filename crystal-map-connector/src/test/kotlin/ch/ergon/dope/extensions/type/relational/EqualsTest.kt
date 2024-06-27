package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.relational.isEqualTo
import ch.ergon.dope.helper.someCMNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class EqualsTest {
    @Test
    fun `should support extensions for equals with CMField`() {
        val expected = DopeQuery(
            queryString = "`CMNumberField` = `CMNumberField`",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().isEqualTo(someCMNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
