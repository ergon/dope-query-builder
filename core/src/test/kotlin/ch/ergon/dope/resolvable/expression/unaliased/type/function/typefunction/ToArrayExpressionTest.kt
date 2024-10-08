package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToArrayExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support to array expression`() {
        val expected = DopeQuery(
            "TOARRAY(`stringField`)",
            emptyMap(),
        )
        val underTest = ToArrayExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to array expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "TOARRAY($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ToArrayExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to array extension`() {
        val string = someString().toDopeType()
        val expected = ToArrayExpression(string)

        val actual = string.toArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
