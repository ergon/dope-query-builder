package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MBLengthExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support mbLength`() {
        val expected = DopeQuery(
            queryString = "MB_LENGTH(`stringField`)",
        )
        val underTest = MBLengthExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbLength with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "MB_LENGTH($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = MBLengthExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbLength function type`() {
        val inStr = someStringField("inStr")
        val expected = MBLengthExpression(inStr)

        val actual = mbLength(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLength function string`() {
        val inStr = someString()
        val expected = MBLengthExpression(inStr.toDopeType())

        val actual = mbLength(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
