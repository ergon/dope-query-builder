package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectConcatExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object concat expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_CONCAT(`field1`, `field2`, `field3`)",
        )
        val underTest = ObjectConcatExpression(someObjectField("field1"), someObjectField("field2"), someObjectField("field3"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object concat function`() {
        val firstObjectExpression = someObjectField("field1")
        val secondObjectExpression = someObjectField("field2")
        val additionalObjectExpression = someObjectField("field3")
        val expected = ObjectConcatExpression(firstObjectExpression, secondObjectExpression, additionalObjectExpression)

        val actual = firstObjectExpression.concat(secondObjectExpression, additionalObjectExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
