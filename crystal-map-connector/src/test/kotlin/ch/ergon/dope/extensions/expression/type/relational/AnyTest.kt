package ch.ergon.dope.extensions.expression.type.relational

import ch.ergon.dope.extension.expression.type.ObjectField
import ch.ergon.dope.extension.expression.type.getField
import ch.ergon.dope.extension.expression.type.relational.any
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AnyTest {
    class Dummy(path: String = "") : Schema {
        val type: CMJsonField<String> = CMJsonField("type", path)
    }

    @Test
    fun `should support any with CMJsonList number`() {
        val list = someCMNumberList()

        val actual = list.any("i") { it.isEqualTo(1.toDopeType()) }

        assertEquals(list.toDopeType(), actual.arrayExpression)
        assertEquals("i", actual.iteratorName)

        val predicateResult = actual.predicate(Iterator("x"))
        assertNotNull(predicateResult)
        assertEquals(EqualsExpression(Iterator("x"), 1.toDopeType()), predicateResult)
    }

    @Test
    fun `should support any with CMJsonList string`() {
        val list = someCMStringList()

        val actual = list.any("i") { it.isEqualTo("value".toDopeType()) }

        assertEquals(list.toDopeType(), actual.arrayExpression)
        assertEquals("i", actual.iteratorName)

        val predicateResult = actual.predicate(Iterator("x"))
        assertNotNull(predicateResult)
        assertEquals(EqualsExpression(Iterator("x"), "value".toDopeType()), predicateResult)
    }

    @Test
    fun `should support any with CMJsonList boolean`() {
        val list = someCMBooleanList()

        val actual = list.any("i") { it.isEqualTo(true.toDopeType()) }

        assertEquals(list.toDopeType(), actual.arrayExpression)
        assertEquals("i", actual.iteratorName)

        val predicateResult = actual.predicate(Iterator("x"))
        assertNotNull(predicateResult)
        assertEquals(EqualsExpression(Iterator("x"), true.toDopeType()), predicateResult)
    }

    @Test
    fun `should support any with CMObjectList`() {
        val schema = Dummy()
        val list = CMObjectList(schema, "objectList", "")

        val actual = list.any("o") { it.getField(Dummy::type).toDopeType().isEqualTo("test") }

        assertEquals(list.toDopeType(), actual.arrayExpression)
        assertEquals("o", actual.iteratorName)

        val predicateResult = actual.predicate(Iterator("x"))
        assertNotNull(predicateResult)
        val expected = ObjectField(schema, "x").getField(Dummy::type).toDopeType().isEqualTo("test")
        assertEquals(expected, predicateResult)
    }
}
