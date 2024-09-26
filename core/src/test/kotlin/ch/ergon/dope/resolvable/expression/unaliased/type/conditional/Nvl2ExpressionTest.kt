package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someUnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.Nvl2Expression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.nvl2
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class Nvl2ExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support nvl2`() {
        val expected = DopeQuery(
            "NVL2(`booleanField`, `stringField`, `stringField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = Nvl2Expression(someBooleanField(), someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "NVL2($1, `stringField`, `stringField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = Nvl2Expression(parameterValue.asParameter(), someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            "NVL2(\$$parameterName, `stringField`, `stringField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = Nvl2Expression(parameterValue.asParameter(parameterName), someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with positional second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "NVL2(`booleanField`, $1, `stringField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = Nvl2Expression(someBooleanField(), parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with named second parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            "NVL2(`booleanField`, \$$parameterName, `stringField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = Nvl2Expression(someBooleanField(), parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with positional third parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "NVL2(`booleanField`, `stringField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = Nvl2Expression(someBooleanField(), someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with named third parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            "NVL2(`booleanField`, `stringField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = Nvl2Expression(someBooleanField(), someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with positional all parameters`() {
        val parameterValue = someBoolean()
        val parameterValue2 = someString()
        val parameterValue3 = someString()
        val expected = DopeQuery(
            "NVL2($1, $2, $3)",
            emptyMap(),
            listOf(parameterValue, parameterValue2, parameterValue3),
        )
        val underTest = Nvl2Expression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with named all parameters`() {
        val parameterValue = someBoolean()
        val parameterValue2 = someString()
        val parameterValue3 = someString()
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val parameterName3 = "param3"
        val expected = DopeQuery(
            "NVL2(\$$parameterName1, \$$parameterName2, \$$parameterName3)",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2, parameterName3 to parameterValue3),
            emptyList(),
        )
        val underTest = Nvl2Expression(
            parameterValue.asParameter(parameterName1),
            parameterValue2.asParameter(parameterName2),
            parameterValue3.asParameter(parameterName3),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 function expression expression`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someStringField()
        val valueIfNotExists = someStringField()
        val expected = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists)

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 function expression number`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someNumberField()
        val valueIfNotExists = someNumber()
        val expected = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 function expression string`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someStringField()
        val valueIfNotExists = someString()
        val expected = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 function expression boolean`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someBooleanField()
        val valueIfNotExists = someBoolean()
        val expected = Nvl2Expression(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 function number expression`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someNumber()
        val valueIfNotExists = someNumberField()
        val expected = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 function string expression`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someString()
        val valueIfNotExists = someStringField()
        val expected = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 function boolean expression`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someBoolean()
        val valueIfNotExists = someBooleanField()
        val expected = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 function number number`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someNumber()
        val valueIfNotExists = someNumber()
        val expected = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 function string string`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someString()
        val valueIfNotExists = someString()
        val expected = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 function boolean boolean`() {
        val initialExpression = someUnaliasedExpression()
        val valueIfExists = someBoolean()
        val valueIfNotExists = someBoolean()
        val expected = Nvl2Expression(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(initialExpression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
