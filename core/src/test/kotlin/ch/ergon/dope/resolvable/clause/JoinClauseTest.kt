package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.InnerJoinClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinClause
import ch.ergon.dope.resolvable.clause.model.RightJoinClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class JoinClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support standard join`() {
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON TRUE",
            emptyMap(),
            manager,
        )
        val underTest = StandardJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON $1",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = StandardJoinClause(someBucket(), onCondition = parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with parameter and parameter in parent`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 JOIN `someBucket` ON $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = StandardJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with onCondition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = StandardJoinClause(bucket, onCondition = condition, parentClause)

        val actual = parentClause.join(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON TRUE",
            emptyMap(),
            manager,
        )
        val underTest = LeftJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON $1",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = LeftJoinClause(someBucket(), onCondition = parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with parameter and parameter in parent`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `someBucket` ON $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = LeftJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with onCondition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = LeftJoinClause(bucket, onCondition = condition, parentClause)

        val actual = parentClause.leftJoin(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON TRUE",
            emptyMap(),
            manager,
        )
        val underTest = InnerJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON $1",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = InnerJoinClause(someBucket(), onCondition = parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with parameter and parameter in parent`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `someBucket` ON $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = InnerJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with onCondition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = InnerJoinClause(bucket, onCondition = condition, parentClause)

        val actual = parentClause.innerJoin(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support right join`() {
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON TRUE",
            emptyMap(),
            manager,
        )
        val underTest = RightJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON $1",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = RightJoinClause(someBucket(), onCondition = parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with parameter and parameter in parent`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 RIGHT JOIN `someBucket` ON $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = RightJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join function with onCondition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = RightJoinClause(bucket, onCondition = condition, parentClause)

        val actual = parentClause.rightJoin(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    // ON KEYS

    @Test
    fun `should support standard join on keys`() {
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
            manager,
        )
        val underTest = StandardJoinClause(someBucket(), onKeys = someStringField(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on keys and parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = StandardJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with onKeys`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someNumberField()
        val expected = StandardJoinClause(bucket, onKeys = field, parentClause)

        val actual = parentClause.join(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join on keys`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
            manager,
        )
        val underTest = LeftJoinClause(someBucket(), onKeys = someStringField(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys with parameter and parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = LeftJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with onKeys`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someNumberField()
        val expected = LeftJoinClause(bucket, onKeys = field, parentClause)

        val actual = parentClause.leftJoin(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join on keys`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
            manager,
        )
        val underTest = InnerJoinClause(someBucket(), onKeys = someStringField(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with parameter and parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = InnerJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with onKeys`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someNumberField()
        val expected = InnerJoinClause(bucket, onKeys = field, parentClause)

        val actual = parentClause.innerJoin(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    // ON KEY

    @Test
    fun `should support standard join on key`() {
        val expected = DopeQuery(
            "SELECT * JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            emptyMap(),
            manager,
        )
        val underTest = StandardJoinClause(someBucket("bucket1"), onKey = someStringField(), someBucket("bucket2"), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on key and parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = StandardJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with onKey`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someNumberField()
        val forBucket = someBucket("forBucket")
        val expected = StandardJoinClause(bucket, onKey = field, forBucket, parentClause)

        val actual = parentClause.join(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join on key`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            emptyMap(),
            manager,
        )
        val underTest = LeftJoinClause(someBucket("bucket1"), onKey = someStringField(), someBucket("bucket2"), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on key with parameter and parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = LeftJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with onKey`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someNumberField()
        val forBucket = someBucket("forBucket")
        val expected = LeftJoinClause(bucket, onKey = field, forBucket, parentClause)

        val actual = parentClause.leftJoin(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join on key`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            emptyMap(),
            manager,
        )
        val underTest = InnerJoinClause(someBucket("bucket1"), onKey = someStringField(), someBucket("bucket2"), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on key with parameter and parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = InnerJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with onKey`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someNumberField()
        val forBucket = someBucket("forBucket")
        val expected = InnerJoinClause(bucket, onKey = field, forBucket, parentClause)

        val actual = parentClause.innerJoin(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
