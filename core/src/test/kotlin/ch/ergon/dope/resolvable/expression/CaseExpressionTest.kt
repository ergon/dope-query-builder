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
        val whenThen = someNumberField() to someStringField()
        val expected = SimpleCaseExpression(numberField, whenThen)

        val actual = case.`when`(whenThen.first, whenThen.second)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with simple case`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val whenThen = someNumberField() to someStringField()
        val additionalWhenThen = someNumberField() to someNumberField()
        val expected = SimpleCaseExpression(numberField, whenThen, additionalWhenThen)

        val actual = case.`when`(whenThen.first, whenThen.second).`when`(additionalWhenThen.first, additionalWhenThen.second)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with simple case and else`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val whenThen = someNumberField() to someStringField()
        val elseCase = someNumberField()
        val expected = SimpleElseCaseExpression(numberField, whenThen, elseCase = elseCase)

        val actual = case.`when`(whenThen.first, whenThen.second).`else`(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with simple case and else`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val whenThen = someNumberField() to someStringField()
        val additionalWhenThen = someNumberField() to someNumberField()
        val elseCase = someNumberField()
        val expected = SimpleElseCaseExpression(numberField, whenThen, additionalWhenThen, elseCase = elseCase)

        val actual = case.`when`(whenThen.first, whenThen.second).`when`(additionalWhenThen.first, additionalWhenThen.second).`else`(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with searched case`() {
        val whenThen = someBooleanField() to someStringField()
        val expected = SearchedCaseExpression(whenThen)

        val actual = `when`(whenThen.first, whenThen.second)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with searched case`() {
        val whenThen = someBooleanField() to someStringField()
        val additionalWhenThen = someBooleanField() to someNumberField()
        val expected = SearchedCaseExpression(whenThen, additionalWhenThen)

        val actual = `when`(whenThen.first, whenThen.second).`when`(additionalWhenThen.first, additionalWhenThen.second)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support when then function with searched case and else`() {
        val whenThen = someBooleanField() to someStringField()
        val elseCase = someNumberField()
        val expected = SearchedElseCaseExpression(whenThen, elseCase = elseCase)

        val actual = `when`(whenThen.first, whenThen.second).`else`(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support multiple when then function with searched case and else`() {
        val whenThen = someBooleanField() to someStringField()
        val additionalWhenThen = someBooleanField() to someNumberField()
        val elseCase = someNumberField()
        val expected = SearchedElseCaseExpression(whenThen, additionalWhenThen, elseCase = elseCase)

        val actual = `when`(whenThen.first, whenThen.second).`when`(additionalWhenThen.first, additionalWhenThen.second).`else`(elseCase)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
