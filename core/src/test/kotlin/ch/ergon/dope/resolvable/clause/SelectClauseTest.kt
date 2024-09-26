package ch.ergon.dope.resolvable.clause

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
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support select`() {
        val expected = DopeQuery(
            "SELECT `stringField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectClause(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with bucket`() {
        val expected = DopeQuery(
            "SELECT `someBucket`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectClause(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with aliased bucket`() {
        val expected = DopeQuery(
            "SELECT `alias`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectClause(someBucket().alias("alias"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with positional parameter`() {
        val parameterValue = "value"
        val expected = DopeQuery(
            "SELECT $1",
            emptyMap(),
            listOf(parameterValue),
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
            "SELECT \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = SelectClause(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with multiple fields`() {
        val expected = DopeQuery(
            "SELECT `numberField`, `stringArrayField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectClause(someNumberField(), someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct`() {
        val expected = DopeQuery(
            "SELECT DISTINCT `numberField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectDistinctClause(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with bucket`() {
        val expected = DopeQuery(
            "SELECT DISTINCT `someBucket`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectDistinctClause(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with aliased bucket`() {
        val expected = DopeQuery(
            "SELECT DISTINCT `alias`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectDistinctClause(someBucket().alias("alias"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with positional parameter`() {
        val parameterValue = "value"
        val expected = DopeQuery(
            "SELECT DISTINCT $1",
            emptyMap(),
            listOf(parameterValue),
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
            "SELECT DISTINCT \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = SelectDistinctClause(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with multiple fields`() {
        val expected = DopeQuery(
            "SELECT DISTINCT `numberField`, `stringArrayField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectDistinctClause(someNumberField(), someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with alias`() {
        val expected = DopeQuery(
            "SELECT `stringField` AS `stringFieldAlias`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectClause(someStringField().alias("stringFieldAlias"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with mixed aliases`() {
        val expected = DopeQuery(
            "SELECT `numberField` AS `numberFieldAlias`, `stringArrayField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectClause(someNumberField().alias("numberFieldAlias"), someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression`() {
        val expected = DopeQuery(
            "SELECT RAW `stringField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectRawClause(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression with positional parameter`() {
        val parameterValue = "value"
        val expected = DopeQuery(
            "SELECT RAW $1",
            emptyMap(),
            listOf(parameterValue),
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
            "SELECT RAW \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = SelectRawClause(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression with alias`() {
        val expected = DopeQuery(
            "SELECT RAW `stringField` AS `stringFieldAlias`",
            emptyMap(),
            emptyList(),
        )
        val underTest = SelectRawClause(someStringField().alias("stringFieldAlias"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
