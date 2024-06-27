package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.relational.isNotNull
import ch.ergon.dope.helper.someCMNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotNullTest {
    @Test
    fun `should support extensions for is not null with CMField`() {
        val expected = DopeQuery(
            queryString = "`CMNumberField` IS NOT NULL",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().isNotNull().toDopeQuery()

        assertEquals(expected, actual)
    }
}
