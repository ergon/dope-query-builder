package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.relational.isNotValued
import ch.ergon.dope.helper.someCMNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotValuedTest {
    @Test
    fun `should support extensions for is not valued with CMField`() {
        val expected = DopeQuery(
            queryString = "`CMNumberField` IS NOT VALUED",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().isNotValued().toDopeQuery()

        assertEquals(expected, actual)
    }
}
