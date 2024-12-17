package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectInnerPairsExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object inner pairs expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_INNER_PAIRS(`objectField`)",
        )
        val underTest = ObjectInnerPairsExpression(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object inner pairs function`() {
        val objectExpression = someObjectField()
        val expected = ObjectInnerPairsExpression(objectExpression)

        val actual = objectExpression.innerPairs()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
