package ch.ergon.dope.extensions.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.logical.not
import ch.ergon.dope.helper.someCMBooleanField
import kotlin.test.Test
import kotlin.test.assertEquals

class NotTest {
    @Test
    fun `should support extensions for not with CMField`() {
        val expected = DopeQuery(
            queryString = "NOT `CMBooleanField`",
            parameters = emptyMap(),
        )

        val actual = not(someCMBooleanField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
