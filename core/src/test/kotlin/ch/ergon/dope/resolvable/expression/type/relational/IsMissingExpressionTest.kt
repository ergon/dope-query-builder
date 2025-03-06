package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsMissingExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is missing`() {
        val expected = DopeQuery(
            queryString = "`stringField` IS MISSING",
        )
        val underTest = IsMissingExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is missing function`() {
        val field = someStringField()
        val expected = IsMissingExpression(field)

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
