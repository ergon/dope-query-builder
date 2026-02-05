package ch.ergon.dope.extensions.expression.type.relational

import ch.ergon.dope.extension.expression.type.relational.notWithinArray
import ch.ergon.dope.extension.expression.type.relational.withinArray
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.resolvable.expression.type.collection.NotWithinExpression
import ch.ergon.dope.resolvable.expression.type.collection.WithinExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class WithinTest {
    @Test
    fun `should support within array with CMJsonField and CMJsonList`() {
        val field = someCMNumberField()
        val list = someCMNumberList()
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and CMJsonList`() {
        val field = someCMNumberField()
        val list = someCMNumberList()
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }
}
