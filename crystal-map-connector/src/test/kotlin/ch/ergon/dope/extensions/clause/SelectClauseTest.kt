package ch.ergon.dope.extensions.clause

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.clause.assignTo
import ch.ergon.dope.extension.clause.groupBy
import ch.ergon.dope.extension.clause.innerJoin
import ch.ergon.dope.extension.clause.join
import ch.ergon.dope.extension.clause.joinhint.keysHint
import ch.ergon.dope.extension.clause.leftJoin
import ch.ergon.dope.extension.clause.limit
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
import ch.ergon.dope.resolvable.clause.model.DopeVariable
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.InnerJoinClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinClause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
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
        val field = someCMNumberField()
        val parentClause = someFrom()
        val expected = StandardJoinClause(bucket, onKeys = field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.join(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select join on key for with CM`() {
        val bucket = someBucket()
        val field = someCMNumberField()
        val forBucket = someBucket()
        val parentClause = someFrom()
        val expected = StandardJoinClause(bucket, onKey = field.toDopeType(), forBucket, parentClause = parentClause)

        val actual = parentClause.join(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select inner join with CM`() {
        val bucket = someBucket()
        val field = someCMNumberField()
        val parentClause = someFrom()
        val expected = InnerJoinClause(bucket, onKeys = field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select inner join on key for with CM`() {
        val bucket = someBucket()
        val field = someCMNumberField()
        val forBucket = someBucket()
        val parentClause = someFrom()
        val expected = InnerJoinClause(bucket, onKey = field.toDopeType(), forBucket, parentClause = parentClause)

        val actual = parentClause.innerJoin(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select left join with CM`() {
        val bucket = someBucket()
        val field = someCMNumberField()
        val parentClause = someFrom()
        val expected = LeftJoinClause(bucket, onKeys = field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, onKeys = field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select left join on key for with CM`() {
        val bucket = someBucket()
        val field = someCMNumberField()
        val forBucket = someBucket()
        val parentClause = someFrom()
        val expected = LeftJoinClause(bucket, onKey = field.toDopeType(), forBucket, parentClause = parentClause)

        val actual = parentClause.leftJoin(bucket, onKey = field, forBucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select join use single key hint with CM`() {
        val bucket = someBucket()
        val field = someNumberField()
        val keysHint = keysHint(someCMStringField())
        val parentClause = someFrom()
        val expected = StandardJoinClause(
            bucket,
            onKeys = field,
            keysOrIndexHint = keysHint,
            parentClause = parentClause,
        )

        val actual = parentClause.join(bucket, onKeys = field, keysOrIndexHint = keysHint)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select join use multiple keys hint with CM`() {
        val bucket = someBucket()
        val field = someNumberField()
        val keysHint = keysHint(someCMStringList())
        val parentClause = someFrom()
        val expected = StandardJoinClause(
            bucket,
            onKeys = field,
            keysOrIndexHint = keysHint,
            parentClause = parentClause,
        )

        val actual = parentClause.join(bucket, onKeys = field, keysOrIndexHint = keysHint)

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
        val field = someCMNumberField()
        val parentClause = someSelect()
        val expected = SelectOrderByClause(OrderExpression(field.toDopeType()), parentClause = parentClause)

        val actual = parentClause.orderBy(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select order by with type and CMNumberField`() {
        val field = someCMNumberField()
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
        val field = someCMNumberField()
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
        val field = someCMNumberField()
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
        val value = someCMNumberField()
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
