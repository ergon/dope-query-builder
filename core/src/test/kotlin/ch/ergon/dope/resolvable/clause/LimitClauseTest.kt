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
        )
        val underTest = DeleteLimitClause(someNumberField(), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

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
    fun `should support select limit`() {
        val expected = DopeQuery(
            "SELECT * LIMIT `numberField`",
            emptyMap(),
        )
        val underTest = SelectLimitClause(someNumberField(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

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

        val actual = underTest.toDopeQuery(manager)

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
        )
        val underTest = UpdateLimitClause(someNumberField(), someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update limit with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "UPDATE `someBucket` LIMIT $1",
            mapOf("$1" to parameterValue),
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
