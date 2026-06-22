package ch.ergon.dope.extensions.expression.type.function.array

import ch.ergon.dope.couchbase.resolvable.expression.type.function.array.UnpackExpression
import ch.ergon.dope.extension.expression.type.function.array.unpack
import ch.ergon.dope.helper.someCMObjectList
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionCouchbaseTest {
    @Test
    fun `should support get asterisk with CM Object list as receiver`() {
        val cMJsonList = someCMObjectList()
        val expected = UnpackExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.unpack()

        assertEquals(expected, actual)
    }
}
