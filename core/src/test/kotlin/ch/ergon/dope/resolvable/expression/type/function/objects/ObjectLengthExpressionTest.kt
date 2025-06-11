package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectLengthExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object length expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_LENGTH(`objectField`)",
        )
        val underTest = ObjectLengthExpression(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object length function`() {
        val objectExpression = someObjectField()
        val expected = ObjectLengthExpression(objectExpression)

        val actual = objectExpression.getLength()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
