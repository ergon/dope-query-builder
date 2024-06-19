package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class FromClauseTest {
    @Test
    fun `should support from`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket`",
            emptyMap(),
        )

        val actual = FromClause(someBucket(), someSelectClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support from with alias bucket`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` AS `bucket`",
            emptyMap(),
        )

        val actual = FromClause(someBucket().alias("bucket"), someSelectClause()).build()

        assertEquals(expected, actual)
    }
}
