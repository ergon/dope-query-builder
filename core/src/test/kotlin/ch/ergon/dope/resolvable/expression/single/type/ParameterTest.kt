package ch.ergon.dope.resolvable.expression.single.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someObject
import ch.ergon.dope.helper.someString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support positional number parameter`() {
        val parameterValue = 10
        val expected = DopeQuery(
            queryString = "$1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NumberParameter(parameterValue)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support named number parameter`() {
        val parameterValue = 10
        val parameterName = "testName"
        val expected = DopeQuery(
            queryString = "\$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = NumberParameter(parameterValue, parameterName)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support positional string parameter`() {
        val parameterValue = "testValue"
        val expected = DopeQuery(
            queryString = "$1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = StringParameter(parameterValue)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support named string parameter`() {
        val parameterValue = "testValue"
        val parameterName = "testName"
        val expected = DopeQuery(
            queryString = "\$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = StringParameter(parameterValue, parameterName)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support positional boolean parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            queryString = "$1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = BooleanParameter(parameterValue)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support named boolean parameter`() {
        val parameterValue = true
        val parameterName = "testName"
        val expected = DopeQuery(
            queryString = "\$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = BooleanParameter(parameterValue, parameterName)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support positional array parameter`() {
        val parameterValue = listOf("testValue")
        val expected = DopeQuery(
            queryString = "$1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayParameter<ValidType>(parameterValue)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support named array parameter`() {
        val parameterValue = listOf("testValue")
        val parameterName = "testName"
        val expected = DopeQuery(
            queryString = "\$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayParameter<ValidType>(parameterValue, parameterName)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support positional object parameter`() {
        val parameterValue = someObject()
        val expected = DopeQuery(
            queryString = "$1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ObjectParameter(parameterValue)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support named object parameter`() {
        val parameterValue = someObject()
        val parameterName = "testName"
        val expected = DopeQuery(
            queryString = "$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ObjectParameter(parameterValue, parameterName)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function number`() {
        val value = someNumber()
        val parameterName = null
        val expected = NumberParameter(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter().toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function number with named parameter`() {
        val value = someNumber()
        val parameterName = someString()
        val expected = NumberParameter(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter(parameterName).toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function string`() {
        val value = someString()
        val parameterName = null
        val expected = StringParameter(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter().toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function string with named parameter`() {
        val value = someString()
        val parameterName = someString()
        val expected = StringParameter(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter(parameterName).toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function boolean`() {
        val value = someBoolean()
        val parameterName = null
        val expected = BooleanParameter(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter().toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function boolean with named parameter`() {
        val value = someBoolean()
        val parameterName = someString()
        val expected = BooleanParameter(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter(parameterName).toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function collection number`() {
        val value = listOf(someNumber())
        val parameterName = null
        val expected = ArrayParameter<NumberType>(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter().toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function collection number with named parameter`() {
        val value = listOf(someNumber())
        val parameterName = someString()
        val expected = ArrayParameter<NumberType>(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter(parameterName).toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function collection string`() {
        val value = listOf(someString())
        val parameterName = null
        val expected = ArrayParameter<StringType>(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter().toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function collection string with named parameter`() {
        val value = listOf(someString())
        val parameterName = someString()
        val expected = ArrayParameter<StringType>(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter(parameterName).toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function collection boolean`() {
        val value = listOf(someBoolean())
        val parameterName = null
        val expected = ArrayParameter<BooleanType>(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter().toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function collection boolean with named parameter`() {
        val value = listOf(someBoolean())
        val parameterName = someString()
        val expected = ArrayParameter<BooleanType>(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter(parameterName).toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function object`() {
        val value = someObject()
        val parameterName = null
        val expected = ObjectParameter(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter().toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }

    @Test
    fun `should support as parameter function object with named parameter`() {
        val value = someObject()
        val parameterName = someString()
        val expected = ObjectParameter(value, parameterName).toDopeQuery(manager)

        val actual = value.asParameter(parameterName).toDopeQuery(DopeQueryManager())

        assertEquals(expected, actual)
    }
}
