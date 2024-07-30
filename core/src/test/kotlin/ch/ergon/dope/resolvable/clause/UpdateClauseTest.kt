package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateClauseTest {
    @Test
    fun `should support update clause`() {
        val expected = DopeQuery(
            "UPDATE `someBucket`",
            emptyMap(),
        )
        val underTest = UpdateClause(someBucket())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with an alias bucket`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` AS `bucket`",
            emptyMap(),
        )
        val underTest = UpdateClause(someBucket().alias("bucket"))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
