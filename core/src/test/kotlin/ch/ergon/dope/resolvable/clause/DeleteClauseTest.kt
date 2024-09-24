package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support delete`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket`",
            emptyMap(),
        )
        val underTest = DeleteClause(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete with alias bucket`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` AS `bucket`",
            emptyMap(),
        )
        val underTest = DeleteClause(someBucket().alias("bucket"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
