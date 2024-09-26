package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someUpdateClause
import ch.ergon.dope.resolvable.clause.model.DeleteLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.UpdateLimitClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LimitClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support delete limit`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` LIMIT `numberField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = DeleteLimitClause(someNumberField(), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete limit with named parameter`() {
        val parameterValue = 2
        val parameterName = "param"
        val expected = DopeQuery(
            "DELETE FROM `someBucket` LIMIT \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = DeleteLimitClause(parameterValue.asParameter(parameterName), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete limit with positional parameter`() {
        val parameterValue = 2
        val expected = DopeQuery(
            "DELETE FROM `someBucket` LIMIT $1",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = DeleteLimitClause(parameterValue.asParameter(), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete limit function`() {
        val numberField = someNumberField()
        val parentClause = someDeleteClause()
        val expected = DeleteLimitClause(numberField, parentClause)

        val actual = parentClause.limit(numberField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select limit with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "SELECT * LIMIT $1",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = SelectLimitClause(parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select limit with named parameter and named parent parameter`() {
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val parameterValue = "param"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "SELECT \$$parameterName1 LIMIT \$$parameterName2",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = SelectLimitClause(
            parameterValue2.asParameter(parameterName2),
            someSelectClause(parameterValue.asParameter(parameterName1)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select limit with positional parameter and positional parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "SELECT $1 LIMIT $2",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = SelectLimitClause(parameterValue2.asParameter(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select limit function`() {
        val numberField = someNumberField()
        val parentClause = someSelectClause()
        val expected = SelectLimitClause(numberField, parentClause)

        val actual = parentClause.limit(numberField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update limit`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` LIMIT `numberField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = UpdateLimitClause(someNumberField(), someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update limit with named parameter`() {
        val parameterValue = 5
        val parameterName = "param"
        val expected = DopeQuery(
            "UPDATE `someBucket` LIMIT \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = UpdateLimitClause(parameterValue.asParameter(parameterName), someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update limit with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "UPDATE `someBucket` LIMIT $1",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = UpdateLimitClause(parameterValue.asParameter(), someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update limit function`() {
        val numberField = someNumberField()
        val parentClause = someUpdateClause()
        val expected = UpdateLimitClause(numberField, parentClause)

        val actual = parentClause.limit(numberField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update limit function with number`() {
        val number = someNumber()
        val parentClause = someUpdateClause()
        val expected = UpdateLimitClause(number.toDopeType(), parentClause)

        val actual = parentClause.limit(number)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
