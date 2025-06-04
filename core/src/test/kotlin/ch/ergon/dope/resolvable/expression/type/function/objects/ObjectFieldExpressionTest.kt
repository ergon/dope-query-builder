package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectFieldExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object field expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_FIELD(`objectField`, \"key\")",
        )
        val underTest = ObjectFieldExpression(someObjectField(), "key".toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object field function`() {
        val objectExpression = someObjectField()
        val attributeKey = "key".toDopeType()
        val expected = ObjectFieldExpression(objectExpression, attributeKey)

        val actual = objectExpression.getField(attributeKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object field function string`() {
        val objectExpression = someObjectField()
        val attributeKey = "key"
        val expected = ObjectFieldExpression(objectExpression, attributeKey.toDopeType())

        val actual = objectExpression.getField(attributeKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
