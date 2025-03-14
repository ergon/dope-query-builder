package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.resolvable.clause.model.With
import ch.ergon.dope.resolvable.clause.model.WithClause
import ch.ergon.dope.resolvable.clause.model.asCTE
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class WithClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support with clause`() {
        val expected = DopeQuery(
            queryString = "WITH `alias` AS (\"someString\")",
        )
        val underTest = WithClause(With("alias", someString().toDopeType()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support with clause multiple CTEs`() {
        val expected = DopeQuery(
            queryString = "WITH `alias` AS (\"someString\"), `subquery` AS ((SELECT *))",
        )
        val underTest = WithClause(
            With("alias", someString().toDopeType()),
            With("subquery", someSelectClause().asExpression()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support with expression`() {
        val expression = someString().toDopeType()
        val alias = "alias"
        val expected = With(alias, expression)

        val actual = alias.asCTE(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support with expression with string`() {
        val expression = someString()
        val alias = "alias"
        val expected = With(alias, expression.toDopeType())

        val actual = alias.asCTE(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support with expression with number`() {
        val expression = someNumber()
        val alias = "alias"
        val expected = With(alias, expression.toDopeType())

        val actual = alias.asCTE(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support with expression with boolean`() {
        val expression = someBoolean()
        val alias = "alias"
        val expected = With(alias, expression.toDopeType())

        val actual = alias.asCTE(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support with expression with subquery`() {
        val subquery = someSelectClause()
        val alias = "alias"
        val expected = With(alias, subquery.asExpression())

        val actual = alias.asCTE(subquery)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
