package ch.ergon.dope.resolvable.expression.type.function.date

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

class StrToDurationExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support STR_TO_DURATION with field`() {
        val expected = DopeQuery(
            queryString = "STR_TO_DURATION(`stringField`)",
        )
        val underTest = StrToDurationExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support STR_TO_DURATION with positional parameter duration`() {
        val dur = "1h"
        val expected = DopeQuery(
            queryString = "STR_TO_DURATION($1)",
            DopeParameters(positionalParameters = listOf(dur)),
        )
        val underTest = StrToDurationExpression(dur.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support STR_TO_DURATION with named parameter duration`() {
        val dur = "5m"
        val name = "d"
        val expected = DopeQuery(
            queryString = "STR_TO_DURATION(\$$name)",
            DopeParameters(namedParameters = mapOf(name to dur)),
        )
        val underTest = StrToDurationExpression(dur.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toDurationMillis extension on TypeExpression`() {
        val expr = someStringField().toDurationMillis()
        val expected = StrToDurationExpression(someStringField())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String toDurationMillis extension`() {
        val raw = someString()
        val expr = raw.toDurationMillis()
        val expected = StrToDurationExpression(raw.toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
