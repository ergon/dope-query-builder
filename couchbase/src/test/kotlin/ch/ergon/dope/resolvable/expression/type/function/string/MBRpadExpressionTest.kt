package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MBRpadExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support mbRpad`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MB_RPAD(`stringField`, `numberField`)",
        )
        val underTest = MBRpadExpression(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "MB_RPAD($1, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = MBRpadExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val expected = CouchbaseDopeQuery(
            queryString = "MB_RPAD($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = MBRpadExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with extra `() {
        val expected = CouchbaseDopeQuery(
            queryString = "MB_RPAD(`stringField`, `numberField`, `stringField`)",
        )
        val underTest = MBRpadExpression(someStringField(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with extra and with parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "MB_RPAD($1, `numberField`, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = MBRpadExpression(parameterValue.asParameter(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with extra and with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val parameterValue3 = "extra"
        val expected = CouchbaseDopeQuery(
            queryString = "MB_RPAD($1, $2, $3)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2, parameterValue3)),
        )
        val underTest = MBRpadExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad function type type type`() {
        val inStr = someStringField("inStr")
        val size = someNumberField("size")
        val prefix = someStringField("prefix")
        val expected = MBRpadExpression(inStr, size, prefix)

        val actual = inStr.mbRpad(size, prefix)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function type type string`() {
        val inStr = someStringField("inStr")
        val size = someNumberField("size")
        val prefix = someString("prefix")
        val expected = MBRpadExpression(inStr, size, prefix.toDopeType())

        val actual = inStr.mbRpad(size, prefix)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function type number type`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = MBRpadExpression(inStr, size.toDopeType(), prefix)

        val actual = inStr.mbRpad(size, prefix)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function string type type`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = someStringField("prefix")
        val expected = MBRpadExpression(inStr.toDopeType(), size, prefix)

        val actual = inStr.mbRpad(size, prefix)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function type number`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = null
        val expected = MBRpadExpression(inStr, size.toDopeType(), prefix)

        val actual = inStr.mbRpad(size)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function type number string`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = MBRpadExpression(inStr, size.toDopeType(), prefix.toDopeType())

        val actual = inStr.mbRpad(size, prefix)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function string type`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = null
        val expected = MBRpadExpression(inStr.toDopeType(), size, prefix)

        val actual = inStr.mbRpad(size)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function string type string`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = someString("prefix")
        val expected = MBRpadExpression(inStr.toDopeType(), size, prefix.toDopeType())

        val actual = inStr.mbRpad(size, prefix)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function string number type`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = MBRpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = inStr.mbRpad(size, prefix)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function string number`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = null
        val expected = MBRpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = inStr.mbRpad(size)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support mbRpad function string number string`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = MBRpadExpression(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

        val actual = inStr.mbRpad(size, prefix)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
