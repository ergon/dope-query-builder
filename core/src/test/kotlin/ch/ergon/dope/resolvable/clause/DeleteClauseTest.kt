package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class DeleteClauseTest {
    @Test
    fun `should support delete`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket`",
            emptyMap(),
        )

        val actual = DeleteClause(someBucket()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete with alias bucket`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` AS `bucket`",
            emptyMap(),
        )

        val actual = DeleteClause(someBucket().alias("bucket")).toDopeQuery()

        assertEquals(expected, actual)
    }
}
