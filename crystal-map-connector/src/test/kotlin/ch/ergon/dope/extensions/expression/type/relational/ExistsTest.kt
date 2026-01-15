package ch.ergon.dope.extensions.expression.type.relational

import ch.ergon.dope.extension.expression.type.relational.exists
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.type.collection.ExistsExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ExistsTest {
    @Test
    fun `should support exists with CMJsonList number`() {
        val list = someCMNumberList()
        val expected = ExistsExpression(list.toDopeType())

        val actual = exists(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support exists with CMJsonList string`() {
        val list = someCMStringList()
        val expected = ExistsExpression(list.toDopeType())

        val actual = exists(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support exists with CMJsonList boolean`() {
        val list = someCMBooleanList()
        val expected = ExistsExpression(list.toDopeType())

        val actual = exists(list)

        assertEquals(expected, actual)
    }
}
