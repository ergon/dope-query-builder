package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderExpression
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.clause.model.OrderType.DESC
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.function.string.lower
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderByClauseTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support order expression without order type`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField`",
        )
        val underTest = OrderExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order expression with asc`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` ASC",
        )
        val underTest = OrderExpression(someStringField(), ASC)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order expression with desc`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` DESC",
        )
        val underTest = OrderExpression(someStringField(), DESC)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order expression with complex typeExpression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "LOWER(CONCAT(\"A\", \"B\")) DESC",
        )
        val underTest = OrderExpression("A".toDopeType().concat("B").lower(), DESC)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * ORDER BY `stringField` ASC",
        )
        val underTest = SelectOrderByClause(someOrderExpression(), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with named parameter in parent`() {
        val parameterValue = "asdf"
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName ORDER BY `stringField` ASC",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectOrderByClause(someOrderExpression(), parentClause = someSelectClause(parameterValue.asParameter(parameterName)))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with positional parameter in parent`() {
        val parameterValue = "asdf"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 ORDER BY `stringField` ASC",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectOrderByClause(someOrderExpression(), parentClause = someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with type ASC`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * ORDER BY `stringField` ASC",
        )
        val underTest = SelectOrderByClause(OrderExpression(someStringField(), ASC), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with named parameter in parent with type ASC`() {
        val parameterValue = "asdf"
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName ORDER BY `stringField` ASC",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectOrderByClause(
            OrderExpression(someStringField(), ASC),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with positional parameter in parent with type ASC`() {
        val parameterValue = "asdf"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 ORDER BY `stringField` ASC",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectOrderByClause(
            OrderExpression(someStringField(), ASC),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with type DESC`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * ORDER BY `stringField` DESC",
        )
        val underTest = SelectOrderByClause(OrderExpression(someStringField(), DESC), parentClause = someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with named parameter in parent with type DESC`() {
        val parameterValue = "asdf"
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT \$$parameterName ORDER BY `stringField` DESC",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectOrderByClause(
            OrderExpression(someStringField(), DESC),
            parentClause = someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with positional parameter in parent with type DESC`() {
        val parameterValue = "asdf"
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT $1 ORDER BY `stringField` DESC",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectOrderByClause(
            OrderExpression(someStringField(), DESC),
            parentClause = someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple order by`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * ORDER BY `stringField` DESC, `numberField`, `booleanField` ASC",
        )
        val underTest = SelectOrderByClause(
            OrderExpression(someStringField(), DESC),
            listOf(
                OrderExpression(someNumberField()),
                OrderExpression(someBooleanField(), ASC),
            ),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by function`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val expected = SelectOrderByClause(OrderExpression(stringField), parentClause = parentClause)

        val actual = parentClause.orderBy(stringField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support order by function with type`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val orderType = ASC
        val expected = SelectOrderByClause(OrderExpression(stringField, orderType), parentClause = parentClause)

        val actual = parentClause.orderBy(stringField, orderType)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support order by function with orderExpression`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val orderExpression = OrderExpression(stringField)
        val expected = SelectOrderByClause(orderExpression, parentClause = parentClause)

        val actual = parentClause.orderBy(orderExpression)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support order by function with multiple orderExpressions`() {
        val parentClause = someSelectClause()
        val orderExpression1 = OrderExpression(someStringField())
        val orderExpression2 = OrderExpression(someNumberField(), ASC)
        val expected = SelectOrderByClause(orderExpression1, listOf(orderExpression2), parentClause = parentClause)

        val actual = parentClause.orderBy(orderExpression1, orderExpression2)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support order by function with then order by with orderExpression`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val orderType = ASC
        val orderExpression = OrderExpression(someNumberField())
        val expected = SelectOrderByClause(OrderExpression(stringField, orderType), listOf(orderExpression), parentClause = parentClause)

        val actual = parentClause.orderBy(stringField, orderType).thenOrderBy(orderExpression)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support order by function with then order by`() {
        val stringField = someStringField()
        val numberField = someNumberField()
        val parentClause = someSelectClause()
        val orderType = ASC
        val orderType2 = DESC
        val expected = SelectOrderByClause(
            OrderExpression(stringField, orderType),
            listOf(OrderExpression(numberField, orderType2)),
            parentClause = parentClause,
        )

        val actual = parentClause.orderBy(stringField, orderType).thenOrderBy(numberField, orderType2)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
