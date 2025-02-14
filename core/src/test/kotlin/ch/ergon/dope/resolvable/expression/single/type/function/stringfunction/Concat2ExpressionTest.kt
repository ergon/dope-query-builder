package ch.ergon.dope.resolvable.expression.single.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.string.Concat2Expression
import ch.ergon.dope.resolvable.expression.single.type.function.string.concat2
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class Concat2ExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support concat2 with no parameters`() {
        val expected = DopeQuery(
            queryString = "CONCAT2(`stringField`, `stringField`)",
        )
        val underTest = Concat2Expression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "CONCAT2($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = Concat2Expression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "CONCAT2($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = Concat2Expression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 with mixed parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "CONCAT2($1, `stringField`, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = Concat2Expression(parameterValue.asParameter(), someStringField(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 with mixed parameters (all named)`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "CONCAT2(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = Concat2Expression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 function type type`() {
        val separator = someStringField("separator")
        val string = someStringField()
        val expected = Concat2Expression(separator, string)

        val actual = concat2(separator, string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support concat2 function string type`() {
        val separator = someString("separator")
        val string = someStringField()
        val expected =
            Concat2Expression(separator.toDopeType(), string)

        val actual = concat2(separator, string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support concat2 function type string`() {
        val separator = someStringField("separator")
        val string = someString()
        val expected =
            Concat2Expression(separator, string.toDopeType())

        val actual = concat2(separator, string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support concat2 function string string`() {
        val separator = someString("separator")
        val string = someString()
        val expected = Concat2Expression(
            separator.toDopeType(),
            string.toDopeType(),
        )

        val actual = concat2(separator, string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
