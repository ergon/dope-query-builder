package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class IsValuedExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support is valued`() {
        val expected = DopeQuery(
            "`stringField` IS VALUED",
            emptyMap(),
        )

        val actual = IsValuedExpression(someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
