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
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NvlExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support nvl`() {
        val expected = DopeQuery(
            "NVL(`stringField`, `stringField`)",
            emptyMap(),
            manager,
        )
        val underTest = NvlExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "NVL($1, `stringField`)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = NvlExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl with second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "NVL(`stringField`, $1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = NvlExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl with all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "NVL($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = NvlExpression(parameterValue.asParameter(), parameterValue2.asParameter())

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
