package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.isLike
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LikeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LikeTest {
    @Test
    fun `should support extensions for is missing with CMField`() {
        val someCMStringField = someCMStringField()
        val right = "%".toDopeType()
        val expected = LikeExpression(someCMStringField.toDopeType(), right)

        val actual = someCMStringField.isLike(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
