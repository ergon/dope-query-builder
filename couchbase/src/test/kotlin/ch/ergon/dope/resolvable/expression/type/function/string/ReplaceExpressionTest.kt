package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someInt
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ReplaceExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support replace`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val replace = someStringField("replace")
        val numberOfInstances = null
        val expected = CouchbaseDopeQuery(
            queryString = "REPLACE(`inStr`, `searchStr`, `replace`)",
        )
        val underTest = ReplaceExpression(inStr, searchStr, replace, numberOfInstances)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support replace function type type type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val replace = someStringField("replace")
        val numberOfInstances = someNumberField("numberOfInstances")
        val expected = ReplaceExpression(inStr, searchStr, replace, numberOfInstances)

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function type type type int`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val replace = someStringField("replace")
        val numberOfInstances = someInt()
        val expected = ReplaceExpression(inStr, searchStr, replace, numberOfInstances.toDopeType())

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function type type string type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val replace = someString("replace")
        val numberOfInstances = someNumberField("numberOfInstances")
        val expected = ReplaceExpression(inStr, searchStr, replace.toDopeType(), numberOfInstances)

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function type type string int`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val replace = someString("replace")
        val numberOfInstances = someInt()
        val expected = ReplaceExpression(inStr, searchStr, replace.toDopeType(), numberOfInstances.toDopeType())

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function type string type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val replace = someStringField("replace")
        val numberOfInstances = someNumberField()
        val expected = ReplaceExpression(inStr, searchStr.toDopeType(), replace, numberOfInstances)

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function type string type int`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val replace = someStringField("replace")
        val numberOfInstances = someInt()
        val expected = ReplaceExpression(inStr, searchStr.toDopeType(), replace, numberOfInstances.toDopeType())

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function type string string type`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val replace = someString("replace")
        val numberOfInstances = someNumberField()
        val expected = ReplaceExpression(inStr, searchStr.toDopeType(), replace.toDopeType(), numberOfInstances)

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function type string string int`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val replace = someString("replace")
        val numberOfInstances = someInt()
        val expected = ReplaceExpression(inStr, searchStr.toDopeType(), replace.toDopeType(), numberOfInstances.toDopeType())

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function string type type type`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val replace = someStringField("replace")
        val numberOfInstances = someNumberField("numberOfInstances")
        val expected = ReplaceExpression(inStr.toDopeType(), searchStr, replace, numberOfInstances)

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function string type type int`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val replace = someStringField("replace")
        val numberOfInstances = someInt()
        val expected = ReplaceExpression(inStr.toDopeType(), searchStr, replace, numberOfInstances.toDopeType())

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function string type string type`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val replace = someString("replace")
        val numberOfInstances = someNumberField("numberOfInstances")
        val expected = ReplaceExpression(inStr.toDopeType(), searchStr, replace.toDopeType(), numberOfInstances)

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function string type string int`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val replace = someString("replace")
        val numberOfInstances = someInt()
        val expected = ReplaceExpression(inStr.toDopeType(), searchStr, replace.toDopeType(), numberOfInstances.toDopeType())

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function string string type type`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val replace = someStringField("replace")
        val numberOfInstances = someNumberField()
        val expected = ReplaceExpression(inStr.toDopeType(), searchStr.toDopeType(), replace, numberOfInstances)

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function string string type int`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val replace = someStringField("replace")
        val numberOfInstances = someInt()
        val expected = ReplaceExpression(inStr.toDopeType(), searchStr.toDopeType(), replace, numberOfInstances.toDopeType())

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function string string string type`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val replace = someString("replace")
        val numberOfInstances = someNumberField()
        val expected = ReplaceExpression(inStr.toDopeType(), searchStr.toDopeType(), replace.toDopeType(), numberOfInstances)

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support replace function string string string int`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val replace = someString("replace")
        val numberOfInstances = someInt()
        val expected = ReplaceExpression(inStr.toDopeType(), searchStr.toDopeType(), replace.toDopeType(), numberOfInstances.toDopeType())

        val actual = inStr.replace(searchStr, replace, numberOfInstances)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
