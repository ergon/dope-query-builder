package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support select`() {
        val expected = DopeQuery(
            queryString = "SELECT `stringField`",
        )
        val underTest = SelectClause(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with bucket`() {
        val expected = DopeQuery(
            queryString = "SELECT `someBucket`",
        )
        val underTest = SelectClause(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with aliased bucket`() {
        val expected = DopeQuery(
            queryString = "SELECT `alias`",
        )
        val underTest = SelectClause(someBucket().alias("alias"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with positional parameter`() {
        val parameterValue = "value"
        val expected = DopeQuery(
            queryString = "SELECT $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectClause(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with named parameter`() {
        val parameterValue = "value"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectClause(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with multiple fields`() {
        val expected = DopeQuery(
            queryString = "SELECT `numberField`, `stringArrayField`",
        )
        val underTest = SelectClause(someNumberField(), someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct`() {
        val expected = DopeQuery(
            queryString = "SELECT DISTINCT `numberField`",
        )
        val underTest = SelectDistinctClause(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with bucket`() {
        val expected = DopeQuery(
            queryString = "SELECT DISTINCT `someBucket`",
        )
        val underTest = SelectDistinctClause(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with aliased bucket`() {
        val expected = DopeQuery(
            queryString = "SELECT DISTINCT `alias`",
        )
        val underTest = SelectDistinctClause(someBucket().alias("alias"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with positional parameter`() {
        val parameterValue = "value"
        val expected = DopeQuery(
            queryString = "SELECT DISTINCT $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectDistinctClause(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with named parameter`() {
        val parameterValue = "value"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT DISTINCT \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectDistinctClause(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with multiple fields`() {
        val expected = DopeQuery(
            queryString = "SELECT DISTINCT `numberField`, `stringArrayField`",
        )
        val underTest = SelectDistinctClause(someNumberField(), someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with alias`() {
        val expected = DopeQuery(
            queryString = "SELECT `stringField` AS `stringFieldAlias`",
        )
        val underTest = SelectClause(someStringField().alias("stringFieldAlias"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with mixed aliases`() {
        val expected = DopeQuery(
            queryString = "SELECT `numberField` AS `numberFieldAlias`, `stringArrayField`",
        )
        val underTest = SelectClause(someNumberField().alias("numberFieldAlias"), someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression`() {
        val expected = DopeQuery(
            queryString = "SELECT RAW `stringField`",
        )
        val underTest = SelectRawClause(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression with positional parameter`() {
        val parameterValue = "value"
        val expected = DopeQuery(
            queryString = "SELECT RAW $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SelectRawClause(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression with named parameter`() {
        val parameterValue = "value"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT RAW \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SelectRawClause(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression with alias`() {
        val expected = DopeQuery(
            queryString = "SELECT RAW `stringField` AS `stringFieldAlias`",
        )
        val underTest = SelectRawClause(someStringField().alias("stringFieldAlias"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
