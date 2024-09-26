package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByTypeClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderByClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support order by`() {
        val expected = DopeQuery(
            "SELECT * ORDER BY `stringField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectOrderByClause(someStringField(), someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with named parameter in parent`() {
        val parameterValue = "asdf"
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT \$$parameterName ORDER BY `stringField`",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = SelectOrderByClause(someStringField(), someSelectClause(parameterValue.asParameter(parameterName)))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with positional parameter in parent`() {
        val parameterValue = "asdf"
        val expected = DopeQuery(
            "SELECT $1 ORDER BY `stringField`",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = SelectOrderByClause(someStringField(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with type ASC`() {
        val expected = DopeQuery(
            "SELECT * ORDER BY `stringField` ASC",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.ASC, someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with named parameter in parent with type ASC`() {
        val parameterValue = "asdf"
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT \$$parameterName ORDER BY `stringField` ASC",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.ASC, someSelectClause(parameterValue.asParameter(parameterName)))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with positional parameter in parent with type ASC`() {
        val parameterValue = "asdf"
        val expected = DopeQuery(
            "SELECT $1 ORDER BY `stringField` ASC",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.ASC, someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with type DESC`() {
        val expected = DopeQuery(
            "SELECT * ORDER BY `stringField` DESC",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.DESC, someSelectClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with named parameter in parent with type DESC`() {
        val parameterValue = "asdf"
        val parameterName = "param"
        val expected = DopeQuery(
            "SELECT \$$parameterName ORDER BY `stringField` DESC",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.DESC, someSelectClause(parameterValue.asParameter(parameterName)))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with positional parameter in parent with type DESC`() {
        val parameterValue = "asdf"
        val expected = DopeQuery(
            "SELECT $1 ORDER BY `stringField` DESC",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.DESC, someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by function`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val expected = SelectOrderByClause(stringField, parentClause)

        val actual = parentClause.orderBy(stringField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support order by function with type`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val orderType = OrderByType.ASC
        val expected = SelectOrderByTypeClause(stringField, orderType, parentClause)

        val actual = parentClause.orderBy(stringField, orderType)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
