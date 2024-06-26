package ch.ergon.dope.extensions.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.arithmetic.neg
import ch.ergon.dope.helper.someCMNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class NegationTest {
    @Test
    fun `should support negation with CMField Number`() {
        val expected = DopeQuery(
            queryString = "-`CMNumberField`",
            parameters = emptyMap(),
        )

        val actual = neg(someCMNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
