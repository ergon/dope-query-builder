package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.InnerNestClause
import ch.ergon.dope.resolvable.clause.model.LeftNestClause
import ch.ergon.dope.resolvable.clause.model.StandardNestClause
import ch.ergon.dope.resolvable.expression.type.TRUE
import kotlin.test.Test
import kotlin.test.assertEquals

class NestClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support standard nest with boolean condition`() {
        val expected = DopeQuery(
            queryString = "SELECT * NEST `someBucket` ON TRUE",
        )
        val underTest = StandardNestClause(
            someBucket(),
            onCondition = TRUE,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * NEST `someBucket` ON KEYS `stringField`",
        )
        val underTest = StandardNestClause(
            someBucket(),
            onKeys = someStringField(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with on key for bucket`() {
        val bucket = someBucket()
        val expected = DopeQuery(
            queryString = "SELECT * NEST `someBucket` ON KEY `stringField` FOR `someBucket`",
        )
        val underTest = StandardNestClause(
            bucket,
            onKey = someStringField(),
            forBucket = bucket,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with boolean condition function`() {
        val bucket = someBucket()
        val onCondition = TRUE
        val parentClause = someSelectClause()
        val expected = StandardNestClause(bucket, onCondition, parentClause)

        val actual = parentClause.nest(bucket, onCondition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard nest with on keys function`() {
        val bucket = someBucket()
        val onKeys = someStringField()
        val parentClause = someSelectClause()
        val expected = StandardNestClause(bucket, onKeys, parentClause)

        val actual = parentClause.nest(bucket, onKeys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard nest with on key for bucket function`() {
        val bucket = someBucket()
        val onKey = someStringField()
        val parentClause = someSelectClause()
        val expected = StandardNestClause(bucket, onKey, bucket, parentClause)

        val actual = parentClause.nest(bucket, onKey, bucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner nest with boolean condition`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER NEST `someBucket` ON TRUE",
        )
        val underTest = InnerNestClause(
            someBucket(),
            onCondition = TRUE,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER NEST `someBucket` ON KEYS `stringField`",
        )
        val underTest = InnerNestClause(
            someBucket(),
            onKeys = someStringField(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with on key for bucket`() {
        val bucket = someBucket()
        val expected = DopeQuery(
            queryString = "SELECT * INNER NEST `someBucket` ON KEY `stringField` FOR `someBucket`",
        )
        val underTest = InnerNestClause(
            bucket,
            onKey = someStringField(),
            forBucket = bucket,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with boolean condition function`() {
        val bucket = someBucket()
        val onCondition = TRUE
        val parentClause = someSelectClause()
        val expected = InnerNestClause(bucket, onCondition, parentClause)

        val actual = parentClause.innerNest(bucket, onCondition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner nest with on keys function`() {
        val bucket = someBucket()
        val onKeys = someStringField()
        val parentClause = someSelectClause()
        val expected = InnerNestClause(bucket, onKeys, parentClause)

        val actual = parentClause.innerNest(bucket, onKeys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner nest with on key for bucket function`() {
        val bucket = someBucket()
        val onKey = someStringField()
        val parentClause = someSelectClause()
        val expected = InnerNestClause(bucket, onKey, bucket, parentClause)

        val actual = parentClause.innerNest(bucket, onKey, bucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left nest with boolean condition`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT NEST `someBucket` ON TRUE",
        )
        val underTest = LeftNestClause(
            someBucket(),
            onCondition = TRUE,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT NEST `someBucket` ON KEYS `stringField`",
        )
        val underTest = LeftNestClause(
            someBucket(),
            onKeys = someStringField(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with on key for bucket`() {
        val bucket = someBucket()
        val expected = DopeQuery(
            queryString = "SELECT * LEFT NEST `someBucket` ON KEY `stringField` FOR `someBucket`",
        )
        val underTest = LeftNestClause(
            bucket,
            onKey = someStringField(),
            forBucket = bucket,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with boolean condition function`() {
        val bucket = someBucket()
        val onCondition = TRUE
        val parentClause = someSelectClause()
        val expected = LeftNestClause(bucket, onCondition, parentClause)

        val actual = parentClause.leftNest(bucket, onCondition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left nest with on keys function`() {
        val bucket = someBucket()
        val onKeys = someStringField()
        val parentClause = someSelectClause()
        val expected = LeftNestClause(bucket, onKeys, parentClause)

        val actual = parentClause.leftNest(bucket, onKeys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left nest with on key for bucket function`() {
        val bucket = someBucket()
        val onKey = someStringField()
        val parentClause = someSelectClause()
        val expected = LeftNestClause(bucket, onKey, bucket, parentClause)

        val actual = parentClause.leftNest(bucket, onKey, bucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
