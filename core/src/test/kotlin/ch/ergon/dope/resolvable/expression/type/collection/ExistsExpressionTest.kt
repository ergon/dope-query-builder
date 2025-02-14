package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ExistsExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support EXISTS expression`() {
        val expected = DopeQuery(
            queryString = "EXISTS `numberArrayField`",
        )
        val underTest = ExistsExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support EXISTS expression with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "EXISTS \$param",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ExistsExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support EXISTS expression with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "EXISTS $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ExistsExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support EXISTS extension`() {
        val array = someNumberArrayField()
        val expected = ExistsExpression(array)

        val actual = exists(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support EXISTS extension collection`() {
        val array = listOf(someNumberField(), someNumberField())
        val expected = ExistsExpression(array.toDopeType())

        val actual = exists(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support EXISTS extension select`() {
        val selectClause = someSelectRawClause()
        val expected = ExistsExpression(selectClause.asExpression())

        val actual = exists(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
