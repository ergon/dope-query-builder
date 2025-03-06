package ch.ergon.dope.resolvable.expression.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.string.ConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ConcatExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support concat`() {
        val expected = DopeQuery(
            queryString = "CONCAT(`stringField`, `stringField`)",
        )
        val underTest = ConcatExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "CONCAT($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ConcatExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "CONCAT($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
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
            queryString = "CONCAT($1, `stringField`, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = ConcatExpression(parameterValue.asParameter(), someStringField(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat with all named parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "CONCAT(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = ConcatExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

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

    @Test
    fun `should support concat function string type string`() {
        val firstString = someString("first")
        val secondString = someStringField("second")
        val thirdString = someString("third")
        val expected = ConcatExpression(firstString.toDopeType(), secondString, thirdString.toDopeType())

        val actual = concat(firstString, secondString, thirdString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support concat function type type string`() {
        val firstString = someStringField("first")
        val secondString = someStringField("second")
        val thirdString = someString("third")
        val expected = ConcatExpression(firstString, secondString, thirdString.toDopeType())

        val actual = concat(firstString, secondString, thirdString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support concat function string string type`() {
        val firstString = someString("first")
        val secondString = someString("second")
        val thirdString = someStringField("third")
        val expected = ConcatExpression(firstString.toDopeType(), secondString.toDopeType(), thirdString)

        val actual = concat(firstString, secondString, thirdString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
