package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCaseClass
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
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
            someNumberField(),
            someNumberField("other") to someStringField(),
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
            someNumberField(),
            someNumberField("other") to someStringField(),
            someNumberField("other2") to someNumberField(),
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
            someNumberField(),
            someNumberField("other") to someStringField(),
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
            someNumberField(),
            someNumberField("other") to someStringField(),
            someNumberField("other2") to someNumberField(),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression with simple case`() {
        val expected = DopeQuery(
            "CASE `numberField` WHEN `other` THEN `stringField` AS `alias` END",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            alias = "alias",
            unaliasedCaseExpression = SimpleCaseExpression(
                someNumberField(),
                someNumberField("other") to someStringField(),
            ),
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
            someBooleanField() to someStringField(),
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
            someBooleanField("first") to someStringField(),
            someBooleanField("second") to someNumberField(),
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
            someBooleanField() to someStringField(),
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
            someBooleanField() to someStringField(),
            someBooleanField("other2") to someNumberField(),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression with searched case`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `stringField` AS `alias` END",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            alias = "alias",
            unaliasedCaseExpression = SearchedCaseExpression(
                someBooleanField() to someStringField(),
            ),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support case function`() {
        val numberField = someNumberField()
        val expected = CaseClass(
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
        val whenExpression = someNumberField()
        val thenExpression = someStringField()
        val expected = SimpleCaseExpression(numberField, whenExpression to thenExpression)

        val actual = case.`when`(whenExpression, thenExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with simple case`() {
        val numberField1 = someNumberField()
        val case = someCaseClass(numberField1)
        val whenExpression = someNumberField()
        val thenExpression = someStringField()
        val additionalWhen = someNumberField()
        val additionalThen = someNumberField()
        val expected = SimpleCaseExpression(numberField1, whenExpression to thenExpression, additionalWhen to additionalThen)

        val actual = case.`when`(whenExpression, thenExpression).`when`(additionalWhen, additionalThen)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with simple case and else`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val whenExpression = someNumberField()
        val thenExpression = someStringField()
        val elseCase = someNumberField()
        val expected = SimpleElseCaseExpression(numberField, whenExpression to thenExpression, elseCase = elseCase)

        val actual = case.`when`(whenExpression, thenExpression).`else`(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with simple case and else`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val whenExpression = someNumberField()
        val thenExpression = someStringField()
        val additionalWhen = someNumberField()
        val additionalThen = someNumberField()
        val elseCase = someNumberField()
        val expected = SimpleElseCaseExpression(
            numberField,
            whenExpression to thenExpression,
            additionalWhen to additionalThen,
            elseCase = elseCase,
        )

        val actual = case.`when`(whenExpression, thenExpression).`when`(additionalWhen, additionalThen).`else`(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with searched case`() {
        val whenExpression = someBooleanField()
        val thenExpression = someStringField()
        val expected = SearchedCaseExpression(whenExpression to thenExpression)

        val actual = `when`(whenExpression, thenExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with searched case`() {
        val whenExpression = someBooleanField()
        val thenExpression = someStringField()
        val additionalWhen = someBooleanField()
        val additionalThen = someNumberField()
        val expected = SearchedCaseExpression(whenExpression to thenExpression, additionalWhen to additionalThen)

        val actual = `when`(whenExpression, thenExpression).`when`(additionalWhen, additionalThen)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with searched case and else`() {
        val whenExpression = someBooleanField()
        val thenExpression = someStringField()
        val elseCase = someNumberField()
        val expected = SearchedElseCaseExpression(whenExpression to thenExpression, elseCase = elseCase)

        val actual = `when`(whenExpression, thenExpression).`else`(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with searched case and else`() {
        val whenExpression = someBooleanField()
        val thenExpression = someStringField()
        val additionalWhen = someBooleanField()
        val additionalThen = someNumberField()
        val elseCase = someNumberField()
        val expected = SearchedElseCaseExpression(
            whenExpression to thenExpression,
            additionalWhen to additionalThen,
            elseCase = elseCase,
        )

        val actual = `when`(whenExpression, thenExpression).`when`(additionalWhen, additionalThen).`else`(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
