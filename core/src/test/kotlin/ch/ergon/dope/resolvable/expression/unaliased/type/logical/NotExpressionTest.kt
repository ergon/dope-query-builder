package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support not`() {
        val expected = DopeQuery(
            "NOT `booleanField`",
            emptyMap(),
            manager,
        )
        val underTest = NotExpression(someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "NOT $1",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = NotExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not function`() {
        val field = someBooleanField()
        val expected = NotExpression(field)

        val actual = not(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not function boolean`() {
        val boolean = someBoolean()
        val expected = NotExpression(boolean.toDopeType())

        val actual = not(boolean)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
