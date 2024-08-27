package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayRepeatExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_REPEAT`() {
        val expected = DopeQuery(
            "ARRAY_REPEAT(`numberField`, `numberField`)",
            emptyMap(),
        )
        val underTest = ArrayRepeatExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT with parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "ARRAY_REPEAT($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayRepeatExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT with parameter as value`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "ARRAY_REPEAT(`numberField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayRepeatExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT with all parameters`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            "ARRAY_REPEAT($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayRepeatExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT extension type type`() {
        val value = someNumberField()
        val repetitions = someNumberField()
        val expected = ArrayRepeatExpression(value, repetitions)

        val actual = arrayRepeat(value, repetitions)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPEAT extension type number`() {
        val value = someNumberField()
        val repetitions = someNumber()
        val expected = ArrayRepeatExpression(value, repetitions.toDopeType())

        val actual = arrayRepeat(value, repetitions)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
