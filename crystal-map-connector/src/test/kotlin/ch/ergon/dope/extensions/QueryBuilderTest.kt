package ch.ergon.dope.extensions

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.extension.select
import ch.ergon.dope.extension.selectDistinct
import ch.ergon.dope.extension.selectRaw
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.toDopeType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class QueryBuilderTest {
    private lateinit var manager: DopeQueryManager

    @BeforeTest
    fun setup() {
        manager = DopeQueryManager()
    }

    @Test
    fun `should support select with CM`() {
        val queryBuilder = QueryBuilder()
        val expression = someCMNumberField()
        val expected = SelectClause(expression.toDopeType())

        val actual = queryBuilder.select(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select with multiple CM`() {
        val queryBuilder = QueryBuilder()
        val expression = someCMNumberField()
        val expression2 = someCMStringList()
        val expected = SelectClause(expression.toDopeType(), expression2.toDopeType())

        val actual = queryBuilder.select(expression, expression2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select distinct with CM`() {
        val queryBuilder = QueryBuilder()
        val expression = someCMNumberField()
        val expected = SelectDistinctClause(expression.toDopeType())

        val actual = queryBuilder.selectDistinct(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select distinct with multiple CM`() {
        val queryBuilder = QueryBuilder()
        val expression = someCMNumberField()
        val expression2 = someCMStringList()
        val expected = SelectDistinctClause(expression.toDopeType(), expression2.toDopeType())

        val actual = queryBuilder.selectDistinct(expression, expression2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select raw with CM`() {
        val queryBuilder = QueryBuilder()
        val expression = someCMNumberField()
        val expected = SelectRawClause(expression.toDopeType())

        val actual = queryBuilder.selectRaw(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
