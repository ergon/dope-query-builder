package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectUnwrapExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object unwrap expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_UNWRAP(`objectField`)",
        )
        val underTest = ObjectUnwrapExpression(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object unwrap function`() {
        val objectExpression = someObjectField()
        val expected = ObjectUnwrapExpression(objectExpression)

        val actual = objectExpression.unwrap()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
