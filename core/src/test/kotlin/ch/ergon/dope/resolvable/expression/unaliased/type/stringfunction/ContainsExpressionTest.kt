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

class ContainsExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support contains`() {
        val expected = DopeQuery(
            "CONTAINS(`stringField`, `stringField`)",
            emptyMap(),
            manager,
        )
        val underTest = ContainsExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "CONTAINS($1, `stringField`)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ContainsExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contain with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "CONTAINS($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = ContainsExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains function type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val expected = ContainsExpression(inStr, searchStr)

        val actual = contains(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support contains function type string`() {
        val inStr = someStringField("inStr")
        val searchStr = someString()
        val expected = ContainsExpression(inStr, searchStr.toDopeType())

        val actual = contains(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support contains function string type`() {
        val inStr = someString()
        val searchStr = someStringField("searchStr")
        val expected = ContainsExpression(inStr.toDopeType(), searchStr)

        val actual = contains(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support contains function string string`() {
        val inStr = someString()
        val searchStr = someString()
        val expected = ContainsExpression(inStr.toDopeType(), searchStr.toDopeType())

        val actual = contains(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
