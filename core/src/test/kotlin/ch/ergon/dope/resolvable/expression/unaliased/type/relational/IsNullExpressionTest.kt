package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNullExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is null`() {
        val expected = DopeQuery(
            "`stringField` IS NULL",
            emptyMap(),
            emptyList(),
        )
        val underTest = IsNullExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is null function`() {
        val field = someStringField()
        val expected = IsNullExpression(field)

        val actual = field.isNull()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
