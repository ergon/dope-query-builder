package ch.ergon.dope.resolvable.clause.setoperators

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.resolvable.clause.SetOperator
import ch.ergon.dope.resolvable.clause.SetOperatorType.EXCEPT
import ch.ergon.dope.resolvable.clause.SetOperatorType.INTERSECT
import ch.ergon.dope.resolvable.clause.SetOperatorType.UNION
import ch.ergon.dope.resolvable.clause.except
import ch.ergon.dope.resolvable.clause.exceptAll
import ch.ergon.dope.resolvable.clause.intersect
import ch.ergon.dope.resolvable.clause.intersectAll
import ch.ergon.dope.resolvable.clause.union
import ch.ergon.dope.resolvable.clause.unionAll
import kotlin.test.Test
import kotlin.test.assertEquals

class SetOperatorTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support union operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) UNION (SELECT * FROM `bucket2`)",
        )
        val underTest = SetOperator(
            UNION,
            someFromClause(someBucket("bucket1")),
            someFromClause(someBucket("bucket2")),
            duplicatesAllowed = false,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support union all operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) UNION ALL (SELECT * FROM `bucket2`)",
        )
        val underTest = SetOperator(
            UNION,
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
        val expected = SetOperator(UNION, leftSelectClause, rightSelectClause, duplicatesAllowed = false)

        val actual = leftSelectClause.union(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support union all operator extension`() {
        val leftSelectClause = someFromClause(someBucket("bucket1"))
        val rightSelectClause = someFromClause(someBucket("bucket2"))
        val expected = SetOperator(UNION, leftSelectClause, rightSelectClause, duplicatesAllowed = true)

        val actual = leftSelectClause.unionAll(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support intersect operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) INTERSECT (SELECT * FROM `bucket2`)",
        )
        val underTest = SetOperator(
            INTERSECT,
            someFromClause(someBucket("bucket1")),
            someFromClause(someBucket("bucket2")),
            duplicatesAllowed = false,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support intersect all operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) INTERSECT ALL (SELECT * FROM `bucket2`)",
        )
        val underTest = SetOperator(
            INTERSECT,
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
        val expected = SetOperator(INTERSECT, leftSelectClause, rightSelectClause, duplicatesAllowed = false)

        val actual = leftSelectClause.intersect(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support intersect all operator extension`() {
        val leftSelectClause = someFromClause(someBucket("bucket1"))
        val rightSelectClause = someFromClause(someBucket("bucket2"))
        val expected = SetOperator(INTERSECT, leftSelectClause, rightSelectClause, duplicatesAllowed = true)

        val actual = leftSelectClause.intersectAll(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support except operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) EXCEPT (SELECT * FROM `bucket2`)",
        )
        val underTest = SetOperator(
            EXCEPT,
            someFromClause(someBucket("bucket1")),
            someFromClause(someBucket("bucket2")),
            duplicatesAllowed = false,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support except all operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) EXCEPT ALL (SELECT * FROM `bucket2`)",
        )
        val underTest = SetOperator(
            EXCEPT,
            someFromClause(someBucket("bucket1")),
            someFromClause(someBucket("bucket2")),
            duplicatesAllowed = true,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support except operator extension`() {
        val leftSelectClause = someFromClause(someBucket("bucket1"))
        val rightSelectClause = someFromClause(someBucket("bucket2"))
        val expected = SetOperator(EXCEPT, leftSelectClause, rightSelectClause, duplicatesAllowed = false)

        val actual = leftSelectClause.except(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support except all operator extension`() {
        val leftSelectClause = someFromClause(someBucket("bucket1"))
        val rightSelectClause = someFromClause(someBucket("bucket2"))
        val expected = SetOperator(EXCEPT, leftSelectClause, rightSelectClause, duplicatesAllowed = true)

        val actual = leftSelectClause.exceptAll(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
