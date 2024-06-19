package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SelectClauseTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support select`() {
        val expected = DopeQuery(
            "SELECT `stringField`",
            emptyMap(),
        )

        val actual = SelectClause(someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with positional parameter`() {
        val parameterValue = "value"
        val expected = DopeQuery(
            "SELECT $1",
            mapOf("$1" to parameterValue),
        )

        val actual = SelectClause(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with multiple fields`() {
        val expected = DopeQuery(
            "SELECT `numberField`, `stringArrayField`",
            emptyMap(),
        )

        val actual = SelectClause(someNumberField(), someStringArrayField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct`() {
        val expected = DopeQuery(
            "SELECT DISTINCT `numberField`",
            emptyMap(),
        )

        val actual = SelectDistinctClause(someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with positional parameter`() {
        val parameterValue = "value"
        val expected = DopeQuery(
            "SELECT DISTINCT $1",
            mapOf("$1" to parameterValue),
        )

        val actual = SelectDistinctClause(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select distinct with multiple fields`() {
        val expected = DopeQuery(
            "SELECT DISTINCT `numberField`, `stringArrayField`",
            emptyMap(),
        )

        val actual = SelectDistinctClause(someNumberField(), someStringArrayField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with alias`() {
        val expected = DopeQuery(
            "SELECT `stringField` AS `stringFieldAlias`",
            emptyMap(),
        )

        val actual = SelectClause(someStringField().alias("stringFieldAlias")).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with mixed aliases`() {
        val expected = DopeQuery(
            "SELECT `numberField` AS `numberFieldAlias`, `stringArrayField`",
            emptyMap(),
        )

        val actual = SelectClause(someNumberField().alias("numberFieldAlias"), someStringArrayField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression`() {
        val expected = DopeQuery(
            "SELECT RAW `stringField`",
            emptyMap(),
        )

        val actual = SelectRawClause(someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression with positional parameter`() {
        val parameterValue = "value"
        val expected = DopeQuery(
            "SELECT RAW $1",
            mapOf("$1" to parameterValue),
        )

        val actual = SelectRawClause(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with raw expression with alias`() {
        val expected = DopeQuery(
            "SELECT RAW `stringField` AS `stringFieldAlias`",
            emptyMap(),
        )

        val actual = SelectRawClause(someStringField().alias("stringFieldAlias")).toDopeQuery()

        assertEquals(expected, actual)
    }
}
