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
            queryString = "SELECT * JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = StandardJoinOnConditionClause(
            someBucket(),
            onCondition = parameterValue.asParameter(parameterName),
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
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = StandardJoinOnConditionClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(parameterName2),
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
        val expected = StandardJoinOnConditionClause(
            bucket,
            onCondition = condition,
            parentClause = parentClause,
        )

        val actual = parentClause.joinOnCondition(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT JOIN `someBucket` ON TRUE",
        )
        val underTest = LeftJoinOnConditionClause(
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
            queryString = "SELECT * LEFT JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeftJoinOnConditionClause(
            someBucket(),
            onCondition = parameterValue.asParameter(parameterName),
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
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName LEFT JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = LeftJoinOnConditionClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(parameterName2),
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
        val expected = LeftJoinOnConditionClause(bucket, onCondition = condition, parentClause = parentClause)

        val actual = parentClause.leftJoinOnCondition(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER JOIN `someBucket` ON TRUE",
        )
        val underTest = InnerJoinOnConditionClause(
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
            queryString = "SELECT * INNER JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest =
            InnerJoinOnConditionClause(someBucket(), onCondition = parameterValue.asParameter(parameterName), parentClause = someSelectClause())

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
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName INNER JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = InnerJoinOnConditionClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(parameterName2),
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
        val expected = InnerJoinOnConditionClause(bucket, onCondition = condition, parentClause = parentClause)

        val actual = parentClause.innerJoinOnCondition(bucket, onCondition = condition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support right join`() {
        val expected = DopeQuery(
            queryString = "SELECT * RIGHT JOIN `someBucket` ON TRUE",
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
            queryString = "SELECT * RIGHT JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = RightJoinClause(someBucket(), onCondition = parameterValue.asParameter(parameterName), parentClause = someSelectClause())

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
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName RIGHT JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = RightJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(parameterName2),
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
            queryString = "SELECT * JOIN `someBucket` ON KEYS `stringField`",
        )
        val underTest = StandardJoinOnKeysClause(someBucket(), onKeys = someStringField(), parentClause = someSelectClause())

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
        val underTest = StandardJoinOnKeysClause(
            someBucket(),
            onKeys = someStringField(),
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
        val underTest = StandardJoinOnKeysClause(
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
        val field = someStringField()
        val expected = StandardJoinOnKeysClause(bucket, onKeys = field, parentClause = parentClause)

        val actual = parentClause.joinOnKeys(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard join function with onKeys string`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someString = someString()
        val expected = StandardJoinOnKeysClause(bucket, onKeys = someString.toDopeType(), parentClause = parentClause)

        val actual = parentClause.joinOnKeys(bucket, onKeys = someString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard join function with onKeys string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = listOf(someString(), "test")
        val expected = StandardJoinOnKeysClause(bucket, onKeys = someStrings.toDopeType(), parentClause = parentClause)

        val actual = parentClause.joinOnKeys(bucket, onKeys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard join function with onKeys list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = someStringArrayField()
        val expected = StandardJoinOnKeysClause(bucket, onKeys = someStrings, parentClause = parentClause)

        val actual = parentClause.joinOnKeys(bucket, onKeys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT JOIN `someBucket` ON KEYS `stringField`",
        )
        val underTest = LeftJoinOnKeysClause(someBucket(), onKeys = someStringField(), parentClause = someSelectClause())

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
        val underTest = LeftJoinOnKeysClause(
            someBucket(),
            onKeys = someStringField(),
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
        val underTest = LeftJoinOnKeysClause(
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
        val field = someStringField()
        val expected = LeftJoinOnKeysClause(bucket, onKeys = field, parentClause = parentClause)

        val actual = parentClause.leftJoinOnKeys(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join function with onKeys with string`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someString = someString()
        val expected = LeftJoinOnKeysClause(bucket, onKeys = someString.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftJoinOnKeys(bucket, onKeys = someString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join function with onKeys with string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = listOf(someString(), "test")
        val expected = LeftJoinOnKeysClause(bucket, onKeys = someStrings.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftJoinOnKeys(bucket, onKeys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join function with onKeys string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = someStringArrayField()
        val expected = LeftJoinOnKeysClause(bucket, onKeys = someStrings, parentClause = parentClause)

        val actual = parentClause.leftJoinOnKeys(bucket, onKeys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join on keys`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER JOIN `someBucket` ON KEYS `stringField`",
        )
        val underTest = InnerJoinOnKeysClause(someBucket(), onKeys = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName INNER JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = InnerJoinOnKeysClause(
            someBucket(),
            onKeys = someStringField(),
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
        val underTest = InnerJoinOnKeysClause(
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
        val field = someStringField()
        val expected = InnerJoinOnKeysClause(bucket, onKeys = field, parentClause = parentClause)

        val actual = parentClause.innerJoinOnKeys(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join function with onKeys with string`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someString = someString()
        val expected = InnerJoinOnKeysClause(bucket, onKeys = someString.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerJoinOnKeys(bucket, onKeys = someString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join function with onKeys with string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = listOf(someString(), "test")
        val expected = InnerJoinOnKeysClause(bucket, onKeys = someStrings.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerJoinOnKeys(bucket, onKeys = someStrings)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join function with onKeys string list`() {
        val bucket = someBucket()
        val parentClause = someFromClause()
        val someStrings = someStringArrayField()
        val expected = InnerJoinOnKeysClause(bucket, onKeys = someStrings, parentClause = parentClause)

        val actual = parentClause.innerJoinOnKeys(bucket, onKeys = someStrings)

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
            queryString = "SELECT \$$parameterName JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = StandardJoinOnKeyClause(
            someBucket("bucket1"),
            onKey = someStringField(),
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
        val field = someStringField()
        val forBucket = someBucket("forBucket")
        val expected = StandardJoinOnKeyClause(bucket, onKey = field, forBucket, parentClause = parentClause)

        val actual = parentClause.joinOnKey(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left join on key`() {
        val expected = DopeQuery(
            queryString = "SELECT * LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
        )
        val underTest = LeftJoinOnKeyClause(
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
            queryString = "SELECT \$$parameterName LEFT JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeftJoinOnKeyClause(
            someBucket("bucket1"),
            onKey = someStringField(),
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
        val field = someStringField()
        val forBucket = someBucket("forBucket")
        val expected = LeftJoinOnKeyClause(bucket, onKey = field, forBucket, parentClause = parentClause)

        val actual = parentClause.leftJoinOnKey(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner join on key`() {
        val expected = DopeQuery(
            queryString = "SELECT * INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
        )
        val underTest = InnerJoinOnKeyClause(
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
            queryString = "SELECT \$$parameterName INNER JOIN `bucket1` ON KEY `stringField` FOR `bucket2`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = InnerJoinOnKeyClause(
            someBucket("bucket1"),
            onKey = someStringField(),
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
        val field = someStringField()
        val forBucket = someBucket("forBucket")
        val expected = InnerJoinOnKeyClause(bucket, onKey = field, forBucket, parentClause = parentClause)

        val actual = parentClause.innerJoinOnKey(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
