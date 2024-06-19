package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.validtype.ValidType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support positional number parameter`() {
        val parameterValue = 10
        val expected = DopeQuery(
            "$1",
            mapOf("$1" to parameterValue),
        )

        val actual = NumberParameter(parameterValue).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support named number parameter`() {
        val parameterValue = 10
        val parameterName = "testName"
        val expected = DopeQuery(
            "\$$parameterName",
            mapOf(parameterName to parameterValue),
        )

        val actual = NumberParameter(parameterValue, parameterName).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support positional string parameter`() {
        val parameterValue = "testValue"
        val expected = DopeQuery(
            "$1",
            mapOf("$1" to parameterValue),
        )

        val actual = StringParameter(parameterValue).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support named string parameter`() {
        val parameterValue = "testValue"
        val parameterName = "testName"
        val expected = DopeQuery(
            "\$$parameterName",
            mapOf(parameterName to parameterValue),
        )

        val actual = StringParameter(parameterValue, parameterName).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support positional boolean parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "$1",
            mapOf("$1" to parameterValue),
        )

        val actual = BooleanParameter(parameterValue).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support named boolean parameter`() {
        val parameterValue = true
        val parameterName = "testName"
        val expected = DopeQuery(
            "\$$parameterName",
            mapOf(parameterName to parameterValue),
        )

        val actual = BooleanParameter(parameterValue, parameterName).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support positional array parameter`() {
        val parameterValue = listOf("testValue")
        val expected = DopeQuery(
            "$1",
            mapOf("$1" to parameterValue),
        )

        val actual = ArrayParameter<ValidType>(parameterValue).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support named array parameter`() {
        val parameterValue = listOf("testValue")
        val parameterName = "testName"
        val expected = DopeQuery(
            "\$$parameterName",
            mapOf(parameterName to parameterValue),
        )

        val actual = ArrayParameter<ValidType>(parameterValue, parameterName).toDopeQuery()

        assertEquals(expected, actual)
    }
}
