package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MillisToStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support MILLIS_TO_STR without format`() {
        val expected = DopeQuery(
            queryString = "MILLIS_TO_STR(`numberField`)",
        )
        val underTest = MillisToStrExpression(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support MILLIS_TO_STR with format field`() {
        val fmt = someStringField()
        val expected = DopeQuery(
            queryString = "MILLIS_TO_STR(`numberField`, `stringField`)",
        )
        val underTest = MillisToStrExpression(someNumberField(), fmt)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support MILLIS_TO_STR with positional parameter format`() {
        val fmt = "yyyy"
        val expected = DopeQuery(
            queryString = "MILLIS_TO_STR(`numberField`, $1)",
            DopeParameters(positionalParameters = listOf(fmt)),
        )
        val underTest = MillisToStrExpression(someNumberField(), fmt.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support MILLIS_TO_STR with named parameter format`() {
        val fmt = "MM-dd"
        val name = "f"
        val expected = DopeQuery(
            queryString = "MILLIS_TO_STR(`numberField`, \$$name)",
            DopeParameters(namedParameters = mapOf(name to fmt)),
        )
        val underTest = MillisToStrExpression(someNumberField(), fmt.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toFormattedDate extension on TypeExpression`() {
        val expr = someNumberField().toFormattedDate()
        val expected = MillisToStrExpression(someNumberField(), null)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support toFormattedDate extension on raw and TypeExpression`() {
        val expr = 123L.toFormattedDate()
        val expected = MillisToStrExpression(123L.toDopeType(), null)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDate extension with raw format`() {
        val expr = 123L.toFormattedDate("dd/MM")
        val expected = MillisToStrExpression(123L.toDopeType(), "dd/MM".toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support toFormattedDate extension on TypeExpression with format`() {
        val expr = someNumberField().toFormattedDate("dd/MM")
        val expected = MillisToStrExpression(someNumberField(), "dd/MM".toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
