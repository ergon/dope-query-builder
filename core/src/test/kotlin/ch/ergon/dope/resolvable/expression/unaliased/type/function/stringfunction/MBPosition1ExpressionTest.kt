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

class MBPosition1ExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support mbPosition1`() {
        val expected = DopeQuery(
            queryString = "MB_POSITION1(`stringField`, `stringField`)",

        )
        val underTest = MBPosition1Expression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbPosition1 with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "MB_POSITION1($1, `stringField`)",

            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = MBPosition1Expression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbPosition1 with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "MB_POSITION1($1, $2)",

            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = MBPosition1Expression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbPosition1 function type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val expected = MBPosition1Expression(inStr, searchStr)

        val actual = mbPosition1(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbPosition1 function type string`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val expected = MBPosition1Expression(inStr, searchStr.toDopeType())

        val actual = mbPosition1(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbPosition1 function string type`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val expected = MBPosition1Expression(inStr.toDopeType(), searchStr)

        val actual = mbPosition1(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbPosition1 function string string`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val expected = MBPosition1Expression(inStr.toDopeType(), searchStr.toDopeType())

        val actual = mbPosition1(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
