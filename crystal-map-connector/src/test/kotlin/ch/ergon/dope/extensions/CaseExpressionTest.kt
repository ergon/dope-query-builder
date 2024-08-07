package ch.ergon.dope.extensions

import ch.ergon.dope.extension.case
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.UnaliasedCaseExpression
import ch.ergon.dope.resolvable.whenThen
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class CaseExpressionTest {
    @Test
    fun `should support case with cmBooleanField`() {
        val field = someCMBooleanField()
        val expression = someStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val expected = UnaliasedCaseExpression(field.toDopeType(), whenThenCondition, elseCase = expression)

        val actual = case(field, whenThenCondition, elseCase = expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support case with cmStringField`() {
        val field = someBooleanField()
        val expression = someCMStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val expected = UnaliasedCaseExpression(field, whenThenCondition, elseCase = expression.toDopeType())

        val actual = case(field, whenThenCondition, elseCase = expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support case with cmBooleanField and someStringField`() {
        val field = someCMBooleanField()
        val expression = someCMStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val expected = UnaliasedCaseExpression(field.toDopeType(), whenThenCondition, elseCase = expression.toDopeType())

        val actual = case(field, whenThenCondition, elseCase = expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support case with cmBooleanField without expression`() {
        val expression = someCMStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val expected = UnaliasedCaseExpression(whenThenCondition = whenThenCondition, elseCase = expression.toDopeType())

        val actual = case(whenThenCondition, elseCase = expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
