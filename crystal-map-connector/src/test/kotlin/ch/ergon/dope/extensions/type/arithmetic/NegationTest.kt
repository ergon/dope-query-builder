package ch.ergon.dope.extensions.type.arithmetic

import ch.ergon.dope.extension.type.arithmetic.neg
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.NegationExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NegationTest {
    @Test
    fun `should support neg with CMJsonField Number`() {
        val field = someCMNumberField()
        val expected = NegationExpression(field.toDopeType())

        val actual = neg(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
