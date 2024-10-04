package ch.ergon.dope.resolvable.clause.setoperators

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.resolvable.clause.model.setoperators.IntersectOperator
import ch.ergon.dope.resolvable.clause.model.setoperators.intersect
import ch.ergon.dope.resolvable.clause.model.setoperators.intersectAll
import kotlin.test.Test
import kotlin.test.assertEquals

class IntersectOperatorTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support intersect operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) INTERSECT (SELECT * FROM `bucket2`)",
            parameters = emptyMap(),
        )
        val underTest = IntersectOperator(
            someFromClause(someBucket("bucket1")),
            someFromClause(someBucket("bucket2")),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support intersect all operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) INTERSECT ALL (SELECT * FROM `bucket2`)",
            parameters = emptyMap(),
        )
        val underTest = IntersectOperator(
            someFromClause(someBucket("bucket1")),
            someFromClause(someBucket("bucket2")),
            duplicatesAllowed = true,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support intersect operator extension`() {
        val leftSelectClause = someFromClause(someBucket("bucket1"))
        val rightSelectClause = someFromClause(someBucket("bucket2"))
        val expected = IntersectOperator(leftSelectClause, rightSelectClause)

        val actual = leftSelectClause.intersect(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support intersect all operator extension`() {
        val leftSelectClause = someFromClause(someBucket("bucket1"))
        val rightSelectClause = someFromClause(someBucket("bucket2"))
        val expected = IntersectOperator(leftSelectClause, rightSelectClause, duplicatesAllowed = true)

        val actual = leftSelectClause.intersectAll(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
