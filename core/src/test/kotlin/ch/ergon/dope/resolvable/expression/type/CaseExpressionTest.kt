package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCaseClass
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.conditional.SearchResult
import kotlin.test.Test
import kotlin.test.assertEquals

class CaseExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support simple case expression with single when then`() {
        val expected = DopeQuery(
            queryString = "CASE `numberField` WHEN `other` THEN `stringField` END",
        )
        val underTest = CaseExpression(
            CaseClass(someNumberField()),
            SearchResult(someNumberField("other"), someStringField()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support simple case expression with multiple when then`() {
        val expected = DopeQuery(
            queryString = "CASE `numberField` WHEN `other` THEN `stringField` WHEN `other2` THEN `numberField` END",
        )
        val underTest = CaseExpression(
            CaseClass(someNumberField()),
            SearchResult(someNumberField("other"), someStringField()),
            SearchResult(someNumberField("other2"), someNumberField()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support simple case expression with multiple when then all parameters`() {
        val positionalParameter1 = someNumber()
        val positionalParameter2 = someNumber()
        val namedParameter1 = someBoolean()
        val namedParameter2 = someBoolean()
        val parameterName1 = someString("booleanTrue")
        val parameterName2 = someString("booleanFalse")
        val expected = DopeQuery(
            queryString = "CASE `numberField` WHEN $1 THEN \$booleanTrue WHEN $2 THEN \$booleanFalse END",
            parameters = DopeParameters(
                namedParameters = mapOf(parameterName1 to namedParameter1, parameterName2 to namedParameter2),
                positionalParameters = listOf(positionalParameter1, positionalParameter2),
            ),
        )
        val underTest = CaseExpression(
            CaseClass(someNumberField()),
            SearchResult(positionalParameter1.asParameter(), namedParameter1.asParameter(parameterName1)),
            SearchResult(positionalParameter2.asParameter(), namedParameter2.asParameter(parameterName2)),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support simple else case expression with single when then`() {
        val expected = DopeQuery(
            queryString = "CASE `numberField` WHEN `other` THEN `stringField` ELSE `numberField` END",
        )
        val underTest = ElseCaseExpression(
            CaseClass(someNumberField()),
            SearchResult(someNumberField("other"), someStringField()),
            elseCase = someNumberField(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support simple else case expression with multiple when then`() {
        val expected = DopeQuery(
            queryString = "CASE `numberField` WHEN `other` THEN `stringField` WHEN `other2` THEN `numberField` ELSE `stringField` END",
        )
        val underTest = ElseCaseExpression(
            CaseClass(someNumberField()),
            SearchResult(someNumberField("other"), someStringField()),
            SearchResult(someNumberField("other2"), someNumberField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support searched case expression with single when then`() {
        val expected = DopeQuery(
            queryString = "CASE WHEN `booleanField` THEN `stringField` END",
        )
        val underTest = CaseExpression(
            CaseClass(),
            SearchResult(someBooleanField(), someStringField()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support searched case expression with multiple when then`() {
        val expected = DopeQuery(
            queryString = "CASE WHEN `first` THEN `stringField` WHEN `second` THEN `numberField` END",
        )
        val underTest = CaseExpression(
            CaseClass(),
            SearchResult(someBooleanField("first"), someStringField()),
            SearchResult(someBooleanField("second"), someNumberField()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support searched else case expression with single when then`() {
        val expected = DopeQuery(
            queryString = "CASE WHEN `booleanField` THEN `stringField` ELSE `numberField` END",
        )
        val underTest = ElseCaseExpression(
            CaseClass(),
            SearchResult(someBooleanField(), someStringField()),
            elseCase = someNumberField(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support searched else case expression with multiple when then`() {
        val expected = DopeQuery(
            queryString = "CASE WHEN `booleanField` THEN `stringField` WHEN `other2` THEN `numberField` ELSE `stringField` END",
        )
        val underTest = ElseCaseExpression(
            CaseClass(),
            SearchResult(someBooleanField(), someStringField()),
            SearchResult(someBooleanField("other2"), someNumberField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery(manager)

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

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support when then function with simple case`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val firstCondition = SearchResult(someNumberField(), someStringField())
        val expected = CaseExpression(case, firstCondition)

        val actual = case.condition(firstCondition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple when then function with simple case`() {
        val numberField1 = someNumberField()
        val case = someCaseClass(numberField1)
        val firstCondition = SearchResult(someNumberField(), someStringField())
        val additionalCondition = SearchResult(someNumberField(), someNumberField())
        val expected = CaseExpression(case, firstCondition, additionalCondition)

        val actual = case.condition(firstCondition).condition(additionalCondition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple when then function with simple case same type`() {
        val numberField1 = someNumberField()
        val case = someCaseClass(numberField1)
        val firstCondition = SearchResult(someNumberField(), someStringField())
        val additionalCondition = SearchResult(someNumberField(), someStringField())
        val expected = CaseExpression(case, firstCondition, additionalCondition)

        val actual = case.condition(firstCondition).condition(additionalCondition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support when then function with simple case and else`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val whenExpression = someNumberField()
        val thenExpression = someStringField()
        val elseCase = someNumberField()
        val firstCondition = SearchResult(whenExpression, thenExpression)
        val expected = ElseCaseExpression(case, firstCondition, elseCase = elseCase)

        val actual = case.condition(firstCondition).otherwise(elseCase)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple when then function with simple case and else`() {
        val numberField = someNumberField()
        val case = someCaseClass(numberField)
        val firstCondition = SearchResult(someNumberField(), someStringField())
        val additionalCondition = SearchResult(someNumberField(), someNumberField())
        val elseCase = someNumberField()
        val expected = ElseCaseExpression(
            case,
            firstCondition,
            additionalCondition,
            elseCase = elseCase,
        )

        val actual = case.condition(firstCondition).condition(additionalCondition).otherwise(elseCase)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support when then function with searched case`() {
        val firstCondition = SearchResult(someBooleanField(), someStringField())
        val expected = CaseExpression(CaseClass(), firstCondition)

        val actual = case().condition(firstCondition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple when then function with searched case same type`() {
        val firstCondition = SearchResult(someBooleanField(), someStringField())
        val additionalCondition = SearchResult(someBooleanField(), someStringField())
        val expected = CaseExpression(CaseClass(), firstCondition, additionalCondition)

        val actual = case().condition(firstCondition).condition(additionalCondition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple when then function with searched case`() {
        val whenExpression = someBooleanField()
        val firstCondition = SearchResult(whenExpression, someStringField())
        val additionalCondition = SearchResult(someBooleanField(), someNumberField())
        val expected = CaseExpression(CaseClass(), firstCondition, additionalCondition)

        val actual = case().condition(firstCondition).condition(additionalCondition)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support when then function with searched case and else`() {
        val firstCondition = SearchResult(someBooleanField(), someStringField())
        val elseCase = someNumberField()
        val expected = ElseCaseExpression(CaseClass(), firstCondition, elseCase = elseCase)

        val actual = case().condition(firstCondition).otherwise(elseCase)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple when then function with searched case and else`() {
        val firstCondition = SearchResult(someBooleanField(), someStringField())
        val additionalCondition = SearchResult(someBooleanField(), someNumberField())
        val elseCase = someNumberField()
        val expected = ElseCaseExpression(
            CaseClass(),
            firstCondition,
            additionalCondition,
            elseCase = elseCase,
        )

        val actual = case().condition(firstCondition).condition(additionalCondition).otherwise(elseCase)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
