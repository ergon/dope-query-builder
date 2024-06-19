package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class OrderByClauseTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support order by`() {
        val expected = DopeQuery(
            "SELECT * ORDER BY `stringField`",
            emptyMap(),
        )

        val actual = SelectOrderByClause(someStringField(), someSelectClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with parameter in parent`() {
        val paramValue = "asdf"
        val expected = DopeQuery(
            "SELECT $1 ORDER BY `stringField`",
            mapOf("$1" to paramValue),
        )

        val actual = SelectOrderByClause(someStringField(), someSelectClause(paramValue.asParameter())).build()

        assertEquals(expected, actual)
    }
}
