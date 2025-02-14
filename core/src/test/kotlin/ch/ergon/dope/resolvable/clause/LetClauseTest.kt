package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.DopeVariable
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.assignTo
import ch.ergon.dope.resolvable.expression.type.function.string.lower
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LetClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support let`() {
        val expected = DopeQuery(
            queryString = "SELECT * FROM `someBucket` LET `someName` = `numberField`",
        )
        val underTest = LetClause(DopeVariable("someName", someNumberField()), parentClause = someFromClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support let with multiple expressions`() {
        val expected = DopeQuery(
            queryString = "SELECT * FROM `someBucket` LET `name1` = `numberField`, `name2` = `stringField`",
        )
        val underTest = LetClause(
            DopeVariable("name1", someNumberField()),
            DopeVariable("name2", someStringField()),
            parentClause = someFromClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support let function`() {
        val parentClause = someFromClause()
        val dopeVariable = DopeVariable("name1", someNumberField())
        val expected = LetClause(dopeVariable, parentClause = parentClause)

        val actual = parentClause.withVariables(dopeVariable)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support let expression`() {
        val expression = lower("TEST")
        val expected = DopeQuery("`name1`")
        val underTest = DopeVariable("name1", expression)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support let expression function`() {
        val expression = lower("test")
        val name = "name"
        val expected = DopeVariable(name, expression)

        val actual = name.assignTo(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support let expression function number`() {
        val expression = someNumber()
        val name = "name"
        val expected = DopeVariable(name, expression.toDopeType())

        val actual = name.assignTo(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support let expression function string`() {
        val expression = someString()
        val name = "name"
        val expected = DopeVariable(name, expression.toDopeType())

        val actual = name.assignTo(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support let expression function boolean`() {
        val expression = someBoolean()
        val name = "name"
        val expected = DopeVariable(name, expression.toDopeType())

        val actual = name.assignTo(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
