package ch.ergon.dope.resolvable.expression.single.type.function.typefunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.type.IsArrayExpression
import ch.ergon.dope.resolvable.expression.single.type.function.type.isArray
import kotlin.test.Test
import kotlin.test.assertEquals

class IsArrayExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is array expression`() {
        val expected = DopeQuery(
            queryString = "ISARRAY(`stringArrayField`)",
        )
        val underTest = IsArrayExpression(someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is array expression with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ISARRAY($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IsArrayExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is array expression with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ISARRAY(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IsArrayExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is array extension`() {
        val array = someStringArrayField()
        val expected = IsArrayExpression(array)

        val actual = array.isArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
