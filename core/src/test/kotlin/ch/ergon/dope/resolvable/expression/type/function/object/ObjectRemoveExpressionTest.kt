package ch.ergon.dope.resolvable.expression.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectRemoveExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object remove expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_REMOVE(`objectField`, \"key\")",
        )
        val underTest = ObjectRemoveExpression(someObjectField(), "key".toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object remove function`() {
        val objectExpression = someObjectField()
        val attributeKey = "key".toDopeType()
        val expected = ObjectRemoveExpression(objectExpression, attributeKey)

        val actual = objectExpression.removeAttribute(attributeKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object remove function string`() {
        val objectExpression = someObjectField()
        val attributeKey = "key"
        val expected = ObjectRemoveExpression(objectExpression, attributeKey.toDopeType())

        val actual = objectExpression.removeAttribute(attributeKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
