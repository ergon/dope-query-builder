package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.ReturningClause
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ReturningClauseTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support returning`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` RETURNING `stringField`",
            emptyMap(),
        )

        val actual = ReturningClause(someStringField(), parentClause = someDeleteClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support returning with multiple fields`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` RETURNING `stringField`, `numberField`",
            emptyMap(),
        )

        val actual = ReturningClause(someStringField(), someNumberField(), parentClause = someDeleteClause()).build()

        assertEquals(expected, actual)
    }
}
