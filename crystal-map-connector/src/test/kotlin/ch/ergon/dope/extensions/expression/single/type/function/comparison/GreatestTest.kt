package ch.ergon.dope.extensions.expression.single.type.function.comparison

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.single.type.function.comparison.greatestOf
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.single.type.function.comparison.GreatestExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class GreatestTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support greatest cm extension number field`() {
        val cmNumberField = someCMNumberField()
        val cmNumberField2 = someCMNumberField()
        val cmNumberField3 = someCMNumberField()
        val expected = GreatestExpression(cmNumberField.toDopeType(), cmNumberField2.toDopeType(), cmNumberField3.toDopeType())

        val actual = greatestOf(cmNumberField, cmNumberField2, cmNumberField3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greatest cm extension string field`() {
        val cmNumberField = someCMStringField()
        val cmNumberField2 = someCMStringField()
        val cmNumberField3 = someCMStringField()
        val expected = GreatestExpression(cmNumberField.toDopeType(), cmNumberField2.toDopeType(), cmNumberField3.toDopeType())

        val actual = greatestOf(cmNumberField, cmNumberField2, cmNumberField3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
