package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotMissingExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is not missing`() {
        val expected = DopeQuery(
            queryString = "`stringField` IS NOT MISSING",
        )
        val underTest = IsNotMissingExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not missing function`() {
        val field = someStringField()
        val expected = IsNotMissingExpression(field)

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
