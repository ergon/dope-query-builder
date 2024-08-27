package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.clause.model.DeleteOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class OffsetClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support delete offset`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` OFFSET `numberField`",
            emptyMap(),
        )
        val underTest = DeleteOffsetClause(someNumberField(), someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete offset with positional parameter`() {
        val parameterValue = 2
        val expected = DopeQuery(
            "DELETE FROM `someBucket` OFFSET $1",
            mapOf("$1" to parameterValue),
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
            "SELECT * OFFSET `numberField`",
            emptyMap(),
        )
        val underTest = SelectOffsetClause(someNumberField(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "SELECT * OFFSET $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = SelectOffsetClause(parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset with parameter and parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "SELECT $1 OFFSET $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
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
