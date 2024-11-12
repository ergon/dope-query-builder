package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.LetExpression
import ch.ergon.dope.resolvable.clause.model.assignTo
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.lower
import kotlin.test.Test
import kotlin.test.assertEquals

class LetClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support let`() {
        val expected = DopeQuery(
            queryString = "SELECT * FROM `someBucket` LET `someAlias` = `numberField`",
        )
        val underTest = LetClause(LetExpression("someAlias", someNumberField()), parentClause = someFromClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support let with multiple expressions`() {
        val expected = DopeQuery(
            queryString = "SELECT * FROM `someBucket` LET `alias1` = `numberField`, `alias2` = `stringField`",
        )
        val underTest = LetClause(
            LetExpression("alias1", someNumberField()),
            LetExpression("alias2", someStringField()),
            parentClause = someFromClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support let function`() {
        val parentClause = someFromClause()
        val letExpression = LetExpression("alias1", someNumberField())
        val expected = LetClause(letExpression, parentClause = parentClause)

        val actual = parentClause.let(letExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support let expression`() {
        val expression = lower("TEST")
        val expected = DopeQuery("`alias1`")
        val underTest = LetExpression("alias1", expression)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support let expression function`() {
        val expression = lower("test")
        val alias = "alias"
        val expected = LetExpression(alias, expression)

        val actual = alias.assignTo(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
