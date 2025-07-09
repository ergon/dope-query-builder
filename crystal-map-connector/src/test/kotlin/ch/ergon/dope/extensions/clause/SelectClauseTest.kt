package ch.ergon.dope.extensions.clause

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.clause.assignTo
import ch.ergon.dope.extension.clause.groupBy
import ch.ergon.dope.extension.clause.innerJoin
import ch.ergon.dope.extension.clause.innerNest
import ch.ergon.dope.extension.clause.join
import ch.ergon.dope.extension.clause.joinHint.keysHint
import ch.ergon.dope.extension.clause.leftJoin
import ch.ergon.dope.extension.clause.leftNest
import ch.ergon.dope.extension.clause.limit
import ch.ergon.dope.extension.clause.nest
import ch.ergon.dope.extension.clause.offset
import ch.ergon.dope.extension.clause.orderBy
import ch.ergon.dope.extension.clause.thenOrderBy
import ch.ergon.dope.extension.clause.unnest
import ch.ergon.dope.extension.clause.where
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someFrom
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderBy
import ch.ergon.dope.helper.someSelect
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeysClause
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support select where with CM`() {
        val field = someCMBooleanField()
        val parentClause = someSelect()
        val expected = SelectWhereClause(field.toDopeType(), parentClause)

        val actual = parentClause.where(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard nest on keys with CM`() {
        val bucket = someBucket()
        val field = someCMStringList()
        val parentClause = someFrom()
        val expected = StandardNestOnKeysClause(bucket, keys = field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.nest(bucket, keys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support standard nest on key for bucket with CM`() {
        val bucket = someBucket()
        val field = someCMStringField()
        val forBucket = someBucket()
        val parentClause = someFrom()
        val expected = StandardNestOnKeyClause(bucket, key = field.toDopeType(), forBucket, parentClause = parentClause)

        val actual = parentClause.nest(bucket, key = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner nest on keys with CM`() {
        val bucket = someBucket()
        val field = someCMStringList()
        val parentClause = someFrom()
        val expected = InnerNestOnKeysClause(bucket, keys = field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerNest(bucket, keys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support inner nest on key for bucket with CM`() {
        val bucket = someBucket()
        val field = someCMStringField()
        val forBucket = someBucket()
        val parentClause = someFrom()
        val expected = InnerNestOnKeyClause(bucket, key = field.toDopeType(), forBucket, parentClause = parentClause)

        val actual = parentClause.innerNest(bucket, key = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left nest on keys with CM`() {
        val bucket = someBucket()
        val field = someCMStringList()
        val parentClause = someFrom()
        val expected = LeftNestOnKeysClause(bucket, keys = field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftNest(bucket, keys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support left nest on key for bucket with CM`() {
        val bucket = someBucket()
        val field = someCMStringField()
        val forBucket = someBucket()
        val parentClause = someFrom()
        val expected = LeftNestOnKeyClause(bucket, key = field.toDopeType(), forBucket, parentClause = parentClause)

        val actual = parentClause.leftNest(bucket, key = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select unnest with CM Number`() {
        val field = someCMNumberList()
        val parentClause = someFrom()
        val expected = UnnestClause(field.toDopeType(), parentClause)

        val actual = parentClause.unnest(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select unnest with CM String`() {
        val field = someCMStringList()
        val parentClause = someFrom()
        val expected = UnnestClause(field.toDopeType(), parentClause)

        val actual = parentClause.unnest(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select unnest with CM Boolean`() {
        val field = someCMBooleanList()
        val parentClause = someFrom()
        val expected = UnnestClause(field.toDopeType(), parentClause)

        val actual = parentClause.unnest(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select join with CM`() {
        val bucket = someBucket()
        val field = someCMStringField()
        val parentClause = someFrom()
        val expected = StandardJoinOnKeyClause(bucket, key = field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.join(bucket, key = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select join on key for with CM`() {
        val bucket = someBucket()
        val field = someCMStringField()
        val forBucket = someBucket()
        val parentClause = someFrom()
        val expected = StandardJoinOnKeyClause(bucket, key = field.toDopeType(), forBucket, parentClause = parentClause)

        val actual = parentClause.join(bucket, key = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select inner join with CM`() {
        val bucket = someBucket()
        val field = someCMStringField()
        val parentClause = someFrom()
        val expected = InnerJoinOnKeyClause(bucket, key = field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, key = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select inner join on key for with CM`() {
        val bucket = someBucket()
        val field = someCMStringField()
        val forBucket = someBucket()
        val parentClause = someFrom()
        val expected = InnerJoinOnKeyClause(bucket, key = field.toDopeType(), forBucket, parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, key = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select left join with CM`() {
        val bucket = someBucket()
        val field = someCMStringField()
        val parentClause = someFrom()
        val expected = LeftJoinOnKeyClause(bucket, key = field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, key = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select left join on key for with CM`() {
        val bucket = someBucket()
        val field = someCMStringField()
        val forBucket = someBucket()
        val parentClause = someFrom()
        val expected = LeftJoinOnKeyClause(bucket, key = field.toDopeType(), forBucket, parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, key = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select join use single key hint with CM`() {
        val bucket = someBucket()
        val field = someStringField()
        val keysHint = keysHint(someCMStringField())
        val parentClause = someFrom()
        val expected = StandardJoinOnKeyClause(
            bucket,
            key = field,
            keysOrIndexHint = keysHint,
            parentClause = parentClause,
        )

        val actual = parentClause.join(bucket, key = field, keysOrIndexHint = keysHint)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select join use multiple keys hint with CM`() {
        val bucket = someBucket()
        val field = someStringField()
        val keysHint = keysHint(someCMStringList())
        val parentClause = someFrom()
        val expected = StandardJoinOnKeyClause(
            bucket,
            key = field,
            keysOrIndexHint = keysHint,
            parentClause = parentClause,
        )

        val actual = parentClause.join(bucket, key = field, keysOrIndexHint = keysHint)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select group by with CM`() {
        val field = someCMBooleanField()
        val parentClause = someSelect()
        val expected = GroupByClause(field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.groupBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select group by with multiple CM`() {
        val field1 = someCMBooleanField()
        val field2 = someCMNumberList()
        val parentClause = someSelect()
        val expected = GroupByClause(field1.toDopeType(), field2.toDopeType(), parentClause = parentClause)

        val actual = parentClause.groupBy(field1, field2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with CMNumberField`() {
        val field = someCMStringField()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType()), parentClause = parentClause)

        val actual = parentClause.orderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with type and CMNumberField`() {
        val field = someCMStringField()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType(), orderType), parentClause = parentClause)

        val actual = parentClause.orderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with CMStringField`() {
        val field = someCMStringField()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType()), parentClause = parentClause)

        val actual = parentClause.orderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with type and CMStringField`() {
        val field = someCMStringField()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType(), orderType), parentClause = parentClause)

        val actual = parentClause.orderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with CMBooleanField`() {
        val field = someCMBooleanField()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType()), parentClause = parentClause)

        val actual = parentClause.orderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with type and CMBooleanField`() {
        val field = someCMBooleanField()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType(), orderType), parentClause = parentClause)

        val actual = parentClause.orderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with CMNumberList`() {
        val field = someCMNumberList()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType()), parentClause = parentClause)

        val actual = parentClause.orderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with type and CMNumberList`() {
        val field = someCMNumberList()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType(), orderType), parentClause = parentClause)

        val actual = parentClause.orderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with CMStringList`() {
        val field = someCMStringList()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType()), parentClause = parentClause)

        val actual = parentClause.orderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with type and CMStringList`() {
        val field = someCMStringList()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType(), orderType), parentClause = parentClause)

        val actual = parentClause.orderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with CMBooleanList`() {
        val field = someCMBooleanList()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType()), parentClause = parentClause)

        val actual = parentClause.orderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with type and CMBooleanList`() {
        val field = someCMBooleanList()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType(), orderType), parentClause = parentClause)

        val actual = parentClause.orderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with CMNumberField`() {
        val field = someCMStringField()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType()),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with type and CMNumberField`() {
        val field = someCMStringField()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType(), orderType),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with CMStringField`() {
        val field = someCMStringField()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType()),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with type and CMStringField`() {
        val field = someCMStringField()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType(), orderType),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with CMBooleanField`() {
        val field = someCMBooleanField()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType()),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with type and CMBooleanField`() {
        val field = someCMBooleanField()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType(), orderType),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with CMNumberList`() {
        val field = someCMNumberList()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType()),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with type and CMNumberList`() {
        val field = someCMNumberList()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType(), orderType),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with CMStringList`() {
        val field = someCMStringList()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType()),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with type and CMStringList`() {
        val field = someCMStringList()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType(), orderType),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with CMBooleanList`() {
        val field = someCMBooleanList()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType()),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select then order by with type and CMBooleanList`() {
        val field = someCMBooleanList()
        val parentClause = someSelect()
        val orderType = OrderType.ASC
        val expected = SelectOrderByClause(
            OrderExpression(someNumberField(), OrderType.ASC),
            OrderExpression(field.toDopeType(), orderType),
            parentClause = parentClause,
        )

        val actual = someOrderBy(parentClause).thenOrderBy(field, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select limit with CM`() {
        val field = someCMNumberField()
        val parentClause = someSelect()
        val expected = SelectLimitClause(field.toDopeType(), parentClause)

        val actual = parentClause.limit(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select offset with CM`() {
        val field = someCMNumberField()
        val parentClause = someSelect()
        val expected = SelectOffsetClause(field.toDopeType(), parentClause)

        val actual = parentClause.offset(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DopeVariables with cmNumberField`() {
        val name = someString()
        val value = someCMStringField()
        val expected = DopeVariable(name, value.toDopeType())

        val actual = name.assignTo(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DopeVariables with cmStringField`() {
        val name = someString()
        val value = someCMStringField()
        val expected = DopeVariable(name, value.toDopeType())

        val actual = name.assignTo(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DopeVariables with cmBooleanField`() {
        val name = someString()
        val value = someCMBooleanField()
        val expected = DopeVariable(name, value.toDopeType())

        val actual = name.assignTo(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DopeVariables with cmNumberList`() {
        val name = someString()
        val value = someCMNumberList()
        val expected = DopeVariable(name, value.toDopeType())

        val actual = name.assignTo(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DopeVariables with cmStringList`() {
        val name = someString()
        val value = someCMStringList()
        val expected = DopeVariable(name, value.toDopeType())

        val actual = name.assignTo(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DopeVariables with cmBooleanList`() {
        val name = someString()
        val value = someCMBooleanList()
        val expected = DopeVariable(name, value.toDopeType())

        val actual = name.assignTo(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
