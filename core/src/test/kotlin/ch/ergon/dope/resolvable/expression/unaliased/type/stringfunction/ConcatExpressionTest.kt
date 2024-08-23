package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ConcatExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support concat`() {
        val expected = DopeQuery(
            "CONCAT(`stringField`, `stringField`)",
            emptyMap(),
            manager,
        )
        val underTest = ConcatExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "CONCAT($1, `stringField`)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ConcatExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "CONCAT($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = ConcatExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat with mixed parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "CONCAT($1, `stringField`, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = ConcatExpression(parameterValue.asParameter(), someStringField(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat function type type`() {
        val firstString = someStringField("first")
        val secondString = someStringField("second")
        val expected = ConcatExpression(firstString, secondString)

        val actual = concat(firstString, secondString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support concat function string type`() {
        val firstString = someString("first")
        val secondString = someStringField("second")
        val expected = ConcatExpression(firstString.toDopeType(), secondString)

        val actual = concat(firstString, secondString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support concat function type string`() {
        val firstString = someStringField("first")
        val secondString = someString("second")
        val expected = ConcatExpression(firstString, secondString.toDopeType())

        val actual = concat(firstString, secondString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support concat function string string`() {
        val firstString = someString("first")
        val secondString = someString("second")
        val expected = ConcatExpression(firstString.toDopeType(), secondString.toDopeType())

        val actual = concat(firstString, secondString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
