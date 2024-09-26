package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support update clause`() {
        val expected = DopeQuery(
            "UPDATE `someBucket`",
            emptyMap(),
            emptyList(),
        )
        val underTest = UpdateClause(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with an alias bucket`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` AS `bucket`",
            emptyMap(),
            emptyList(),
        )
        val underTest = UpdateClause(someBucket().alias("bucket"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
