package ch.ergon.dope.resolvable.expression.single.type.function.conditional

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NvlExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support nvl`() {
        val expected = DopeQuery(
            queryString = "NVL(`stringField`, `stringField`)",
        )
        val underTest = NvlExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "NVL($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NvlExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "NVL(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = NvlExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl with positional second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "NVL(`stringField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NvlExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl with named second parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "NVL(`stringField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = NvlExpression(someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl with positional all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            queryString = "NVL($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = NvlExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl with named all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "NVL(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = NvlExpression(
            parameterValue.asParameter(parameterName),
            parameterValue2.asParameter(parameterName2),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl function expression expression`() {
        val initialExpression = someStringField()
        val substituteExpression = someStringField()
        val expected = NvlExpression(initialExpression, substituteExpression)

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl function expression number`() {
        val initialExpression = someNumberField()
        val substituteExpression = someNumber()
        val expected = NvlExpression(initialExpression, substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl function expression string`() {
        val initialExpression = someStringField()
        val substituteExpression = someString()
        val expected = NvlExpression(initialExpression, substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl function expression boolean`() {
        val initialExpression = someBooleanField()
        val substituteExpression = someBoolean()
        val expected = NvlExpression(initialExpression, substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
