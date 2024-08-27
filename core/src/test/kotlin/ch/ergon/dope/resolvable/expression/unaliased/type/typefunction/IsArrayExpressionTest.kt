package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.IsArrayExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isArray
import kotlin.test.Test
import kotlin.test.assertEquals

class IsArrayExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is array expression`() {
        val expected = DopeQuery(
            "ISARRAY(`stringArrayField`)",
            emptyMap(),
        )
        val underTest = IsArrayExpression(someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is array expression with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ISARRAY($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IsArrayExpression(parameterValue.asParameter())

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
