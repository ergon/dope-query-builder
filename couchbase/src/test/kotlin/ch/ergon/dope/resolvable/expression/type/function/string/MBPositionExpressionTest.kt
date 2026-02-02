package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MBPositionExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support mbPosition`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MB_POSITION(`stringField`, `stringField`)",
        )
        val underTest = MBPositionExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbPosition with parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "MB_POSITION($1, `stringField`)",

            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = MBPositionExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbPosition with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "MB_POSITION($1, $2)",

            DopeParameters(positionalParameters = listOf(parameterValue2, parameterValue2)),
        )
        val underTest = MBPositionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbPosition function type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val expected = MBPositionExpression(inStr, searchStr)

        val actual = inStr.mbPosition(searchStr)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbPosition function type string`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val expected = MBPositionExpression(inStr, searchStr.toDopeType())

        val actual = inStr.mbPosition(searchStr)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbPosition function string type`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val expected = MBPositionExpression(inStr.toDopeType(), searchStr)

        val actual = inStr.mbPosition(searchStr)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbPosition function string string`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val expected = MBPositionExpression(inStr.toDopeType(), searchStr.toDopeType())

        val actual = inStr.mbPosition(searchStr)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
