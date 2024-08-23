package ch.ergon.dope.extensions.type.logical

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.logical.not
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.NotExpression
import ch.ergon.dope.toDopeType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NotTest {
    private lateinit var manager: DopeQueryManager

    @BeforeTest
    fun setup() {
        manager = DopeQueryManager()
    }

    @Test
    fun `should support not with CMField CMField`() {
        val expression = someCMBooleanField()
        val expected = NotExpression(expression.toDopeType())

        val actual = not(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
