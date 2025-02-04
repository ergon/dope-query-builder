package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectNamesExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object names expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_NAMES(`objectField`)",
        )
        val underTest = ObjectNamesExpression(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object names function`() {
        val objectExpression = someObjectField()
        val expected = ObjectNamesExpression(objectExpression)

        val actual = objectExpression.names()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
