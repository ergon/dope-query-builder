package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.InnerJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.InnerJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.InnerJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.RightJoinClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinOnKeysClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class JoinClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support standard join`() {
        val expected = DopeQuery(
            queryString = "SELECT * JOIN `someBucket` ON TRUE",
        )
        val underTest = StandardJoinOnConditionClause(
            someBucket(),
            condition = someBooleanExpression(),
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
            queryString = "SELECT * JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = StandardJoinOnConditionClause(
            someBucket(),
            condition = parameterValue.asParameter(parameterName),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            queryString = "SELECT * JOIN `someBucket` ON $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = StandardJoinOnConditionClause(
            someBucket(),
            condition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = StandardJoinOnConditionClause(
            someBucket(),
            condition = parameterValue2.asParameter(parameterName2),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            queryString = "SELECT $1 JOIN `someBucket` ON $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = StandardJoinOnConditionClause(
            someBucket(),
            condition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with condition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = StandardJoinOnConditionClause(
            bucket,
            condition = condition,
            parentClause = parentClause,
        )

        val actual = parentClause.join(bucket, condition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT JOIN `someBucket` ON TRUE",
        )
        val underTest = LeftJoinOnConditionClause(
            someBucket(),
            condition = someBooleanExpression(),
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
            queryString = "SELECT * LEFT JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeftJoinOnConditionClause(
            someBucket(),
            condition = parameterValue.asParameter(parameterName),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            queryString = "SELECT * LEFT JOIN `someBucket` ON $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LeftJoinOnConditionClause(
            someBucket(),
            condition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName LEFT JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = LeftJoinOnConditionClause(
            someBucket(),
            condition = parameterValue2.asParameter(parameterName2),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            queryString = "SELECT $1 LEFT JOIN `someBucket` ON $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = LeftJoinOnConditionClause(
            someBucket(),
            condition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with condition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = LeftJoinOnConditionClause(bucket, condition = condition, parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, condition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER JOIN `someBucket` ON TRUE",
        )
        val underTest = InnerJoinOnConditionClause(
            someBucket(),
            condition = someBooleanExpression(),
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
            queryString = "SELECT * INNER JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest =
            InnerJoinOnConditionClause(someBucket(), condition = parameterValue.asParameter(parameterName), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            queryString = "SELECT * INNER JOIN `someBucket` ON $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = InnerJoinOnConditionClause(
            someBucket(),
            condition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName INNER JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = InnerJoinOnConditionClause(
            someBucket(),
            condition = parameterValue2.asParameter(parameterName2),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            queryString = "SELECT $1 INNER JOIN `someBucket` ON $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = InnerJoinOnConditionClause(
            someBucket(),
            condition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with condition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = InnerJoinOnConditionClause(bucket, condition = condition, parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, condition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support right join`() {
        val expected = DopeQuery(
            queryString = "SELECT * RIGHT JOIN `someBucket` ON TRUE",
        )
        val underTest = RightJoinClause(
            someBucket(),
            condition = someBooleanExpression(),
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
            queryString = "SELECT * RIGHT JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = RightJoinClause(someBucket(), condition = parameterValue.asParameter(parameterName), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            queryString = "SELECT * RIGHT JOIN `someBucket` ON $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = RightJoinClause(
            someBucket(),
            condition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName RIGHT JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = RightJoinClause(
            someBucket(),
            condition = parameterValue2.asParameter(parameterName2),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            queryString = "SELECT $1 RIGHT JOIN `someBucket` ON $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = RightJoinClause(
            someBucket(),
            condition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join function with condition`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = RightJoinClause(bucket, condition = condition, parentClause = parentClause)

        val actual = parentClause.rightJoin(bucket, condition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    // ON KEYS

    @Test
    fun `should support standard join on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * JOIN `someBucket` ON KEYS `stringField`",
        )
        val underTest = StandardJoinOnKeyClause(someBucket(), key = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on keys with named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = StandardJoinOnKeyClause(
            someBucket(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on keys with positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "SELECT $1 JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = StandardJoinOnKeyClause(
            someBucket(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with keys`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someStringField()
        val expected = StandardJoinOnKeyClause(bucket, key = field, parentClause = parentClause)

        val actual = parentClause.join(bucket, key = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard join function with keys string`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someString = someString()
        val expected = StandardJoinOnKeyClause(bucket, key = someString.toDopeType(), parentClause = parentClause)

        val actual = parentClause.join(bucket, keys = someString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard join function with keys string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = listOf(someString(), "test")
        val expected = StandardJoinOnKeysClause(bucket, keys = someStrings.toDopeType(), parentClause = parentClause)

        val actual = parentClause.join(bucket, keys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard join function with keys list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = someStringArrayField()
        val expected = StandardJoinOnKeysClause(bucket, keys = someStrings, parentClause = parentClause)

        val actual = parentClause.join(bucket, keys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT JOIN `someBucket` ON KEYS `stringField`",
        )
        val underTest = LeftJoinOnKeyClause(someBucket(), key = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName LEFT JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeftJoinOnKeyClause(
            someBucket(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "SELECT $1 LEFT JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LeftJoinOnKeyClause(
            someBucket(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with keys`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someStringField()
        val expected = LeftJoinOnKeyClause(bucket, key = field, parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, key = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join function with keys with string`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someString = someString()
        val expected = LeftJoinOnKeyClause(bucket, key = someString.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, key = someString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join function with keys with string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = listOf(someString(), "test")
        val expected = LeftJoinOnKeysClause(bucket, keys = someStrings.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, keys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join function with keys string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = someStringArrayField()
        val expected = LeftJoinOnKeysClause(bucket, keys = someStrings, parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, keys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER JOIN `someBucket` ON KEYS `stringField`",
        )
        val underTest = InnerJoinOnKeyClause(someBucket(), key = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT $$parameterName INNER JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = InnerJoinOnKeyClause(
            someBucket(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "SELECT $1 INNER JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = InnerJoinOnKeyClause(
            someBucket(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with keys`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someStringField()
        val expected = InnerJoinOnKeyClause(bucket, key = field, parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, key = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join function with keys with string`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someString = someString()
        val expected = InnerJoinOnKeyClause(bucket, key = someString.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, key = someString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join function with keys with string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = listOf(someString(), "test")
        val expected = InnerJoinOnKeysClause(bucket, keys = someStrings.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, keys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join function with keys string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = someStringArrayField()
        val expected = InnerJoinOnKeysClause(bucket, keys = someStrings, parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, keys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    // ON KEY

    @Test
    fun `should support standard join on key`() {
        val expected = DopeQuery(
            queryString = "SELECT * JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
        )
        val underTest = StandardJoinOnKeyClause(
            someBucket("bucket1"),
            key = someStringField(),
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
            queryString = "SELECT \$$parameterName JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = StandardJoinOnKeyClause(
            someBucket("bucket1"),
            key = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on key with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "SELECT $1 JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = StandardJoinOnKeyClause(
            someBucket("bucket1"),
            key = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with key`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someStringField()
        val forBucket = someBucket("bucket")
        val expected = StandardJoinOnKeyClause(bucket, key = field, forBucket, parentClause = parentClause)

        val actual = parentClause.join(bucket, key = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join on key`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
        )
        val underTest = LeftJoinOnKeyClause(
            someBucket("bucket1"),
            key = someStringField(),
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
            queryString = "SELECT \$$parameterName LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeftJoinOnKeyClause(
            someBucket("bucket1"),
            key = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on key with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "SELECT $1 LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LeftJoinOnKeyClause(
            someBucket("bucket1"),
            key = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with key`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someStringField()
        val forBucket = someBucket("bucket")
        val expected = LeftJoinOnKeyClause(bucket, key = field, forBucket, parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, key = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join on key`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
        )
        val underTest = InnerJoinOnKeyClause(
            someBucket("bucket1"),
            key = someStringField(),
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
            queryString = "SELECT \$$parameterName INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = InnerJoinOnKeyClause(
            someBucket("bucket1"),
            key = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on key with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "SELECT $1 INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = InnerJoinOnKeyClause(
            someBucket("bucket1"),
            key = someStringField(),
            someBucket("bucket2"),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with key`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val field = someStringField()
        val forBucket = someBucket("bucket")
        val expected = InnerJoinOnKeyClause(bucket, key = field, forBucket, parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, key = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
