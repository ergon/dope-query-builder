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

class PositionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support position`() {
        val expected = DopeQuery(
            "POSITION(`stringField`, `stringField`)",
            emptyMap(),
            manager,
        )
        val underTest = PositionExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "POSITION($1, `stringField`)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = PositionExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "POSITION($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = PositionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position function type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val expected = PositionExpression(inStr, searchStr)

        val actual = position(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support position function type string`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val expected = PositionExpression(inStr, searchStr.toDopeType())

        val actual = position(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support position function string type`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val expected = PositionExpression(inStr.toDopeType(), searchStr)

        val actual = position(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support position function string string`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val expected = PositionExpression(inStr.toDopeType(), searchStr.toDopeType())

        val actual = position(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
