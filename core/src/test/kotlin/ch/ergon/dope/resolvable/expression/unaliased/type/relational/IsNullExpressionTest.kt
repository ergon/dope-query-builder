package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNullExpressionTest : ParameterDependentTest {
    @Test
    fun `should support is null`() {
        val expected = DopeQuery(
            "`stringField` IS NULL",
            emptyMap(),
        )
        val underTest = IsNullExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is null function`() {
        val field = someStringField()
        val expected = IsNullExpression(field)

        val actual = field.isNull()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
