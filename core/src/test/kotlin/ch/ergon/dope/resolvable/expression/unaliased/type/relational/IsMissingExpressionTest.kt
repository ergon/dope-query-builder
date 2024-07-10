package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class IsMissingExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support is missing`() {
        val expected = DopeQuery(
            "`stringField` IS MISSING",
            emptyMap(),
        )
        val underTest = IsMissingExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is missing function`() {
        val field = someStringField()
        val expected = IsMissingExpression(field)

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
