package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MBPositionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support mbPosition`() {
        val expected = DopeQuery(
            queryString = "MB_POSITION(`stringField`, `stringField`)",

        )
        val underTest = MBPositionExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbPosition with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "MB_POSITION($1, `stringField`)",

            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = MBPositionExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbPosition with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "MB_POSITION($1, $2)",

            DopeParameters(positionalParameters = listOf(parameterValue2, parameterValue2)),
        )
        val underTest = MBPositionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbPosition function type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val expected = MBPositionExpression(inStr, searchStr)

        val actual = mbPosition(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbPosition function type string`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val expected = MBPositionExpression(inStr, searchStr.toDopeType())

        val actual = mbPosition(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbPosition function string type`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val expected = MBPositionExpression(inStr.toDopeType(), searchStr)

        val actual = mbPosition(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbPosition function string string`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val expected = MBPositionExpression(inStr.toDopeType(), searchStr.toDopeType())

        val actual = mbPosition(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
