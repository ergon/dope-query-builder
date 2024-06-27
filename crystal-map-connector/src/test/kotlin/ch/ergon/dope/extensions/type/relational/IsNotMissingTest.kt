package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.relational.isNotMissing
import ch.ergon.dope.helper.someCMNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotMissingTest {
    @Test
    fun `should support extensions for is not missing with CMField`() {
        val expected = DopeQuery(
            queryString = "`CMNumberField` IS NOT MISSING",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().isNotMissing().toDopeQuery()

        assertEquals(expected, actual)
    }
}
