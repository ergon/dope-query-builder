package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.clause.model.DeleteOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class OffsetClauseTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support delete offset`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` OFFSET `numberField`",
            emptyMap(),
        )
        val underTest = DeleteOffsetClause(someNumberField(), someDeleteClause())

        val actual = underTest.toDopeQuery()

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

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset`() {
        val expected = DopeQuery(
            "SELECT * OFFSET `numberField`",
            emptyMap(),
        )
        val underTest = SelectOffsetClause(someNumberField(), someSelectClause())

        val actual = underTest.toDopeQuery()

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

        val actual = underTest.toDopeQuery()

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

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
