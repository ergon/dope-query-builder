package ch.ergon.dope.extensions

import ch.ergon.dope.extension.case
import ch.ergon.dope.extension.`else`
import ch.ergon.dope.extension.`when`
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBooleanFieldList
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someCMType
import ch.ergon.dope.helper.someCaseClass
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberFieldList
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringFieldList
import ch.ergon.dope.resolvable.expression.CaseClass
import ch.ergon.dope.resolvable.expression.SearchedCaseExpression
import ch.ergon.dope.resolvable.expression.SearchedElseCaseExpression
import ch.ergon.dope.resolvable.expression.SimpleCaseExpression
import ch.ergon.dope.resolvable.expression.SimpleElseCaseExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class CaseExpressionTest {

    @Test
    fun `should create CaseClass from CMNumberField`() {
        val expression = someCMNumberField()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should create CaseClass from CMStringField`() {
        val expression = someCMStringField()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should create CaseClass from CMBooleanField`() {
        val expression = someCMBooleanField()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should create CaseClass from CMList of Number`() {
        val expression = someCMNumberList()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should create CaseClass from CMList of String`() {
        val expression = someCMStringList()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should create CaseClass from CMList of Boolean`() {
        val expression = someCMBooleanList()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMNumberField to CaseClass`() {
        val case = someCaseClass(someNumberField())
        val field = someCMNumberField()
        val expression = someNumberField()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression)

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMStringField to CaseClass`() {
        val case = someCaseClass(someStringField())
        val field = someCMStringField()
        val expression = someNumberField()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression)

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMBooleanField to CaseClass`() {
        val case = someCaseClass(someBooleanField())
        val field = someCMBooleanField()
        val expression = someNumberField()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression)

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of Number to CaseClass`() {
        val case = someCaseClass(someNumberFieldList())
        val field = someCMNumberList()
        val expression = someNumberField()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression)

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of String to CaseClass`() {
        val case = someCaseClass(someStringFieldList())
        val field = someCMStringList()
        val expression = someNumberField()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression)

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of Boolean to CaseClass`() {
        val case = someCaseClass(someBooleanFieldList())
        val field = someCMBooleanList()
        val expression = someNumberField()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression)

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Number and CMType to CaseClass`() {
        val case = someCaseClass(someNumberField())
        val field = someCMNumberField()
        val expression = someCMType()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression.toDopeType())

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of String and CMType to CaseClass`() {
        val case = someCaseClass(someStringField())
        val field = someCMStringField()
        val expression = someCMType()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression.toDopeType())

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Boolean and CMType to CaseClass`() {
        val case = someCaseClass(someBooleanField())
        val field = someCMBooleanField()
        val expression = someCMType()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression.toDopeType())

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of Number and CMType to CaseClass`() {
        val case = someCaseClass(someNumberFieldList())
        val field = someCMNumberList()
        val expression = someCMType()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression.toDopeType())

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of String and CMType to CaseClass`() {
        val case = someCaseClass(someStringFieldList())
        val field = someCMStringList()
        val expression = someCMType()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression.toDopeType())

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of Boolean and CMType to CaseClass`() {
        val case = someCaseClass(someBooleanFieldList())
        val field = someCMBooleanList()
        val expression = someCMType()
        val expected = SimpleCaseExpression(case.case, field.toDopeType() to expression.toDopeType())

        val actual = case.`when`(field, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Number and UnaliasedExpression to SimpleCaseExpression`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = SimpleCaseExpression(case.case, someCMNumberField().toDopeType() to someNumberField())
        val whenExpression = someCMNumberField()
        val typeExpression = someNumberField()
        val expected = SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression)

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of String and UnaliasedExpression to SimpleCaseExpression`() {
        val case = someCaseClass(someStringField())
        val initialExpression = SimpleCaseExpression(case.case, someCMStringField().toDopeType() to someNumberField())
        val whenExpression = someCMStringField()
        val typeExpression = someStringField()
        val expected = SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression)

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Boolean and UnaliasedExpression to SimpleCaseExpression`() {
        val case = someCaseClass(someBooleanField())
        val initialExpression = SimpleCaseExpression(case.case, someCMBooleanField().toDopeType() to someNumberField())
        val whenExpression = someCMBooleanField()
        val typeExpression = someBooleanField()
        val expected = SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression)

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of Number and UnaliasedExpression to SimpleCaseExpression`() {
        val case = someCaseClass(someNumberFieldList())
        val initialExpression = SimpleCaseExpression(case.case, someCMNumberList().toDopeType() to someNumberField())
        val whenExpression = someCMNumberList()
        val typeExpression = someNumberField()
        val expected = SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression)

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of String and UnaliasedExpression to SimpleCaseExpression`() {
        val case = someCaseClass(someStringFieldList())
        val initialExpression = SimpleCaseExpression(case.case, someCMStringList().toDopeType() to someStringField())
        val whenExpression = someCMStringList()
        val typeExpression = someStringField()
        val expected = SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression)

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of Boolean and UnaliasedExpression to SimpleCaseExpression`() {
        val case = someCaseClass(someBooleanFieldList())
        val initialExpression = SimpleCaseExpression(case.case, someCMBooleanList().toDopeType() to someBooleanField())
        val whenExpression = someCMBooleanList()
        val typeExpression = someBooleanField()
        val expected = SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression)

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Number and CMType to SimpleCaseExpression`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = SimpleCaseExpression(case.case, someCMNumberField().toDopeType() to someNumberField())
        val whenExpression = someCMNumberField()
        val typeExpression = someCMType()
        val expected =
            SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression.toDopeType())

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of String and CMType to SimpleCaseExpression`() {
        val case = someCaseClass(someStringField())
        val initialExpression = SimpleCaseExpression(case.case, someCMStringField().toDopeType() to someStringField())
        val whenExpression = someCMStringField()
        val typeExpression = someCMType()
        val expected =
            SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression.toDopeType())

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Boolean and CMType to SimpleCaseExpression`() {
        val case = someCaseClass(someBooleanField())
        val initialExpression = SimpleCaseExpression(case.case, someCMBooleanField().toDopeType() to someBooleanField())
        val whenExpression = someCMBooleanField()
        val typeExpression = someCMType()
        val expected =
            SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression.toDopeType())

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of Number and CMType to SimpleCaseExpression`() {
        val case = someCaseClass(someNumberFieldList())
        val initialExpression = SimpleCaseExpression(case.case, someCMNumberList().toDopeType() to someNumberField())
        val whenExpression = someCMNumberList()
        val typeExpression = someCMType()
        val expected =
            SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression.toDopeType())

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of String and CMType to SimpleCaseExpression`() {
        val case = someCaseClass(someStringFieldList())
        val initialExpression = SimpleCaseExpression(case.case, someCMStringList().toDopeType() to someStringField())
        val whenExpression = someCMStringList()
        val typeExpression = someCMType()
        val expected =
            SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression.toDopeType())

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMList of Boolean and CMType to SimpleCaseExpression`() {
        val case = someCaseClass(someBooleanFieldList())
        val initialExpression = SimpleCaseExpression(case.case, someCMBooleanList().toDopeType() to someBooleanField())
        val whenExpression = someCMBooleanList()
        val typeExpression = someCMType()
        val expected =
            SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression.toDopeType() to typeExpression.toDopeType())

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with TypeExpression and CMType to SimpleCaseExpression`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = SimpleCaseExpression(case.case, someCMNumberField().toDopeType() to someNumberField())
        val whenExpression = someNumberField()
        val typeExpression = someCMType()
        val expected = SimpleCaseExpression(case.case, initialExpression.firstWhenThen, whenExpression to typeExpression.toDopeType())

        val actual = initialExpression.`when`(whenExpression, typeExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add else condition with CMType to SimpleCaseExpression`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = SimpleCaseExpression(case.case, someCMNumberField().toDopeType() to someNumberField())
        val elseExpression = someCMType()
        val expected =
            SimpleElseCaseExpression(
                case.case,
                initialExpression.firstWhenThen,
                *initialExpression.additionalWhenThen,
                elseCase = elseExpression.toDopeType(),
            )

        val actual = initialExpression.`else`(elseExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Boolean and UnaliasedExpression to SearchCaseExpression`() {
        val whenCondition = someCMBooleanField()
        val thenExpression = someNumberField()
        val expected = SearchedCaseExpression(whenCondition.toDopeType() to thenExpression)

        val actual = `when`(whenCondition, thenExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with TypeExpression of BooleanType and CMType to SearchCaseExpression`() {
        val whenCondition = someBooleanField()
        val thenExpression = someCMType()
        val expected = SearchedCaseExpression(whenCondition to thenExpression.toDopeType())

        val actual = `when`(whenCondition, thenExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Boolean and CMType to SearchCaseExpression`() {
        val whenCondition = someCMBooleanField()
        val thenExpression = someCMType()
        val expected = SearchedCaseExpression(whenCondition.toDopeType() to thenExpression.toDopeType())

        val actual = `when`(whenCondition, thenExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Boolean and UnaliasedExpression to SearchedCaseExpression`() {
        val initialExpression = SearchedCaseExpression(someCMBooleanField().toDopeType() to someNumberField())
        val whenCondition = someCMBooleanField()
        val thenExpression = someNumberField()
        val expected = SearchedCaseExpression(
            initialExpression.firstWhenThen,
            *initialExpression.additionalWhenThen,
            whenCondition.toDopeType() to thenExpression,
        )

        val actual = initialExpression.`when`(whenCondition, thenExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with TypeExpression of BooleanType and CMType to SearchedCaseExpression`() {
        val initialExpression = SearchedCaseExpression(someCMBooleanField().toDopeType() to someNumberField())
        val whenCondition = someBooleanField()
        val thenExpression = someCMType()
        val expected = SearchedCaseExpression(
            initialExpression.firstWhenThen,
            *initialExpression.additionalWhenThen,
            whenCondition to thenExpression.toDopeType(),
        )

        val actual = initialExpression.`when`(whenCondition, thenExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add when condition with CMField of Boolean and CMType to SearchedCaseExpression`() {
        val initialExpression = SearchedCaseExpression(someCMBooleanField().toDopeType() to someNumberField())
        val whenCondition = someCMBooleanField()
        val thenExpression = someCMType()
        val expected = SearchedCaseExpression(
            initialExpression.firstWhenThen,
            *initialExpression.additionalWhenThen,
            whenCondition.toDopeType() to thenExpression.toDopeType(),
        )

        val actual = initialExpression.`when`(whenCondition, thenExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should add else condition with CMType to SearchedCaseExpression`() {
        val initialExpression = SearchedCaseExpression(someCMBooleanField().toDopeType() to someNumberField())
        val elseExpression = someCMType()
        val expected = SearchedElseCaseExpression(
            initialExpression.firstWhenThen,
            *initialExpression.additionalWhenThen,
            elseCase = elseExpression.toDopeType(),
        )

        val actual = initialExpression.`else`(elseExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
