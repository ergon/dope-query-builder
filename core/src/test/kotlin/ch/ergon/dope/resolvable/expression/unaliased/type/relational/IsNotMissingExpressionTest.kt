package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotMissingExpressionTest : ParameterDependentTest {
    @Test
    fun `should support is not missing`() {
        val expected = DopeQuery(
            "`stringField` IS NOT MISSING",
            emptyMap(),
        )
        val underTest = IsNotMissingExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not missing function`() {
        val field = someStringField()
        val expected = IsNotMissingExpression(field)

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
