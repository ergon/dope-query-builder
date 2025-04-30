package ch.ergon.dope.resolvable.expression.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectPairsExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object pairs expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_PAIRS(`objectField`)",
        )
        val underTest = ObjectPairsExpression(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object pairs function`() {
        val objectExpression = someObjectField()
        val expected = ObjectPairsExpression(objectExpression)

        val actual = objectExpression.getPairs()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
