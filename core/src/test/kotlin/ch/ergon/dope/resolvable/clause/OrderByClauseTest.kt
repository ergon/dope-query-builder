package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderExpression
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.OrderByType.ASC
import ch.ergon.dope.resolvable.clause.model.OrderByType.DESC
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.lower
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderByClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support order expression without order type`() {
        val expected = DopeQuery(
            queryString = "`stringField`",
        )
        val underTest = OrderExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order expression with asc`() {
        val expected = DopeQuery(
            queryString = "`stringField` ASC",
        )
        val underTest = OrderExpression(someStringField(), ASC)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order expression with desc`() {
        val expected = DopeQuery(
            queryString = "`stringField` DESC",
        )
        val underTest = OrderExpression(someStringField(), DESC)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order expression with complex typeExpression`() {
        val expected = DopeQuery(
            queryString = "LOWER(CONCAT(\"A\", \"B\")) DESC",
        )
        val underTest = OrderExpression(lower(concat("A", "B")), DESC)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by`() {
        val expected = DopeQuery(
            queryString = "SELECT * ORDER BY `stringField` ASC",
        )
        val underTest = SelectOrderByClause(listOf(someOrderExpression()), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with named parameter in parent`() {
        val parameterValue = "asdf"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName ORDER BY `stringField` ASC",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectOrderByClause(listOf(someOrderExpression()), someSelectClause(parameterValue.asParameter(parameterName)))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with positional parameter in parent`() {
        val parameterValue = "asdf"
        val expected = DopeQuery(
            queryString = "SELECT $1 ORDER BY `stringField` ASC",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectOrderByClause(listOf(someOrderExpression()), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with type ASC`() {
        val expected = DopeQuery(
            queryString = "SELECT * ORDER BY `stringField` ASC",
        )
        val underTest = SelectOrderByClause(listOf(OrderExpression(someStringField(), ASC)), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with named parameter in parent with type ASC`() {
        val parameterValue = "asdf"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName ORDER BY `stringField` ASC",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectOrderByClause(
            listOf(OrderExpression(someStringField(), ASC)),
            someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with positional parameter in parent with type ASC`() {
        val parameterValue = "asdf"
        val expected = DopeQuery(
            queryString = "SELECT $1 ORDER BY `stringField` ASC",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectOrderByClause(listOf(OrderExpression(someStringField(), ASC)), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with type DESC`() {
        val expected = DopeQuery(
            queryString = "SELECT * ORDER BY `stringField` DESC",
        )
        val underTest = SelectOrderByClause(listOf(OrderExpression(someStringField(), DESC)), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with named parameter in parent with type DESC`() {
        val parameterValue = "asdf"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName ORDER BY `stringField` DESC",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectOrderByClause(
            listOf(OrderExpression(someStringField(), DESC)),
            someSelectClause(parameterValue.asParameter(parameterName)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with positional parameter in parent with type DESC`() {
        val parameterValue = "asdf"
        val expected = DopeQuery(
            queryString = "SELECT $1 ORDER BY `stringField` DESC",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectOrderByClause(listOf(OrderExpression(someStringField(), DESC)), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple order by`() {
        val expected = DopeQuery(
            queryString = "SELECT * ORDER BY `stringField` DESC, `numberField`, `booleanField` ASC",
        )
        val underTest = SelectOrderByClause(
            listOf(
                OrderExpression(someStringField(), DESC),
                OrderExpression(someNumberField()),
                OrderExpression(someBooleanField(), ASC),
            ),
            someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by function`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val expected = SelectOrderByClause(listOf(OrderExpression(stringField)), parentClause)

        val actual = parentClause.orderBy(stringField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support order by function with type`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val orderType = ASC
        val expected = SelectOrderByClause(listOf(OrderExpression(stringField, orderType)), parentClause)

        val actual = parentClause.orderBy(stringField, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support order by function with then order by`() {
        val stringField = someStringField()
        val numberField = someNumberField()
        val parentClause = someSelectClause()
        val orderType = ASC
        val orderType2 = DESC
        val expected = SelectOrderByClause(
            listOf(OrderExpression(stringField, orderType), OrderExpression(numberField, orderType2)),
            parentClause,
        )

        val actual = parentClause.orderBy(stringField, orderType).thenOrderBy(numberField, orderType2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
