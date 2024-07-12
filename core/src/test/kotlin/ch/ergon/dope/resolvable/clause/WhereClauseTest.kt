package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.clause.model.DeleteWhereClause
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class WhereClauseTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support delete where`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` WHERE TRUE",
            emptyMap(),
        )
        val underTest = DeleteWhereClause(someBooleanExpression(), someDeleteClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete where with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "DELETE FROM `someBucket` WHERE $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = DeleteWhereClause(parameterValue.asParameter(), someDeleteClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete where function`() {
        val condition = someBooleanExpression()
        val parentClause = someDeleteClause()
        val expected = DeleteWhereClause(condition, parentClause = parentClause)

        val actual = parentClause.where(condition)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support select where`() {
        val expected = DopeQuery(
            "SELECT * WHERE TRUE",
            emptyMap(),
        )
        val underTest = SelectWhereClause(someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where with positional parameter`() {
        val parameterValue = false
        val expected = DopeQuery(
            "SELECT * WHERE $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = SelectWhereClause(parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where with parameter and parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = false
        val expected = DopeQuery(
            "SELECT $1 WHERE $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = SelectWhereClause(parameterValue2.asParameter(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where function`() {
        val condition = someBooleanExpression()
        val parentClause = someSelectClause()
        val expected = SelectWhereClause(condition, parentClause = parentClause)

        val actual = parentClause.where(condition)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
