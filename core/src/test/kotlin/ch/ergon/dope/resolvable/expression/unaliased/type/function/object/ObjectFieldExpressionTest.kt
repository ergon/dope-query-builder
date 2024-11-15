package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectFieldExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object add expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_FIELD(`objectField`, \"key\")",
        )
        val underTest = ObjectFieldExpression(someObjectField(), "key".toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object add function`() {
        val objectExpression = someObjectField()
        val attributeKey = "key".toDopeType()
        val expected = ObjectFieldExpression(objectExpression, attributeKey)

        val actual = objectExpression.objectField(attributeKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function string`() {
        val objectExpression = someObjectField()
        val attributeKey = "key"
        val expected = ObjectFieldExpression(objectExpression, attributeKey.toDopeType())

        val actual = objectExpression.objectField(attributeKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
