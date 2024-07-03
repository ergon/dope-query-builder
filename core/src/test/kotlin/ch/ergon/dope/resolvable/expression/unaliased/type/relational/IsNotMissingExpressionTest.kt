package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotMissingExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

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
}
