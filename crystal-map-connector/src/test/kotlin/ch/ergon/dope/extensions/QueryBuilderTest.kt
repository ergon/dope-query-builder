package ch.ergon.dope.extensions

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.extension.select
import ch.ergon.dope.extension.selectDistinct
import ch.ergon.dope.extension.selectRaw
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class QueryBuilderTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support select with CM`() {
        val queryBuilder = QueryBuilder
        val expression = someCMNumberField()
        val expected = SelectClause(expression.toDopeType())

        val actual = queryBuilder.select(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select with CM expression`() {
        val queryBuilder = QueryBuilder
        val expression = someCMNumberField()
        val expression2 = someNumberField()
        val expected = SelectClause(expression.toDopeType(), expression2)

        val actual = queryBuilder.select(expression, expression2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select with expression CM`() {
        val queryBuilder = QueryBuilder
        val expression = someNumberField()
        val expression2 = someCMNumberField()
        val expected = SelectClause(expression, expression2.toDopeType())

        val actual = queryBuilder.select(expression, expression2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select with multiple CM`() {
        val queryBuilder = QueryBuilder
        val expression = someCMNumberField()
        val expression2 = someCMStringList()
        val expected = SelectClause(expression.toDopeType(), expression2.toDopeType())

        val actual = queryBuilder.select(expression, expression2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select distinct with CM`() {
        val queryBuilder = QueryBuilder
        val expression = someCMNumberField()
        val expected = SelectDistinctClause(expression.toDopeType())

        val actual = queryBuilder.selectDistinct(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select distinct with multiple CM`() {
        val queryBuilder = QueryBuilder
        val expression = someCMNumberField()
        val expression2 = someCMStringList()
        val expected = SelectDistinctClause(expression.toDopeType(), expression2.toDopeType())

        val actual = queryBuilder.selectDistinct(expression, expression2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select distinct with CM expression`() {
        val queryBuilder = QueryBuilder
        val expression = someCMNumberField()
        val expression2 = someNumberField()
        val expected = SelectDistinctClause(expression.toDopeType(), expression2)

        val actual = queryBuilder.selectDistinct(expression, expression2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select distinct with expression CM`() {
        val queryBuilder = QueryBuilder
        val expression = someNumberField()
        val expression2 = someCMNumberField()
        val expected = SelectDistinctClause(expression, expression2.toDopeType())

        val actual = queryBuilder.selectDistinct(expression, expression2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select raw with CM number`() {
        val queryBuilder = QueryBuilder
        val expression = someCMNumberField()
        val expected = SelectRawClause(expression.toDopeType())

        val actual = queryBuilder.selectRaw(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select raw with CM string`() {
        val queryBuilder = QueryBuilder
        val expression = someCMStringField()
        val expected = SelectRawClause(expression.toDopeType())

        val actual = queryBuilder.selectRaw(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select raw with CM boolean`() {
        val queryBuilder = QueryBuilder
        val expression = someCMBooleanField()
        val expected = SelectRawClause(expression.toDopeType())

        val actual = queryBuilder.selectRaw(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select raw with CM number list`() {
        val queryBuilder = QueryBuilder
        val expression = someCMNumberList()
        val expected = SelectRawClause(expression.toDopeType())

        val actual = queryBuilder.selectRaw(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select raw with CM string list`() {
        val queryBuilder = QueryBuilder
        val expression = someCMStringList()
        val expected = SelectRawClause(expression.toDopeType())

        val actual = queryBuilder.selectRaw(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select raw with CM boolean list`() {
        val queryBuilder = QueryBuilder
        val expression = someCMBooleanList()
        val expected = SelectRawClause(expression.toDopeType())

        val actual = queryBuilder.selectRaw(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
