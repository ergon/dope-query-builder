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

        val actual = IsMissingExpression(someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
