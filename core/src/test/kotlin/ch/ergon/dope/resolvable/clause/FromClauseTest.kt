package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import kotlin.test.Test
import kotlin.test.assertEquals

class FromClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support from`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket`",
            emptyMap(),
            emptyList(),
        )
        val underTest = FromClause(someBucket(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support from with alias bucket`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` AS `bucket`",
            emptyMap(),
            emptyList(),
        )
        val underTest = FromClause(someBucket().alias("bucket"), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support from function`() {
        val bucket = someBucket()
        val parentClause = someSelectClause()
        val expected = FromClause(bucket, parentClause)

        val actual = parentClause.from(bucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
