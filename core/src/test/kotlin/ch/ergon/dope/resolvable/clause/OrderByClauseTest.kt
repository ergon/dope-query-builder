package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByTypeClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class OrderByClauseTest : ParameterDependentTest {
    @Test
    fun `should support order by`() {
        val expected = DopeQuery(
            "SELECT * ORDER BY `stringField`",
            emptyMap(),
        )
        val underTest = SelectOrderByClause(someStringField(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with parameter in parent`() {
        val parameterValue = "asdf"
        val expected = DopeQuery(
            "SELECT $1 ORDER BY `stringField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = SelectOrderByClause(someStringField(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with type ASC`() {
        val expected = DopeQuery(
            "SELECT * ORDER BY `stringField` ASC",
            emptyMap(),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.ASC, someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with parameter in parent with type ASC`() {
        val parameterValue = "asdf"
        val expected = DopeQuery(
            "SELECT $1 ORDER BY `stringField` ASC",
            mapOf("$1" to parameterValue),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.ASC, someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with type DESC`() {
        val expected = DopeQuery(
            "SELECT * ORDER BY `stringField` DESC",
            emptyMap(),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.DESC, someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by with parameter in parent with type DESC`() {
        val parameterValue = "asdf"
        val expected = DopeQuery(
            "SELECT $1 ORDER BY `stringField` DESC",
            mapOf("$1" to parameterValue),
        )
        val underTest = SelectOrderByTypeClause(someStringField(), OrderByType.DESC, someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support order by function`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val expected = SelectOrderByClause(stringField, parentClause)

        val actual = parentClause.orderBy(stringField)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support order by function with type`() {
        val stringField = someStringField()
        val parentClause = someSelectClause()
        val orderType = OrderByType.ASC
        val expected = SelectOrderByTypeClause(stringField, orderType, parentClause)

        val actual = parentClause.orderBy(stringField, orderType)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
