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

        val actual = DeleteOffsetClause(someNumberField(), someDeleteClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete offset with positional parameter`() {
        val paramValue = 2
        val expected = DopeQuery(
            "DELETE FROM `someBucket` OFFSET $1",
            mapOf("$1" to paramValue),
        )

        val actual = DeleteOffsetClause(paramValue.asParameter(), someDeleteClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset`() {
        val expected = DopeQuery(
            "SELECT * OFFSET `numberField`",
            emptyMap(),
        )

        val actual = SelectOffsetClause(someNumberField(), someSelectClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset with positional parameter`() {
        val paramValue = 5
        val expected = DopeQuery(
            "SELECT * OFFSET $1",
            mapOf("$1" to paramValue),
        )

        val actual = SelectOffsetClause(paramValue.asParameter(), someSelectClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select offset with parameter and parent parameter`() {
        val paramValue = "param"
        val paramValue2 = 5
        val expected = DopeQuery(
            "SELECT $1 OFFSET $2",
            mapOf("$1" to paramValue, "$2" to paramValue2),
        )

        val actual = SelectOffsetClause(paramValue2.asParameter(), someSelectClause(paramValue.asParameter())).build()

        assertEquals(expected, actual)
    }
}
