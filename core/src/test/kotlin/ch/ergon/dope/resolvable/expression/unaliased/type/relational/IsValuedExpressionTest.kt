package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsValuedExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is valued`() {
        val expected = DopeQuery(
            "`stringField` IS VALUED",
            emptyMap(),
            emptyList(),
        )
        val underTest = IsValuedExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is valued function`() {
        val field = someStringField()
        val expected = IsValuedExpression(field)

        val actual = field.isValued()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
