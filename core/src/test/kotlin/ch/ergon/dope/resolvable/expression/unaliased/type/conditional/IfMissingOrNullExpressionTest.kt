package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class IfMissingOrNullExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support if missing or null`() {
        val expected = DopeQuery(
            "IFMISSINGORNULL(`stringField`, `stringField`)",
            emptyMap(),
        )
        val underTest = IfMissingOrNullExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFMISSINGORNULL($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IfMissingOrNullExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null with second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFMISSINGORNULL(`stringField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IfMissingOrNullExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null with all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "IFMISSINGORNULL($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = IfMissingOrNullExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null function`() {
        val firstExpression = someStringField()
        val secondExpression = someStringField()
        val expected = IfMissingOrNullExpression(firstExpression, secondExpression)

        val actual = ifMissingOrNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support coalesce`() {
        val expected = DopeQuery(
            "COALESCE(`stringField`, `stringField`)",
            emptyMap(),
        )
        val underTest = CoalesceExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "COALESCE($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = CoalesceExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce with second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "COALESCE(`stringField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = CoalesceExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce with all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "COALESCE($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = CoalesceExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce function`() {
        val firstExpression = someStringField()
        val secondExpression = someStringField()
        val expected = CoalesceExpression(firstExpression, secondExpression)

        val actual = coalesce(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
