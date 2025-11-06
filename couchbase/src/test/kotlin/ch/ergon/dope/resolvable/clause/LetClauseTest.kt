package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.resolvable.expression.type.assignTo
import ch.ergon.dope.resolvable.expression.type.function.string.lower
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LetClauseTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support let`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * FROM `someBucket` LET `someName` = `numberField`",
        )
        val underTest = LetClause(DopeVariable("someName", someNumberField()), parentClause = someFromClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support let with multiple expressions`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * FROM `someBucket` LET `name1` = `numberField`, `name2` = `stringField`",
        )
        val underTest = LetClause(
            DopeVariable("name1", someNumberField()),
            listOf(DopeVariable("name2", someStringField())),
            parentClause = someFromClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support let function`() {
        val parentClause = someFromClause()
        val dopeVariable = DopeVariable("name1", someNumberField())
        val expected = LetClause(dopeVariable, parentClause = parentClause)

        val actual = parentClause.withVariables(dopeVariable)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support let expression`() {
        val expression = lower("TEST")
        val expected = CouchbaseDopeQuery("`name1`")
        val underTest = DopeVariable("name1", expression)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support let expression function`() {
        val expression = lower("test")
        val name = "name"
        val expected = DopeVariable(name, expression)

        val actual = name.assignTo(expression)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support let expression function number`() {
        val expression = someNumber()
        val name = "name"
        val expected = DopeVariable(name, expression.toDopeType())

        val actual = name.assignTo(expression)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support let expression function string`() {
        val expression = someString()
        val name = "name"
        val expected = DopeVariable(name, expression.toDopeType())

        val actual = name.assignTo(expression)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support let expression function boolean`() {
        val expression = someBoolean()
        val name = "name"
        val expected = DopeVariable(name, expression.toDopeType())

        val actual = name.assignTo(expression)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
