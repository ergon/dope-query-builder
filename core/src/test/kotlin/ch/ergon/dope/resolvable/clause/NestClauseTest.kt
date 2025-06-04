package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeysClause
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
        val underTest = StandardNestOnConditionClause(
            someBucket(),
            condition = TRUE,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * NEST `someBucket` ON KEYS `stringArrayField`",
        )
        val underTest = StandardNestOnKeysClause(
            someBucket(),
            keys = someStringArrayField(),
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
        val underTest = StandardNestOnKeyClause(
            bucket,
            key = someStringField(),
            bucket = bucket,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with boolean condition function`() {
        val bucket = someBucket()
        val condition = TRUE
        val parentClause = someSelectClause()
        val expected = StandardNestOnConditionClause(bucket, condition, parentClause)

        val actual = parentClause.nest(bucket, condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard nest with on keys function`() {
        val bucket = someBucket()
        val keys = someStringArrayField()
        val parentClause = someSelectClause()
        val expected = StandardNestOnKeysClause(bucket, keys, parentClause)

        val actual = parentClause.nest(bucket, keys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard nest with on key for bucket function`() {
        val bucket = someBucket()
        val key = someStringField()
        val parentClause = someSelectClause()
        val expected = StandardNestOnKeyClause(bucket, key, bucket, parentClause)

        val actual = parentClause.nest(bucket, key, bucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner nest with boolean condition`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER NEST `someBucket` ON TRUE",
        )
        val underTest = InnerNestOnConditionClause(
            someBucket(),
            condition = TRUE,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER NEST `someBucket` ON KEYS `stringArrayField`",
        )
        val underTest = InnerNestOnKeysClause(
            someBucket(),
            keys = someStringArrayField(),
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
        val underTest = InnerNestOnKeyClause(
            bucket,
            key = someStringField(),
            bucket = bucket,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with boolean condition function`() {
        val bucket = someBucket()
        val condition = TRUE
        val parentClause = someSelectClause()
        val expected = InnerNestOnConditionClause(bucket, condition, parentClause)

        val actual = parentClause.innerNest(bucket, condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner nest with on keys function`() {
        val bucket = someBucket()
        val keys = someStringArrayField()
        val parentClause = someSelectClause()
        val expected = InnerNestOnKeysClause(bucket, keys, parentClause)

        val actual = parentClause.innerNest(bucket, keys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner nest with on key for bucket function`() {
        val bucket = someBucket()
        val key = someStringField()
        val parentClause = someSelectClause()
        val expected = InnerNestOnKeyClause(bucket, key, bucket, parentClause)

        val actual = parentClause.innerNest(bucket, key, bucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left nest with boolean condition`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT NEST `someBucket` ON TRUE",
        )
        val underTest = LeftNestOnConditionClause(
            someBucket(),
            condition = TRUE,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT NEST `someBucket` ON KEYS `stringArrayField`",
        )
        val underTest = LeftNestOnKeysClause(
            someBucket(),
            keys = someStringArrayField(),
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
        val underTest = LeftNestOnKeyClause(
            bucket,
            key = someStringField(),
            bucket = bucket,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with boolean condition function`() {
        val bucket = someBucket()
        val condition = TRUE
        val parentClause = someSelectClause()
        val expected = LeftNestOnConditionClause(bucket, condition, parentClause)

        val actual = parentClause.leftNest(bucket, condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left nest with on keys function`() {
        val bucket = someBucket()
        val keys = someStringArrayField()
        val parentClause = someSelectClause()
        val expected = LeftNestOnKeysClause(bucket, keys, parentClause)

        val actual = parentClause.leftNest(bucket, keys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left nest with on key for bucket function`() {
        val bucket = someBucket()
        val key = someStringField()
        val parentClause = someSelectClause()
        val expected = LeftNestOnKeyClause(bucket, key, bucket, parentClause)

        val actual = parentClause.leftNest(bucket, key, bucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
