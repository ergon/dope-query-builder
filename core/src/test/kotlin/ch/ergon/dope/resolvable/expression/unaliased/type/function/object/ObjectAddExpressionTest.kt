package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectAddExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object add expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_ADD(`objectField`, \"key\", \"value\")",
        )
        val underTest = ObjectAddExpression(someObjectField(), "key".toDopeType(), "value".toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object add function`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectAddExpression(objectExpression, newAttributeKey, newAttributeValue)

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function string`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key"
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectAddExpression(objectExpression, newAttributeKey.toDopeType(), newAttributeValue)

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
