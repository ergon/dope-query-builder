package ch.ergon.dope.resolvable.clause.setoperators

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.resolvable.clause.model.setoperators.ExceptOperator
import ch.ergon.dope.resolvable.clause.model.setoperators.except
import ch.ergon.dope.resolvable.clause.model.setoperators.exceptAll
import kotlin.test.Test
import kotlin.test.assertEquals

class ExceptOperatorTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support except operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) EXCEPT (SELECT * FROM `bucket2`)",
            parameters = emptyMap(),
        )
        val underTest = ExceptOperator(
            someFromClause(someBucket("bucket1")),
            someFromClause(someBucket("bucket2")),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support except all operator with two select statements`() {
        val expected = DopeQuery(
            "(SELECT * FROM `bucket1`) EXCEPT ALL (SELECT * FROM `bucket2`)",
            parameters = emptyMap(),
        )
        val underTest = ExceptOperator(
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
        val expected = ExceptOperator(leftSelectClause, rightSelectClause)

        val actual = leftSelectClause.except(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support except all operator extension`() {
        val leftSelectClause = someFromClause(someBucket("bucket1"))
        val rightSelectClause = someFromClause(someBucket("bucket2"))
        val expected = ExceptOperator(leftSelectClause, rightSelectClause, duplicatesAllowed = true)

        val actual = leftSelectClause.exceptAll(rightSelectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
