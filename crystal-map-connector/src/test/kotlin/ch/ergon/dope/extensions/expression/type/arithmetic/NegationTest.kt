package ch.ergon.dope.extensions.expression.type.arithmetic

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.arithmetic.neg
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.resolvable.expression.type.arithmetic.NegationExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NegationTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support neg with CMJsonField Number`() {
        val field = someCMNumberField()
        val expected = NegationExpression(field.toDopeType())

        val actual = neg(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
