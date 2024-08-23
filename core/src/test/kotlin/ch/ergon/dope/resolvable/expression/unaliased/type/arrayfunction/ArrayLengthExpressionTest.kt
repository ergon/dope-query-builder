package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArrayLengthExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_LENGTH`() {
        val expected = DopeQuery(
            "ARRAY_LENGTH(`numberArrayField`)",
            emptyMap(),
            manager,
        )
        val underTest = ArrayLengthExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_LENGTH($1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ArrayLengthExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH extension`() {
        val array = someNumberArrayField()
        val expected = ArrayLengthExpression(array)

        val actual = arrayLength(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
