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
import kotlin.test.Test
import kotlin.test.assertEquals

class JoinClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support standard join`() {
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON TRUE",
            emptyMap(),
            emptyList(),
        )
        val underTest = StandardJoinClause(
            someBucket(),
            onCondition = someBooleanExpression(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = StandardJoinClause(someBucket(), onCondition = parameterValue.asParameter(parameterName), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON $1",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = StandardJoinClause(
            someBucket(),
            onCondition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "SELECT \$$parameterName1 JOIN `someBucket` ON \$$parameterName2",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = StandardJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(parameterName2),
            someSelectClause(parameterValue.asParameter(parameterName1)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 JOIN `someBucket` ON $2",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = StandardJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with onCondition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = StandardJoinClause(
            bucket,
            onCondition = condition,
            parentClause = parentClause,
        )

        val actual = parentClause.join(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON TRUE",
            emptyMap(),
            emptyList(),
        )
        val underTest = LeftJoinClause(
            someBucket(),
            onCondition = someBooleanExpression(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = LeftJoinClause(someBucket(), onCondition = parameterValue.asParameter(parameterName), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON $1",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = LeftJoinClause(
            someBucket(),
            onCondition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "SELECT \$$parameterName1 LEFT JOIN `someBucket` ON \$$parameterName2",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = LeftJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(parameterName2),
            someSelectClause(parameterValue.asParameter(parameterName1)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `someBucket` ON $2",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = LeftJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with onCondition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = LeftJoinClause(bucket, onCondition = condition, parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON TRUE",
            emptyMap(),
            emptyList(),
        )
        val underTest = InnerJoinClause(
            someBucket(),
            onCondition = someBooleanExpression(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = InnerJoinClause(someBucket(), onCondition = parameterValue.asParameter(parameterName), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON $1",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = InnerJoinClause(
            someBucket(),
            onCondition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "SELECT \$$parameterName1 INNER JOIN `someBucket` ON \$$parameterName2",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = InnerJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(parameterName2),
            someSelectClause(parameterValue.asParameter(parameterName1)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `someBucket` ON $2",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = InnerJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with onCondition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = InnerJoinClause(bucket, onCondition = condition, parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support right join`() {
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON TRUE",
            emptyMap(),
            emptyList(),
        )
        val underTest = RightJoinClause(
            someBucket(),
            onCondition = someBooleanExpression(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = RightJoinClause(someBucket(), onCondition = parameterValue.asParameter(parameterName), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON $1",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = RightJoinClause(
            someBucket(),
            onCondition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "SELECT \$$parameterName1 RIGHT JOIN `someBucket` ON \$$parameterName2",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = RightJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(parameterName2),
            someSelectClause(parameterValue.asParameter(parameterName1)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 RIGHT JOIN `someBucket` ON $2",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = RightJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join function with onCondition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = RightJoinClause(bucket, onCondition = condition, parentClause = parentClause)

        val actual = parentClause.rightJoin(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    // ON KEYS

    @Test
    fun `should support standard join on keys`() {
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = StandardJoinClause(someBucket(), onKeys = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on keys with named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT \$$parameterName JOIN `someBucket` ON KEYS `stringField`",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = StandardJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(parameterValue.asParameter(parameterName)))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on keys with positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = StandardJoinClause(
            someBucket(),
            onKeys = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with onKeys`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someNumberField()
        val expected = StandardJoinClause(bucket, onKeys = field, parentClause = parentClause)

        val actual = parentClause.join(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join on keys`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = LeftJoinClause(someBucket(), onKeys = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT \$$parameterName LEFT JOIN `someBucket` ON KEYS `stringField`",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = LeftJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(parameterValue.asParameter(parameterName)))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = LeftJoinClause(
            someBucket(),
            onKeys = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with onKeys`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someNumberField()
        val expected = LeftJoinClause(bucket, onKeys = field, parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join on keys`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = InnerJoinClause(someBucket(), onKeys = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT \$$parameterName INNER JOIN `someBucket` ON KEYS `stringField`",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = InnerJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(parameterValue.asParameter(parameterName)))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = InnerJoinClause(
            someBucket(),
            onKeys = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with onKeys`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someNumberField()
        val expected = InnerJoinClause(bucket, onKeys = field, parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    // ON KEY

    @Test
    fun `should support standard join on key`() {
        val expected = DopeQuery(
            "SELECT * JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            emptyMap(),
            emptyList(),
        )
        val underTest = StandardJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on key with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT \$$parameterName JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = StandardJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on key with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = StandardJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(parameterValue.asParameter()),
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
        val expected = StandardJoinClause(bucket, onKey = field, forBucket, parentClause = parentClause)

        val actual = parentClause.join(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join on key`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            emptyMap(),
            emptyList(),
        )
        val underTest = LeftJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on key with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT \$$parameterName LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = LeftJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on key with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = LeftJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(parameterValue.asParameter()),
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
        val expected = LeftJoinClause(bucket, onKey = field, forBucket, parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join on key`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            emptyMap(),
            emptyList(),
        )
        val underTest = InnerJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on key with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT \$$parameterName INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = InnerJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on key with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = InnerJoinClause(
            someBucket("bucket1"),
            onKey = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(parameterValue.asParameter()),
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
        val expected = InnerJoinClause(bucket, onKey = field, forBucket, parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
