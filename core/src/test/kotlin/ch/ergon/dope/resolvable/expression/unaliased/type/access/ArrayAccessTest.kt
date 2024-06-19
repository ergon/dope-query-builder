package ch.ergon.dope.resolvable.expression.unaliased.type.access

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayAccessTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support array access`() {
        val expected = DopeQuery(
            "`stringArrayField`[`numberField`]",
            emptyMap(),
        )

        val actual = ArrayAccess(someStringArrayField(), someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with parameter`() {
        val parameterValue = listOf("value")
        val expected = DopeQuery(
            "$1[`numberField`]",
            mapOf("$1" to parameterValue),
        )

        val actual = ArrayAccess(parameterValue.asParameter(), someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with all parameters`() {
        val parameterValue = listOf("value")
        val parameterValue2 = 4
        val expected = DopeQuery(
            "$1[$2]",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )

        val actual = ArrayAccess(parameterValue.asParameter(), parameterValue2.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
