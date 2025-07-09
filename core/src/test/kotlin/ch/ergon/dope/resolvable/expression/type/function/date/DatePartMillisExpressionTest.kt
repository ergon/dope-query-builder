package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.DAY
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.HOUR
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.MILLISECOND
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.MONTH
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.SECOND
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DatePartMillisExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_PART_MILLIS with field and no timezone`() {
        val expected = DopeQuery(
            queryString = "DATE_PART_MILLIS(`numberField`, \"DAY\")",
        )
        val underTest = DatePartMillisExpression(
            someNumberField(),
            DAY,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_PART_MILLIS with positional parameter date`() {
        val dateValue = 1620000000000L
        val expected = DopeQuery(
            queryString = "DATE_PART_MILLIS($1, \"HOUR\")",
            DopeParameters(positionalParameters = listOf(dateValue)),
        )
        val underTest = DatePartMillisExpression(
            dateValue.asParameter(),
            HOUR,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_PART_MILLIS with named parameter timezone`() {
        val tz = "UTC"
        val name = "zone"
        val expected = DopeQuery(
            queryString = "DATE_PART_MILLIS(`numberField`, \"MONTH\", \$$name)",
            DopeParameters(namedParameters = mapOf(name to tz)),
        )
        val underTest = DatePartMillisExpression(
            someNumberField(),
            MONTH,
            tz.asParameter(name),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support extractDateComponent extension on TypeExpression`() {
        val expr = someNumberField().extractDateComponent(SECOND)
        val expected = DatePartMillisExpression(someNumberField(), SECOND)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support extractDateComponent extension on Type and raw`() {
        val expr = someNumberField().extractDateComponent(SECOND, "Europe/Paris")
        val expected = DatePartMillisExpression(someNumberField(), SECOND, "Europe/Paris".toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support extractDateComponent extension on Type and raw and nothing`() {
        val raw = someNumber()
        val expr = raw.extractDateComponent(SECOND)
        val expected = DatePartMillisExpression(raw.toDopeType(), SECOND)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number extractDateComponent extension with raw timezone`() {
        val expr = 1620000000000L.extractDateComponent(MILLISECOND, "Europe/Paris")
        val expected = DatePartMillisExpression(
            1620000000000L.toDopeType(),
            MILLISECOND,
            "Europe/Paris".toDopeType(),
        )

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
