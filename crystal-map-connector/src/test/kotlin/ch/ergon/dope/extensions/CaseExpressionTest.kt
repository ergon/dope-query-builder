package ch.ergon.dope.extensions

import ch.ergon.dope.extension.case
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.AliasedCaseExpression
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
    fun `should support case with cmBooleanField with additional when then`() {
        val field = someCMBooleanField()
        val expression = someStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val additionalWhenThenCondition = whenThen(someBooleanField(), someStringField())
        val expected = UnaliasedCaseExpression(field.toDopeType(), whenThenCondition, additionalWhenThenCondition, elseCase = expression)

        val actual = case(field, whenThenCondition, additionalWhenThenCondition, elseCase = expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support case with cmBooleanField alias`() {
        val field = someCMBooleanField()
        val expression = someStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val alias = "alias"
        val expected = AliasedCaseExpression(alias, field.toDopeType(), whenThenCondition, elseCase = expression)

        val actual = case(field, whenThenCondition, elseCase = expression).alias(alias)

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
    fun `should support case with cmStringField with additional when then`() {
        val field = someBooleanField()
        val expression = someCMStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val additionalWhenThenCondition = whenThen(someBooleanField(), someStringField())
        val expected = UnaliasedCaseExpression(field, whenThenCondition, additionalWhenThenCondition, elseCase = expression.toDopeType())

        val actual = case(field, whenThenCondition, additionalWhenThenCondition, elseCase = expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support case with cmStringField alias`() {
        val field = someBooleanField()
        val expression = someCMStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val alias = "alias"
        val expected = AliasedCaseExpression(alias, field, whenThenCondition, elseCase = expression.toDopeType())

        val actual = case(field, whenThenCondition, elseCase = expression).alias(alias)

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
    fun `should support case with cmBooleanField and someStringField with additional when then`() {
        val field = someCMBooleanField()
        val expression = someCMStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val additionalWhenThenCondition = whenThen(someBooleanField(), someStringField())
        val expected = UnaliasedCaseExpression(
            field.toDopeType(),
            whenThenCondition,
            additionalWhenThenCondition,
            elseCase = expression.toDopeType(),
        )

        val actual = case(field, whenThenCondition, additionalWhenThenCondition, elseCase = expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support case with cmBooleanField and someStringField alias`() {
        val field = someCMBooleanField()
        val expression = someCMStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val alias = "alias"
        val expected = AliasedCaseExpression(alias, field.toDopeType(), whenThenCondition, elseCase = expression.toDopeType())

        val actual = case(field, whenThenCondition, elseCase = expression).alias(alias)

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

    @Test
    fun `should support case with cmBooleanField without expression with additional when then`() {
        val expression = someCMStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val additionalWhenThenCondition = listOf(whenThen(someBooleanField(), someStringField())).toTypedArray()
        val expected = UnaliasedCaseExpression(
            whenThenCondition = whenThenCondition,
            additionalWhenThenConditions = additionalWhenThenCondition,
            elseCase = expression.toDopeType(),
        )

        val actual = case(
            whenThenCondition = whenThenCondition,
            additionalWhenThenConditions = additionalWhenThenCondition,
            elseCase = expression,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support case with cmBooleanField without expression alias`() {
        val expression = someCMStringField()
        val whenThenCondition = whenThen(someBooleanField(), someStringField())
        val alias = "alias"
        val expected = AliasedCaseExpression(alias, whenThenCondition = whenThenCondition, elseCase = expression.toDopeType())

        val actual = case(whenThenCondition, elseCase = expression).alias(alias)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
