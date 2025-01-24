package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectValuesExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object values expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_VALUES(`objectField`)",
        )
        val underTest = ObjectValuesExpression(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object values function`() {
        val objectExpression = someObjectField()
        val expected = ObjectValuesExpression(objectExpression)

        val actual = objectExpression.values()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
