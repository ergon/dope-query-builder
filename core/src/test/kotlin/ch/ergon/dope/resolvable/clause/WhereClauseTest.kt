package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someUpdateClause
import ch.ergon.dope.resolvable.clause.model.DeleteWhereClause
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.UpdateWhereClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class WhereClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support delete where`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` WHERE TRUE",
        )
        val underTest = DeleteWhereClause(someBooleanExpression(), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete where with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` WHERE $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = DeleteWhereClause(parameterValue.asParameter(), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete where with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` WHERE \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = DeleteWhereClause(parameterValue.asParameter(parameterName), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete where function`() {
        val condition = someBooleanExpression()
        val parentClause = someDeleteClause()
        val expected = DeleteWhereClause(condition, parentClause = parentClause)

        val actual = parentClause.where(condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select where`() {
        val expected = DopeQuery(
            queryString = "SELECT * WHERE TRUE",
        )
        val underTest = SelectWhereClause(someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where with positional parameter`() {
        val parameterValue = false
        val expected = DopeQuery(
            queryString = "SELECT * WHERE $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectWhereClause(parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where with named parameter`() {
        val parameterValue = false
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT * WHERE \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectWhereClause(parameterValue.asParameter(parameterName), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where with positional parameter and positional parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = false
        val expected = DopeQuery(
            queryString = "SELECT $1 WHERE $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = SelectWhereClause(parameterValue2.asParameter(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where with named parameter and named parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = false
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName WHERE \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = SelectWhereClause(
            parameterValue2.asParameter(parameterName2),
            someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where function`() {
        val condition = someBooleanExpression()
        val parentClause = someSelectClause()
        val expected = SelectWhereClause(condition, parentClause = parentClause)

        val actual = parentClause.where(condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update where`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` WHERE TRUE",
        )
        val underTest = UpdateWhereClause(someBooleanExpression(), someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update where with positional parameter`() {
        val parameterValue = false
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` WHERE $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = UpdateWhereClause(parameterValue.asParameter(), someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update where with named parameter`() {
        val parameterValue = false
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` WHERE \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = UpdateWhereClause(parameterValue.asParameter(parameterName), someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update where function`() {
        val condition = someBooleanExpression()
        val parentClause = someUpdateClause()
        val expected = UpdateWhereClause(condition, parentClause = parentClause)

        val actual = parentClause.where(condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
