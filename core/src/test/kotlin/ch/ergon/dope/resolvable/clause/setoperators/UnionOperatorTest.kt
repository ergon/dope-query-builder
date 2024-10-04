package ch.ergon.dope.resolvable.clause.setoperators

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.resolvable.clause.model.setoperators.UnionOperator
import ch.ergon.dope.resolvable.clause.model.setoperators.union
import ch.ergon.dope.resolvable.clause.model.setoperators.unionAll
import kotlin.test.Test
import kotlin.test.assertEquals

class UnionOperatorTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support union operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) UNION (SELECT * FROM `bucket2`)",
            parameters = emptyMap(),
        )
        val underTest = UnionOperator(
            someFromClause(someBucket("bucket1")),
            someFromClause(someBucket("bucket2")),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support union all operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) UNION ALL (SELECT * FROM `bucket2`)",
            parameters = emptyMap(),
        )
        val underTest = UnionOperator(
            someFromClause(someBucket("bucket1")),
            someFromClause(someBucket("bucket2")),
            duplicatesAllowed = true,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support union operator extension`() {
        val leftSelectClause = someFromClause(someBucket("bucket1"))
        val rightSelectClause = someFromClause(someBucket("bucket2"))
        val expected = UnionOperator(leftSelectClause, rightSelectClause)

        val actual = leftSelectClause.union(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support union all operator extension`() {
        val leftSelectClause = someFromClause(someBucket("bucket1"))
        val rightSelectClause = someFromClause(someBucket("bucket2"))
        val expected = UnionOperator(leftSelectClause, rightSelectClause, duplicatesAllowed = true)

        val actual = leftSelectClause.unionAll(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
