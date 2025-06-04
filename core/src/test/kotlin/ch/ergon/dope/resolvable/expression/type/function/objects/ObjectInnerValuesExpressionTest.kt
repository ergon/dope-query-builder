package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectInnerValuesExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object inner values expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_INNER_VALUES(`objectField`)",
        )
        val underTest = ObjectInnerValuesExpression(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object inner values function`() {
        val objectExpression = someObjectField()
        val expected = ObjectInnerValuesExpression(objectExpression)

        val actual = objectExpression.getInnerValues()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
