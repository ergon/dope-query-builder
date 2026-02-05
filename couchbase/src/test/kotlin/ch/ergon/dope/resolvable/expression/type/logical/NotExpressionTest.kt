package ch.ergon.dope.resolvable.expression.type.logical

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.logic.AndExpression
import ch.ergon.dope.resolvable.expression.type.logic.NotExpression
import ch.ergon.dope.resolvable.expression.type.logic.not
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support and with positional parameter`() {
        val parameterValue = true
        val expected = CouchbaseDopeQuery(
            queryString = "($1 AND `booleanField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = AndExpression(parameterValue.asParameter(), someBooleanField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with all positional parameters`() {
        val parameterValue = true
        val parameterValue2 = true
        val expected = CouchbaseDopeQuery(
            queryString = "($1 AND $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = AndExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with second positional parameter`() {
        val parameterValue = false
        val expected = CouchbaseDopeQuery(
            queryString = "(`booleanField` AND $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = AndExpression(someBooleanField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "($$parameterName AND `booleanField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = AndExpression(parameterValue.asParameter(parameterName), someBooleanField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with all named parameters`() {
        val parameterValue = true
        val parameterValue2 = false
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = CouchbaseDopeQuery(
            queryString = "($$parameterName AND $$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = AndExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not function`() {
        val field = someBooleanField()
        val expected = NotExpression(field)

        val actual = not(field)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support not function boolean`() {
        val boolean = someBoolean()
        val expected = NotExpression(boolean.toDopeType())

        val actual = not(boolean)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
