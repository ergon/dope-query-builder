package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NowTimezoneExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support NOW_TZ with timezone field only`() {
        val expr = NowTimezoneExpression(someStringField())
        val expected = DopeQuery("NOW_TZ(`stringField`)")
        assertEquals(expected, expr.toDopeQuery(manager))
    }

    @Test
    fun `should support NOW_TZ with positional parameter timezone`() {
        val expr = NowTimezoneExpression("UTC".asParameter())
        val expected = DopeQuery("NOW_TZ($1)", DopeParameters(positionalParameters = listOf("UTC")))
        assertEquals(expected, expr.toDopeQuery(manager))
    }

    @Test
    fun `should support NOW_TZ with named parameter timezone`() {
        val expr = NowTimezoneExpression("UTC".asParameter("zone"))
        val expected = DopeQuery("NOW_TZ($" + "zone)", DopeParameters(namedParameters = mapOf("zone" to "UTC")))
        assertEquals(expected, expr.toDopeQuery(manager))
    }

    @Test
    fun `should support NOW_TZ with timezone and format fields`() {
        val expr = NowTimezoneExpression(someStringField(), someStringField())
        val expected = DopeQuery("NOW_TZ(`stringField`, `stringField`)")
        assertEquals(expected, expr.toDopeQuery(manager))
    }

    @Test
    fun `should support NOW_TZ with positional timezone and positional format`() {
        val expr = NowTimezoneExpression("TZ".asParameter(), "FMT".asParameter())
        val expected = DopeQuery(
            "NOW_TZ($1, $2)",
            DopeParameters(positionalParameters = listOf("TZ", "FMT")),
        )
        assertEquals(expected, expr.toDopeQuery(manager))
    }

    @Test
    fun `should support NOW_TZ with named timezone and named format`() {
        val expr = NowTimezoneExpression("TZ".asParameter("z"), "FMT".asParameter("f"))
        val expected = DopeQuery(
            "NOW_TZ(\$z, \$f)",
            DopeParameters(namedParameters = mapOf("z" to "TZ", "f" to "FMT")),
        )
        assertEquals(expected, expr.toDopeQuery(manager))
    }

    @Test
    fun `should support nowStringInZone extension with field timezone only`() {
        val expr = nowStringInZone(someStringField())
        val expected = NowTimezoneExpression(someStringField(), null)
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support nowStringInZone extension with raw timezone only`() {
        val expr = nowStringInZone("UTC")
        val expected = NowTimezoneExpression("UTC".toDopeType(), null)
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support nowStringInZone extension with field timezone and field format`() {
        val expr = nowStringInZone(someStringField(), someStringField())
        val expected = NowTimezoneExpression(someStringField(), someStringField())
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support nowStringInZone extension with field timezone and raw format`() {
        val expr = nowStringInZone(someStringField(), "yyyy")
        val expected = NowTimezoneExpression(someStringField(), "yyyy".toDopeType())
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support nowStringInZone extension with raw timezone and field format`() {
        val expr = nowStringInZone("UTC", someStringField())
        val expected = NowTimezoneExpression("UTC".toDopeType(), someStringField())
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support nowStringInZone extension with raw timezone and raw format`() {
        val expr = nowStringInZone("TZ", "FMT")
        val expected = NowTimezoneExpression("TZ".toDopeType(), "FMT".toDopeType())
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
