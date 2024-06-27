package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.relational.isLike
import ch.ergon.dope.helper.someCMStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class LikeTest {
    @Test
    fun `should support extensions for is missing with CMField`() {
        val expected = DopeQuery(
            queryString = "`CMStringField` LIKE \"%\"",
            parameters = emptyMap(),
        )

        val actual = someCMStringField().isLike("%").toDopeQuery()

        assertEquals(expected, actual)
    }
}
