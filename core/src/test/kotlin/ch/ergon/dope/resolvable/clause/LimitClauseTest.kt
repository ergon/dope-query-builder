package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.clause.model.DeleteLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class LimitClauseTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support delete limit`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` LIMIT `numberField`",
            emptyMap(),
        )

        val actual = DeleteLimitClause(someNumberField(), someDeleteClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete limit with positional parameter`() {
        val paramValue = 2
        val expected = DopeQuery(
            "DELETE FROM `someBucket` LIMIT $1",
            mapOf("$1" to paramValue),
        )

        val actual = DeleteLimitClause(paramValue.asParameter(), someDeleteClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select limit`() {
        val expected = DopeQuery(
            "SELECT * LIMIT `numberField`",
            emptyMap(),
        )

        val actual = SelectLimitClause(someNumberField(), someSelectClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select limit with positional parameter`() {
        val paramValue = 5
        val expected = DopeQuery(
            "SELECT * LIMIT $1",
            mapOf("$1" to paramValue),
        )

        val actual = SelectLimitClause(paramValue.asParameter(), someSelectClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select limit with parameter and parent parameter`() {
        val paramValue = "param"
        val paramValue2 = 5
        val expected = DopeQuery(
            "SELECT $1 LIMIT $2",
            mapOf("$1" to paramValue, "$2" to paramValue2),
        )

        val actual = SelectLimitClause(paramValue2.asParameter(), someSelectClause(paramValue.asParameter())).build()

        assertEquals(expected, actual)
    }
}
