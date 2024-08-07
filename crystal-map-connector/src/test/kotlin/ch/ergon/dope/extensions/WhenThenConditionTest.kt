package ch.ergon.dope.extensions

import ch.ergon.dope.extension.whenThen
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.WhenThenCondition
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class WhenThenConditionTest {
    @Test
    fun `should support when then with cmBooleanField`() {
        val field = someCMBooleanField()
        val expression = someStringField()
        val expected = WhenThenCondition(field.toDopeType(), expression)

        val actual = whenThen(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then with cmStringField`() {
        val field = someBooleanField()
        val expression = someCMStringField()
        val expected = WhenThenCondition(field, expression.toDopeType())

        val actual = whenThen(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then with cmBooleanField and someStringField`() {
        val field = someCMBooleanField()
        val expression = someCMStringField()
        val expected = WhenThenCondition(field.toDopeType(), expression.toDopeType())

        val actual = whenThen(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
