package ch.ergon.dope.extensions.expression.type

import ch.ergon.dope.extension.expression.type.get
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMObjectList
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.type.ArrayAccess
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayAccessTest {
    @Test
    fun `should support array access with CMJsonList number and int index`() {
        val list = someCMNumberList()
        val expected = ArrayAccess(list.toDopeType(), 1.toDopeType())

        val actual = list.get(1)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with CMJsonList number and type index`() {
        val list = someCMNumberList()
        val index = 1.toDopeType()
        val expected = ArrayAccess(list.toDopeType(), index)

        val actual = list.get(index)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with CMJsonList string and int index`() {
        val list = someCMStringList()
        val expected = ArrayAccess(list.toDopeType(), 2.toDopeType())

        val actual = list.get(2)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with CMJsonList string and type index`() {
        val list = someCMStringList()
        val index = 2.toDopeType()
        val expected = ArrayAccess(list.toDopeType(), index)

        val actual = list.get(index)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with CMJsonList boolean and int index`() {
        val list = someCMBooleanList()
        val expected = ArrayAccess(list.toDopeType(), 3.toDopeType())

        val actual = list.get(3)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with CMJsonList boolean and type index`() {
        val list = someCMBooleanList()
        val index = 3.toDopeType()
        val expected = ArrayAccess(list.toDopeType(), index)

        val actual = list.get(index)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with CMObjectList and int index`() {
        val list = someCMObjectList()
        val expected = ArrayAccess(list.toDopeType(), 4.toDopeType())

        val actual = list.get(4)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with CMObjectList and type index`() {
        val list = someCMObjectList()
        val index = 4.toDopeType()
        val expected = ArrayAccess(list.toDopeType(), index)

        val actual = list.get(index)

        assertEquals(expected, actual)
    }
}
