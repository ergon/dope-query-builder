package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotNullExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is not null`() {
        val expected = DopeQuery(
            "`stringField` IS NOT NULL",
            emptyMap(),
            manager,
        )
        val underTest = IsNotNullExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not null function`() {
        val field = someStringField()
        val expected = IsNotNullExpression(field)

        val actual = field.isNotNull()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
