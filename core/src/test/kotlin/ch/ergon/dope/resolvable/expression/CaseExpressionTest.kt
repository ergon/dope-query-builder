package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCaseClass
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SearchResult
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SearchedCaseClass
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SearchedCaseExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SearchedElseCaseExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SimpleCaseClass
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SimpleCaseExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SimpleElseCaseExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.case
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.otherwise
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.whenThen
import kotlin.test.Test
import kotlin.test.assertEquals

class CaseExpressionTest {
    @Test
    fun `should support simple case expression with single when then`() {
        val expected = DopeQuery(
            "CASE `numberField` WHEN `other` THEN `stringField` END",
            emptyMap(),
        )
        val underTest = SimpleCaseExpression(
            SimpleCaseClass(someNumberField()),
            SearchResult(someNumberField("other"), someStringField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support simple case expression with multiple when then`() {
        val expected = DopeQuery(
            "CASE `numberField` WHEN `other` THEN `stringField` WHEN `other2` THEN `numberField` END",
            emptyMap(),
        )
        val underTest = SimpleCaseExpression(
            SimpleCaseClass(someNumberField()),
            SearchResult(someNumberField("other"), someStringField()),
            SearchResult(someNumberField("other2"), someNumberField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support simple else case expression with single when then`() {
        val expected = DopeQuery(
            "CASE `numberField` WHEN `other` THEN `stringField` ELSE `numberField` END",
            emptyMap(),
        )
        val underTest = SimpleElseCaseExpression(
            SimpleCaseClass(someNumberField()),
            SearchResult(someNumberField("other"), someStringField()),
            elseCase = someNumberField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support simple else case expression with multiple when then`() {
        val expected = DopeQuery(
            "CASE `numberField` WHEN `other` THEN `stringField` WHEN `other2` THEN `numberField` ELSE `stringField` END",
            emptyMap(),
        )
        val underTest = SimpleElseCaseExpression(
            SimpleCaseClass(someNumberField()),
            SearchResult(someNumberField("other"), someStringField()),
            SearchResult(someNumberField("other2"), someNumberField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support searched case expression with single when then`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `stringField` END",
            emptyMap(),
        )
        val underTest = SearchedCaseExpression(
            SearchedCaseClass(),
            SearchResult(someBooleanField(), someStringField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support searched case expression with multiple when then`() {
        val expected = DopeQuery(
            "CASE WHEN `first` THEN `stringField` WHEN `second` THEN `numberField` END",
            emptyMap(),
        )
        val underTest = SearchedCaseExpression(
            SearchedCaseClass(),
            SearchResult(someBooleanField("first"), someStringField()),
            SearchResult(someBooleanField("second"), someNumberField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support searched else case expression with single when then`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `stringField` ELSE `numberField` END",
            emptyMap(),
        )
        val underTest = SearchedElseCaseExpression(
            SearchedCaseClass(),
            SearchResult(someBooleanField(), someStringField()),
            elseCase = someNumberField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support searched else case expression with multiple when then`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `stringField` WHEN `other2` THEN `numberField` ELSE `stringField` END",
            emptyMap(),
        )
        val underTest = SearchedElseCaseExpression(
            SearchedCaseClass(),
            SearchResult(someBooleanField(), someStringField()),
            SearchResult(someBooleanField("other2"), someNumberField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support case function`() {
        val numberField = someNumberField()
        val expected = SimpleCaseClass(
            numberField,
        )

        val actual = case(
            numberField,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with simple case`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val firstWhenThen = SearchResult(someNumberField(), someStringField())
        val expected = SimpleCaseExpression(case, firstWhenThen)

        val actual = case.whenThen(firstWhenThen)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with simple case`() {
        val numberField1 = someNumberField()
        val case = someCaseClass(numberField1)
        val firstWhenThen = SearchResult(someNumberField(), someStringField())
        val additionalWhenThen = SearchResult(someNumberField(), someNumberField())
        val expected = SimpleCaseExpression(case, firstWhenThen, additionalWhenThen)

        val actual = case.whenThen(firstWhenThen).whenThen(additionalWhenThen)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with simple case same type`() {
        val numberField1 = someNumberField()
        val case = someCaseClass(numberField1)
        val firstWhenThen = SearchResult(someNumberField(), someStringField())
        val additionalWhenThen = SearchResult(someNumberField(), someStringField())
        val expected = SimpleCaseExpression(case, firstWhenThen, additionalWhenThen)

        val actual = case.whenThen(firstWhenThen).whenThen(additionalWhenThen)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with simple case and else`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val whenExpression = someNumberField()
        val thenExpression = someStringField()
        val elseCase = someNumberField()
        val firstWhenThen = SearchResult(whenExpression, thenExpression)
        val expected = SimpleElseCaseExpression(case, firstWhenThen, elseCase = elseCase)

        val actual = case.whenThen(firstWhenThen).otherwise(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with simple case and else`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val firstWhenThen = SearchResult(someNumberField(), someStringField())
        val additionalWhenThen = SearchResult(someNumberField(), someNumberField())
        val elseCase = someNumberField()
        val expected = SimpleElseCaseExpression(
            case,
            firstWhenThen,
            additionalWhenThen,
            elseCase = elseCase,
        )

        val actual = case.whenThen(firstWhenThen).whenThen(additionalWhenThen).otherwise(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with searched case`() {
        val firstWhenThen = SearchResult(someBooleanField(), someStringField())
        val expected = SearchedCaseExpression(SearchedCaseClass(), firstWhenThen)

        val actual = case().whenThen(firstWhenThen)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with searched case same type`() {
        val firstWhenThen = SearchResult(someBooleanField(), someStringField())
        val additionalWhenThen = SearchResult(someBooleanField(), someStringField())
        val expected = SearchedCaseExpression(SearchedCaseClass(), firstWhenThen, additionalWhenThen)

        val actual = case().whenThen(firstWhenThen).whenThen(additionalWhenThen)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with searched case`() {
        val whenExpression = someBooleanField()
        val firstWhenThen = SearchResult(whenExpression, someStringField())
        val additionalWhenThen = SearchResult(someBooleanField(), someNumberField())
        val expected = SearchedCaseExpression(SearchedCaseClass(), firstWhenThen, additionalWhenThen)

        val actual = case().whenThen(firstWhenThen).whenThen(additionalWhenThen)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with searched case and else`() {
        val firstWhenThen = SearchResult(someBooleanField(), someStringField())
        val elseCase = someNumberField()
        val expected = SearchedElseCaseExpression(SearchedCaseClass(), firstWhenThen, elseCase = elseCase)

        val actual = case().whenThen(firstWhenThen).otherwise(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with searched case and else`() {
        val firstWhenThen = SearchResult(someBooleanField(), someStringField())
        val additionalWhenThen = SearchResult(someBooleanField(), someNumberField())
        val elseCase = someNumberField()
        val expected = SearchedElseCaseExpression(
            SearchedCaseClass(),
            firstWhenThen,
            additionalWhenThen,
            elseCase = elseCase,
        )

        val actual = case().whenThen(firstWhenThen).whenThen(additionalWhenThen).otherwise(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
