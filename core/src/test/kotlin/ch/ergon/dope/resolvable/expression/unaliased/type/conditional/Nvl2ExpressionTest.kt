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
            manager,
        )
        val underTest = Nvl2Expression(someBooleanField(), someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "NVL2($1, `stringField`, `stringField`)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = Nvl2Expression(parameterValue.asParameter(), someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "NVL2(`booleanField`, $1, `stringField`)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = Nvl2Expression(someBooleanField(), parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with third parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "NVL2(`booleanField`, `stringField`, $1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = Nvl2Expression(someBooleanField(), someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with first and second parameters`() {
        val parameterValue = someBoolean()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "NVL2($1, $2, `stringField`)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = Nvl2Expression(parameterValue.asParameter(), parameterValue2.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with first and third parameters`() {
        val parameterValue = someBoolean()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "NVL2($1, `stringField`, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = Nvl2Expression(parameterValue.asParameter(), someStringField(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with second and third parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "NVL2(`booleanField`, $1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = Nvl2Expression(someBooleanField(), parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 with all parameters`() {
        val parameterValue = someBoolean()
        val parameterValue2 = someString()
        val parameterValue3 = someString()
        val expected = DopeQuery(
            "NVL2($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
            manager,
        )
        val underTest = Nvl2Expression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

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
