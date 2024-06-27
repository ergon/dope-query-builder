package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.relational.isMissing
import ch.ergon.dope.helper.someCMNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsMissingTest {
    @Test
    fun `should support extensions for is missing with CMField`() {
        val expected = DopeQuery(
            queryString = "`CMNumberField` IS MISSING",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().isMissing().toDopeQuery()

        assertEquals(expected, actual)
    }
}
