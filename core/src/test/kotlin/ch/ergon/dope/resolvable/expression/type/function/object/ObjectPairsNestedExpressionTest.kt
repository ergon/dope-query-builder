package ch.ergon.dope.resolvable.expression.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectPairsNestedExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object pairs nested expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_PAIRS_NESTED(`objectField`)",
        )
        val underTest = ObjectPairsNestedExpression(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object pairs nested expression with options`() {
        val expected = DopeQuery(
            queryString = "OBJECT_PAIRS_NESTED(`objectField`, `options`)",
        )
        val underTest = ObjectPairsNestedExpression(someObjectField(), someObjectField("options"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object pairs nested function`() {
        val objectExpression = someObjectField()
        val expected = ObjectPairsNestedExpression(objectExpression)

        val actual = objectExpression.pairsNested()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object pairs nested function with options`() {
        val objectExpression = someObjectField()
        val options = someObjectField("options")
        val expected = ObjectPairsNestedExpression(objectExpression, options)

        val actual = objectExpression.pairsNested(options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
