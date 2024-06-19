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

        val actual = DeleteWhereClause(someBooleanExpression(), someDeleteClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete where with positional parameter`() {
        val paramValue = true
        val expected = DopeQuery(
            "DELETE FROM `someBucket` WHERE $1",
            mapOf("$1" to paramValue),
        )

        val actual = DeleteWhereClause(paramValue.asParameter(), someDeleteClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where`() {
        val expected = DopeQuery(
            "SELECT * WHERE TRUE",
            emptyMap(),
        )

        val actual = SelectWhereClause(someBooleanExpression(), someSelectClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where with positional parameter`() {
        val paramValue = false
        val expected = DopeQuery(
            "SELECT * WHERE $1",
            mapOf("$1" to paramValue),
        )

        val actual = SelectWhereClause(paramValue.asParameter(), someSelectClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select where with parameter and parent parameter`() {
        val paramValue = "param"
        val paramValue2 = false
        val expected = DopeQuery(
            "SELECT $1 WHERE $2",
            mapOf("$1" to paramValue, "$2" to paramValue2),
        )

        val actual = SelectWhereClause(paramValue2.asParameter(), someSelectClause(paramValue.asParameter())).build()

        assertEquals(expected, actual)
    }
}
