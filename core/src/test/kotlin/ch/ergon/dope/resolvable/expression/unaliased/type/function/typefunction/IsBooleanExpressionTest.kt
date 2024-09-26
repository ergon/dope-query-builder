package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class IsBooleanExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is boolean expression with no parameters`() {
        val expected = DopeQuery(
            "ISBOOLEAN(`booleanField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = IsBooleanExpression(someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is boolean expression with positional parameter`() {
        val parameterValue = someBoolean()
        val expected = DopeQuery(
            "ISBOOLEAN($1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = IsBooleanExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is boolean expression with named parameter`() {
        val parameterValue = someBoolean()
        val parameterName = "param"
        val expected = DopeQuery(
            "ISBOOLEAN(\$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
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
