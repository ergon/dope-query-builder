package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someKeyspace
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.RightJoinClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardJoinOnKeysClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class JoinClauseTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support standard join`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * JOIN `someBucket` ON TRUE",
        )
        val underTest = StandardJoinOnConditionClause(
            someKeyspace(),
            condition = someBooleanExpression(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = StandardJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue.asParameter(parameterName),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with positional parameter`() {
        val parameterValue = true
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * JOIN `someBucket` ON $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = StandardJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = StandardJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue2.asParameter(parameterName2),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 JOIN `someBucket` ON $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = StandardJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with condition`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = StandardJoinOnConditionClause(
            keyspace,
            condition = condition,
            parentClause = parentClause,
        )

        val actual = parentClause.join(keyspace, condition = condition)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support left join`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * LEFT JOIN `someBucket` ON TRUE",
        )
        val underTest = LeftJoinOnConditionClause(
            someKeyspace(),
            condition = someBooleanExpression(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * LEFT JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeftJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue.asParameter(parameterName),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with positional parameter`() {
        val parameterValue = true
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * LEFT JOIN `someBucket` ON $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LeftJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName LEFT JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = LeftJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue2.asParameter(parameterName2),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 LEFT JOIN `someBucket` ON $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = LeftJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with condition`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = LeftJoinOnConditionClause(keyspace, condition = condition, parentClause = parentClause)

        val actual = parentClause.leftJoin(keyspace, condition = condition)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support inner join`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * INNER JOIN `someBucket` ON TRUE",
        )
        val underTest = InnerJoinOnConditionClause(
            someKeyspace(),
            condition = someBooleanExpression(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * INNER JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest =
            InnerJoinOnConditionClause(someKeyspace(), condition = parameterValue.asParameter(parameterName), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with positional parameter`() {
        val parameterValue = true
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * INNER JOIN `someBucket` ON $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = InnerJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName INNER JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = InnerJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue2.asParameter(parameterName2),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 INNER JOIN `someBucket` ON $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = InnerJoinOnConditionClause(
            someKeyspace(),
            condition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with condition`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = InnerJoinOnConditionClause(keyspace, condition = condition, parentClause = parentClause)

        val actual = parentClause.innerJoin(keyspace, condition = condition)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support right join`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * RIGHT JOIN `someBucket` ON TRUE",
        )
        val underTest = RightJoinClause(
            someKeyspace(),
            condition = someBooleanExpression(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * RIGHT JOIN `someBucket` ON \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = RightJoinClause(someKeyspace(), condition = parameterValue.asParameter(parameterName), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with positional parameter`() {
        val parameterValue = true
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * RIGHT JOIN `someBucket` ON $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = RightJoinClause(
            someKeyspace(),
            condition = parameterValue.asParameter(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with named parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName RIGHT JOIN `someBucket` ON \$$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = RightJoinClause(
            someKeyspace(),
            condition = parameterValue2.asParameter(parameterName2),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with positional parameters in both join and select clause`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 RIGHT JOIN `someBucket` ON $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = RightJoinClause(
            someKeyspace(),
            condition = parameterValue2.asParameter(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join function with condition`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val condition = someBooleanExpression()
        val expected = RightJoinClause(keyspace, condition = condition, parentClause = parentClause)

        val actual = parentClause.rightJoin(keyspace, condition = condition)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support standard join on keys`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * JOIN `someBucket` ON KEYS `stringField`",
        )
        val underTest = StandardJoinOnKeyClause(someKeyspace(), key = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on keys with named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = StandardJoinOnKeyClause(
            someKeyspace(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on keys with positional parameter in parent`() {
        val parameterValue = 1
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = StandardJoinOnKeyClause(
            someKeyspace(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with keys`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val field = someStringField()
        val expected = StandardJoinOnKeyClause(keyspace, key = field, parentClause = parentClause)

        val actual = parentClause.join(keyspace, key = field)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support standard join function with keys string`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val someString = someString()
        val expected = StandardJoinOnKeyClause(keyspace, key = someString.toDopeType(), parentClause = parentClause)

        val actual = parentClause.join(keyspace, keys = someString)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support standard join function with keys string list`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val someStrings = listOf(someString(), "test")
        val expected = StandardJoinOnKeysClause(keyspace, keys = someStrings.toDopeType(), parentClause = parentClause)

        val actual = parentClause.join(keyspace, keys = someStrings)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support standard join function with keys list`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val someStrings = someStringArrayField()
        val expected = StandardJoinOnKeysClause(keyspace, keys = someStrings, parentClause = parentClause)

        val actual = parentClause.join(keyspace, keys = someStrings)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support left join on keys`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * LEFT JOIN `someBucket` ON KEYS `stringField`",
        )
        val underTest = LeftJoinOnKeyClause(someKeyspace(), key = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName LEFT JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeftJoinOnKeyClause(
            someKeyspace(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 LEFT JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LeftJoinOnKeyClause(
            someKeyspace(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with keys`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val field = someStringField()
        val expected = LeftJoinOnKeyClause(keyspace, key = field, parentClause = parentClause)

        val actual = parentClause.leftJoin(keyspace, key = field)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support left join function with keys with string`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val someString = someString()
        val expected = LeftJoinOnKeyClause(keyspace, key = someString.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftJoin(keyspace, key = someString)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support left join function with keys with string list`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val someStrings = listOf(someString(), "test")
        val expected = LeftJoinOnKeysClause(keyspace, keys = someStrings.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftJoin(keyspace, keys = someStrings)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support left join function with keys string list`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val someStrings = someStringArrayField()
        val expected = LeftJoinOnKeysClause(keyspace, keys = someStrings, parentClause = parentClause)

        val actual = parentClause.leftJoin(keyspace, keys = someStrings)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support inner join on keys`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * INNER JOIN `someBucket` ON KEYS `stringField`",
        )
        val underTest = InnerJoinOnKeyClause(someKeyspace(), key = someStringField(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $$parameterName INNER JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = InnerJoinOnKeyClause(
            someKeyspace(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 INNER JOIN `someBucket` ON KEYS `stringField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = InnerJoinOnKeyClause(
            someKeyspace(),
            key = someStringField(),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with keys`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val field = someStringField()
        val expected = InnerJoinOnKeyClause(keyspace, key = field, parentClause = parentClause)

        val actual = parentClause.innerJoin(keyspace, key = field)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support inner join function with keys with string`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val someString = someString()
        val expected = InnerJoinOnKeyClause(keyspace, key = someString.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerJoin(keyspace, key = someString)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support inner join function with keys with string list`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val someStrings = listOf(someString(), "test")
        val expected = InnerJoinOnKeysClause(keyspace, keys = someStrings.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerJoin(keyspace, keys = someStrings)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support inner join function with keys string list`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val someStrings = someStringArrayField()
        val expected = InnerJoinOnKeysClause(keyspace, keys = someStrings, parentClause = parentClause)

        val actual = parentClause.innerJoin(keyspace, keys = someStrings)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support standard join on key`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * JOIN `keyspace1` ON KEY `stringField` FOR `keyspace2`",
        )
        val underTest = StandardJoinOnKeyClause(
            someKeyspace("keyspace1"),
            key = someStringField(),
            someKeyspace("keyspace2"),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on key with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName JOIN `keyspace1` ON KEY `stringField` FOR `keyspace2`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = StandardJoinOnKeyClause(
            someKeyspace("keyspace1"),
            key = someStringField(),
            someKeyspace("keyspace2"),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on key with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 JOIN `keyspace1` ON KEY `stringField` FOR `keyspace2`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = StandardJoinOnKeyClause(
            someKeyspace("keyspace1"),
            key = someStringField(),
            someKeyspace("keyspace2"),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join function with key`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val field = someStringField()
        val forBucket = someKeyspace("keyspace")
        val expected = StandardJoinOnKeyClause(keyspace, key = field, forBucket, parentClause = parentClause)

        val actual = parentClause.join(keyspace, key = field, forBucket)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support left join on key`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * LEFT JOIN `keyspace1` ON KEY `stringField` FOR `keyspace2`",
        )
        val underTest = LeftJoinOnKeyClause(
            someKeyspace("keyspace1"),
            key = someStringField(),
            someKeyspace("keyspace2"),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on key with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName LEFT JOIN `keyspace1` ON KEY `stringField` FOR `keyspace2`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeftJoinOnKeyClause(
            someKeyspace("keyspace1"),
            key = someStringField(),
            someKeyspace("keyspace2"),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on key with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 LEFT JOIN `keyspace1` ON KEY `stringField` FOR `keyspace2`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LeftJoinOnKeyClause(
            someKeyspace("keyspace1"),
            key = someStringField(),
            someKeyspace("keyspace2"),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join function with key`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val field = someStringField()
        val forBucket = someKeyspace("keyspace")
        val expected = LeftJoinOnKeyClause(keyspace, key = field, forBucket, parentClause = parentClause)

        val actual = parentClause.leftJoin(keyspace, key = field, forBucket)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support inner join on key`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * INNER JOIN `keyspace1` ON KEY `stringField` FOR `keyspace2`",
        )
        val underTest = InnerJoinOnKeyClause(
            someKeyspace("keyspace1"),
            key = someStringField(),
            someKeyspace("keyspace2"),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on key with named parameter and named parameter in parent`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName INNER JOIN `keyspace1` ON KEY `stringField` FOR `keyspace2`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = InnerJoinOnKeyClause(
            someKeyspace("keyspace1"),
            key = someStringField(),
            someKeyspace("keyspace2"),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on key with positional parameter and positional parameter in parent`() {
        val parameterValue = 1
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 INNER JOIN `keyspace1` ON KEY `stringField` FOR `keyspace2`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = InnerJoinOnKeyClause(
            someKeyspace("keyspace1"),
            key = someStringField(),
            someKeyspace("keyspace2"),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join function with key`() {
        val keyspace = someKeyspace()
        val parentClause = someFromClause()
        val field = someStringField()
        val forBucket = someKeyspace("keyspace")
        val expected = InnerJoinOnKeyClause(keyspace, key = field, forBucket, parentClause = parentClause)

        val actual = parentClause.innerJoin(keyspace, key = field, forBucket)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
