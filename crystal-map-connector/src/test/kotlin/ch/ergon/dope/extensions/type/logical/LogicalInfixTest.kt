package ch.ergon.dope.extensions.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.logical.and
import ch.ergon.dope.extension.type.logical.or
import ch.ergon.dope.helper.someCMBooleanField
import kotlin.test.Test
import kotlin.test.assertEquals

class LogicalInfixTest {
    @Test
    fun `should support extensions for or with CMField`() {
        val expected = DopeQuery(
            queryString = "(`CMBooleanField` OR `CMBooleanField`)",
            parameters = emptyMap(),
        )

        val actual = someCMBooleanField().or(someCMBooleanField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support extensions for and with CMField`() {
        val expected = DopeQuery(
            queryString = "(`CMBooleanField` AND `CMBooleanField`)",
            parameters = emptyMap(),
        )

        val actual = someCMBooleanField().and(someCMBooleanField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
