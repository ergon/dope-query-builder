package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class Substring1ExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support sub string 1`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SUBSTR1(`stringField`, 3, 1)",
        )
        val underTest = Substring1Expression(someStringField(), 3.toDopeType(), 1.toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub string 1 with parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "SUBSTR1($1, 3, 1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = Substring1Expression(parameterValue.asParameter(), 3.toDopeType(), 1.toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support substring1 function type type type`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val length = someNumberField()
        val expected = Substring1Expression(inStr, startPos, length)

        val actual = inStr.substring1(startPos, length)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support substring1 function type type`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val expected = Substring1Expression(inStr, startPos)

        val actual = inStr.substring1(startPos)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support substring1 function type type int`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val length = 1
        val expected = Substring1Expression(inStr, startPos, length.toDopeType())

        val actual = inStr.substring1(startPos, length)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support substring1 function type int int`() {
        val inStr = someStringField("inStr")
        val startPos = 1
        val length = 2
        val expected = Substring1Expression(inStr, startPos.toDopeType(), length.toDopeType())

        val actual = inStr.substring1(startPos, length)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support substring1 function type int`() {
        val inStr = someStringField("inStr")
        val startPos = 1
        val expected = Substring1Expression(inStr, startPos.toDopeType())

        val actual = inStr.substring1(startPos)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support substring1 function string type type`() {
        val inStr = someString("inStr")
        val startPos = someNumberField()
        val length = someNumberField()
        val expected = Substring1Expression(inStr.toDopeType(), startPos, length)

        val actual = inStr.substring1(startPos, length)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support substring1 function string type int`() {
        val inStr = someString("inStr")
        val startPos = someNumberField()
        val length = 1
        val expected = Substring1Expression(inStr.toDopeType(), startPos, length.toDopeType())

        val actual = inStr.substring1(startPos, length)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support substring1 function string int int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val length = 2
        val expected = Substring1Expression(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = inStr.substring1(startPos, length)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support substring1 function string int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val expected = Substring1Expression(inStr.toDopeType(), startPos.toDopeType())

        val actual = inStr.substring1(startPos)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
