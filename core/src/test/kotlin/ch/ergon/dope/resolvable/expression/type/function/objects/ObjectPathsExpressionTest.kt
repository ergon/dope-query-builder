package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectPathsExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object paths expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_PATHS(`objectField`)",
        )
        val underTest = ObjectPathsExpression(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object paths expression with options`() {
        val expected = DopeQuery(
            queryString = "OBJECT_PATHS(`objectField`, `options`)",
        )
        val underTest = ObjectPathsExpression(someObjectField(), someObjectField("options"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object paths function`() {
        val objectExpression = someObjectField()
        val expected = ObjectPathsExpression(objectExpression)

        val actual = objectExpression.getPaths()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object paths function with options`() {
        val objectExpression = someObjectField()
        val options = someObjectField("options")
        val expected = ObjectPathsExpression(objectExpression, options)

        val actual = objectExpression.getPaths(options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
