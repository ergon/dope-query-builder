package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class IsBooleanExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager<CouchbaseDopeQuery>

    @Test
    fun `should support is boolean expression with no parameters`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ISBOOLEAN(`booleanField`)",
        )
        val underTest = IsBooleanExpression(someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is boolean expression with positional parameter`() {
        val parameterValue = someBoolean()
        val expected = CouchbaseDopeQuery(
            queryString = "ISBOOLEAN($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IsBooleanExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is boolean expression with named parameter`() {
        val parameterValue = someBoolean()
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ISBOOLEAN(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IsBooleanExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is boolean extension`() {
        val boolean = someBooleanExpression()
        val expected = IsBooleanExpression(boolean)

        val actual = boolean.isBoolean()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
