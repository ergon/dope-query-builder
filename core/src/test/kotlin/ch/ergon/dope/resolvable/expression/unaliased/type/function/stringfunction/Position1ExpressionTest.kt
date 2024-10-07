package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class Position1ExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support position1`() {
        val expected = DopeQuery(
            "POSITION1(`stringField`, `stringField`)",
            emptyMap(),
        )
        val underTest = Position1Expression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position1 with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "POSITION1($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = Position1Expression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position1 with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "POSITION1($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = Position1Expression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position1 function type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val expected = Position1Expression(inStr, searchStr)

        val actual = position1(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support position1 function type string`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val expected = Position1Expression(inStr, searchStr.toDopeType())

        val actual = position1(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support position1 function string type`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val expected = Position1Expression(inStr.toDopeType(), searchStr)

        val actual = position1(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support position1 function string string`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val expected = Position1Expression(inStr.toDopeType(), searchStr.toDopeType())

        val actual = position1(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}