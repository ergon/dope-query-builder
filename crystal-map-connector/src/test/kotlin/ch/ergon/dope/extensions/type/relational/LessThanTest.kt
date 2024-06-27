package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.relational.isLessThan
import ch.ergon.dope.helper.someCMNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class LessThanTest {
    @Test
    fun `should support extensions for less or equals than with CMField`() {
        val expected = DopeQuery(
            queryString = "`CMNumberField` < `CMNumberField`",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().isLessThan(someCMNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
