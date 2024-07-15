package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotValuedExpressionTest : ParameterDependentTest {
    @Test
    fun `should support is not valued`() {
        val expected = DopeQuery(
            "`stringField` IS NOT VALUED",
            emptyMap(),
        )
        val underTest = IsNotValuedExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not valued function`() {
        val field = someStringField()
        val expected = IsNotValuedExpression(field)

        val actual = field.isNotValued()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
