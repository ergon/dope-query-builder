package ch.ergon.dope.resolvable.expression.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectPutExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object put expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_PUT(`objectField`, \"key\", \"value\")",
        )
        val underTest = ObjectPutExpression(someObjectField(), "key".toDopeType(), "value".toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object put function`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectPutExpression(objectExpression, newAttributeKey, newAttributeValue)

        val actual = objectExpression.putAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object put function string`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key"
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectPutExpression(objectExpression, newAttributeKey.toDopeType(), newAttributeValue)

        val actual = objectExpression.putAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
