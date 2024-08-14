package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.whenThen
import kotlin.test.Test
import kotlin.test.assertEquals

class CaseExpressionTest {
    @Test
    fun `should support unaliased case expression`() {
        val expected = DopeQuery(
            "CASE `stringField` WHEN `booleanField` THEN `numberField` ELSE `stringField` END",
            emptyMap(),
        )
        val underTest = UnaliasedCaseExpression(
            expression = someStringField(),
            whenThenCondition = whenThen(someBooleanField(), someNumberField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unaliased case expression with multiple when then`() {
        val expected = DopeQuery(
            "CASE `stringField` WHEN `booleanField` THEN `numberField` WHEN `booleanField` THEN" +
                " `stringField` ELSE `stringField` END",
            emptyMap(),
        )
        val underTest = UnaliasedCaseExpression(
            someStringField(),
            whenThen(someBooleanField(), someNumberField()),
            whenThen(someBooleanField(), someStringField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unaliased case expression without else`() {
        val expected = DopeQuery(
            "CASE `stringField` WHEN `booleanField` THEN `numberField` END",
            emptyMap(),
        )
        val underTest = UnaliasedCaseExpression(
            expression = someStringField(),
            whenThenCondition = whenThen(someBooleanField(), someNumberField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unaliased case expression with multiple when then without else`() {
        val expected = DopeQuery(
            "CASE `stringField` WHEN `booleanField` THEN `numberField` WHEN `booleanField` THEN `stringField` END",
            emptyMap(),
        )
        val underTest = UnaliasedCaseExpression(
            someStringField(),
            whenThen(someBooleanField(), someNumberField()),
            whenThen(someBooleanField(), someStringField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unaliased case expression without expression`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `numberField` ELSE `stringField` END",
            emptyMap(),
        )
        val underTest = UnaliasedCaseExpression(
            whenThenCondition = whenThen(someBooleanField(), someNumberField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unaliased case expression with multiple when then without expression`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `numberField` WHEN `booleanField` THEN" +
                " `stringField` ELSE `stringField` END",
            emptyMap(),
        )
        val underTest = UnaliasedCaseExpression(
            expression = null,
            whenThen(someBooleanField(), someNumberField()),
            whenThen(someBooleanField(), someStringField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unaliased case expression without else without expression`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `numberField` END",
            emptyMap(),
        )
        val underTest = UnaliasedCaseExpression(
            whenThenCondition = whenThen(someBooleanField(), someNumberField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unaliased case expression with multiple when then without else without expression`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `numberField` WHEN `booleanField` THEN `stringField` END",
            emptyMap(),
        )
        val underTest = UnaliasedCaseExpression(
            expression = null,
            whenThen(someBooleanField(), someNumberField()),
            whenThen(someBooleanField(), someStringField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression`() {
        val expected = DopeQuery(
            "CASE `stringField` WHEN `booleanField` THEN `numberField` ELSE `stringField` END AS `alias`",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            "alias",
            expression = someStringField(),
            whenThenCondition = whenThen(someBooleanField(), someNumberField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression with multiple when then`() {
        val expected = DopeQuery(
            "CASE `stringField` WHEN `booleanField` THEN `numberField` WHEN `booleanField` THEN" +
                " `stringField` ELSE `stringField` END AS `alias`",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            "alias",
            someStringField(),
            whenThen(someBooleanField(), someNumberField()),
            whenThen(someBooleanField(), someStringField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression without else`() {
        val expected = DopeQuery(
            "CASE `stringField` WHEN `booleanField` THEN `numberField` END AS `alias`",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            "alias",
            expression = someStringField(),
            whenThenCondition = whenThen(someBooleanField(), someNumberField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression with multiple when then without else`() {
        val expected = DopeQuery(
            "CASE `stringField` WHEN `booleanField` THEN `numberField` " +
                "WHEN `booleanField` THEN `stringField` END AS `alias`",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            "alias",
            someStringField(),
            whenThen(someBooleanField(), someNumberField()),
            whenThen(someBooleanField(), someStringField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression without expression`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `numberField` ELSE `stringField` END AS `alias`",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            "alias",
            whenThenCondition = whenThen(someBooleanField(), someNumberField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression with multiple when then without expression`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `numberField` WHEN `booleanField` THEN" +
                " `stringField` ELSE `stringField` END AS `alias`",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            "alias",
            expression = null,
            whenThen(someBooleanField(), someNumberField()),
            whenThen(someBooleanField(), someStringField()),
            elseCase = someStringField(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression without else without expression`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `numberField` END AS `alias`",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            "alias",
            whenThenCondition = whenThen(someBooleanField(), someNumberField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased case expression with multiple when then without else without expression`() {
        val expected = DopeQuery(
            "CASE WHEN `booleanField` THEN `numberField` WHEN `booleanField` THEN `stringField` END AS `alias`",
            emptyMap(),
        )
        val underTest = AliasedCaseExpression(
            "alias",
            expression = null,
            whenThen(someBooleanField(), someNumberField()),
            whenThen(someBooleanField(), someStringField()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unaliased case expression function`() {
        val whenThenCondition = whenThen(someBooleanField(), someNumberField())
        val expected = UnaliasedCaseExpression(
            whenThenCondition = whenThenCondition,
        )

        val actual = case(
            whenThenCondition = whenThenCondition,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support unaliased case expression function with expression`() {
        val whenThenCondition = whenThen(someBooleanField(), someNumberField())
        val expression = someStringField()
        val expected = UnaliasedCaseExpression(
            expression = expression,
            whenThenCondition = whenThenCondition,
        )

        val actual = case(
            expression = expression,
            whenThenCondition = whenThenCondition,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support unaliased case expression function with expression and with else`() {
        val whenThenCondition = whenThen(someBooleanField(), someNumberField())
        val expression = someStringField()
        val elseCase = someStringField()
        val expected = UnaliasedCaseExpression(
            expression = expression,
            whenThenCondition = whenThenCondition,
            elseCase = elseCase,
        )

        val actual = case(
            expression = expression,
            whenThenCondition = whenThenCondition,
            elseCase = elseCase,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support unaliased case expression function and multiple when then`() {
        val whenThenCondition = whenThen(someBooleanField(), someNumberField())
        val additionalWhenThenCondition = whenThen(someBooleanField(), someNumberField())
        val expected = UnaliasedCaseExpression(
            expression = null,
            whenThenCondition,
            additionalWhenThenCondition,
        )

        val actual = case(
            whenThenCondition,
            additionalWhenThenCondition,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support unaliased case expression function with expression and multiple when then`() {
        val whenThenCondition = whenThen(someBooleanField(), someNumberField())
        val expression = someStringField()
        val additionalWhenThenCondition = whenThen(someBooleanField(), someNumberField())
        val expected = UnaliasedCaseExpression(
            expression,
            whenThenCondition,
            additionalWhenThenCondition,
        )

        val actual = case(
            expression,
            whenThenCondition,
            additionalWhenThenCondition,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support unaliased case expression function and multiple when then and else`() {
        val whenThenCondition = whenThen(someBooleanField(), someNumberField())
        val elseCase = someStringField()
        val additionalWhenThenCondition = whenThen(someBooleanField(), someNumberField())
        val expected = UnaliasedCaseExpression(
            expression = null,
            whenThenCondition,
            additionalWhenThenCondition,
            elseCase = elseCase,
        )

        val actual = case(
            whenThenCondition,
            additionalWhenThenCondition,
            elseCase = elseCase,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support unaliased case expression function with expression and else`() {
        val whenThenCondition = whenThen(someBooleanField(), someNumberField())
        val expression = someStringField()
        val elseCase = someStringField()
        val expected = UnaliasedCaseExpression(
            expression = expression,
            whenThenCondition = whenThenCondition,
            elseCase = elseCase,
        )

        val actual = case(
            expression = expression,
            whenThenCondition = whenThenCondition,
            elseCase = elseCase,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support aliased case expression function`() {
        val whenThenCondition = whenThen(someBooleanField(), someNumberField())
        val alias = "alias"
        val expected = AliasedCaseExpression(
            alias,
            whenThenCondition = whenThenCondition,
        )

        val actual = case(whenThenCondition).alias(alias)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
