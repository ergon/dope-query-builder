package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DurationToStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DURATION_TO_STR with field`() {
        val expected = DopeQuery(
            queryString = "DURATION_TO_STR(`numberField`)",
        )
        val underTest = DurationToStringExpression(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DURATION_TO_STR with positional parameter duration`() {
        val dur = 2000L
        val expected = DopeQuery(
            queryString = "DURATION_TO_STR($1)",
            DopeParameters(positionalParameters = listOf(dur)),
        )
        val underTest = DurationToStringExpression(dur.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DURATION_TO_STR with named parameter duration`() {
        val dur = 5000L
        val name = "d"
        val expected = DopeQuery(
            queryString = "DURATION_TO_STR(\$$name)",
            DopeParameters(namedParameters = mapOf(name to dur)),
        )
        val underTest = DurationToStringExpression(dur.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toDurationString extension on TypeExpression`() {
        val expr = someNumberField().toDurationString()
        val expected = DurationToStringExpression(someNumberField())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toDurationString extension`() {
        val expr = 3000L.toDurationString()
        val expected = DurationToStringExpression(3000L.toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
