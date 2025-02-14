package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.clause.model.DeleteOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class OffsetClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support delete offset`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` OFFSET `numberField`",
        )
        val underTest = DeleteOffsetClause(someNumberField(), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete offset with named parameter`() {
        val parameterValue = 2
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` OFFSET \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = DeleteOffsetClause(parameterValue.asParameter(parameterName), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete offset with positional parameter`() {
        val parameterValue = 2
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` OFFSET $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = DeleteOffsetClause(parameterValue.asParameter(), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete offset function`() {
        val numberField = someNumberField()
        val parentClause = someDeleteClause()
        val expected = DeleteOffsetClause(numberField, parentClause)

        val actual = parentClause.offset(numberField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select offset`() {
        val expected = DopeQuery(
            queryString = "SELECT * OFFSET `numberField`",
        )
        val underTest = SelectOffsetClause(someNumberField(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset with named parameter`() {
        val parameterValue = 5
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT * OFFSET \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectOffsetClause(parameterValue.asParameter(parameterName), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            queryString = "SELECT * OFFSET $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectOffsetClause(parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset with named parameter and named parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = 5
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName OFFSET \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = SelectOffsetClause(
            parameterValue2.asParameter(parameterName2),
            someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset with positional parameter and positional parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = 5
        val expected = DopeQuery(
            queryString = "SELECT $1 OFFSET $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = SelectOffsetClause(parameterValue2.asParameter(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset function`() {
        val numberField = someNumberField()
        val parentClause = someSelectClause()
        val expected = SelectOffsetClause(numberField, parentClause)

        val actual = parentClause.offset(numberField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
