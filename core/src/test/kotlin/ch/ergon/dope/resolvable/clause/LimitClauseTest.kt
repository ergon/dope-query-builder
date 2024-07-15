package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.clause.model.DeleteLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class LimitClauseTest : ParameterDependentTest {
    @Test
    fun `should support delete limit`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` LIMIT `numberField`",
            emptyMap(),
        )
        val underTest = DeleteLimitClause(someNumberField(), someDeleteClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete limit with positional parameter`() {
        val parameterValue = 2
        val expected = DopeQuery(
            "DELETE FROM `someBucket` LIMIT $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = DeleteLimitClause(parameterValue.asParameter(), someDeleteClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete limit function`() {
        val numberField = someNumberField()
        val parentClause = someDeleteClause()
        val expected = DeleteLimitClause(numberField, parentClause)

        val actual = parentClause.limit(numberField)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support select limit`() {
        val expected = DopeQuery(
            "SELECT * LIMIT `numberField`",
            emptyMap(),
        )
        val underTest = SelectLimitClause(someNumberField(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select limit with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "SELECT * LIMIT $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = SelectLimitClause(parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select limit with parameter and parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "SELECT $1 LIMIT $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = SelectLimitClause(parameterValue2.asParameter(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select limit function`() {
        val numberField = someNumberField()
        val parentClause = someSelectClause()
        val expected = SelectLimitClause(numberField, parentClause)

        val actual = parentClause.limit(numberField)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
