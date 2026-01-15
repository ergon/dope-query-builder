package ch.ergon.dope.extensions.expression.type.relational

import ch.ergon.dope.extension.expression.type.relational.every
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class EveryTest {
    @Test
    fun `should support every with CMJsonList number`() {
        val list = someCMNumberList()

        val actual = list.every("i") { it.isEqualTo(1.toDopeType()) }

        assertEquals(list.toDopeType(), actual.arrayExpression)
        assertEquals("i", actual.iteratorName)

        val predicateResult = actual.predicate(Iterator("x"))
        assertNotNull(predicateResult)
        assertEquals(EqualsExpression(Iterator("x"), 1.toDopeType()), predicateResult)
    }
}
