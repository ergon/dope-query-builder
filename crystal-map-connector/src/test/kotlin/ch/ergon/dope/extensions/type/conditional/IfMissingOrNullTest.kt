package ch.ergon.dope.extensions.type.conditional

import ch.ergon.dope.extension.type.conditional.coalesce
import ch.ergon.dope.extension.type.conditional.ifMissingOrNull
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.CoalesceExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.IfMissingOrNullExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IfMissingOrNullTest {
    @Test
    fun `should support if missing or null with CMNumberField CMNumberField`() {
        val firstExpression = someCMNumberField()
        val secondExpression = someCMNumberField()
        val expected = IfMissingOrNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissingOrNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMNumberField CMNumberField CMNumberField`() {
        val firstExpression = someCMNumberField()
        val secondExpression = someCMNumberField()
        val additionalExpression = someCMNumberField()
        val expected = IfMissingOrNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissingOrNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMStringField CMStringField`() {
        val firstExpression = someCMStringField()
        val secondExpression = someCMStringField()
        val expected = IfMissingOrNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissingOrNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMStringField CMStringField CMStringField`() {
        val firstExpression = someCMStringField()
        val secondExpression = someCMStringField()
        val additionalExpression = someCMStringField()
        val expected = IfMissingOrNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissingOrNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMBooleanField CMBooleanField`() {
        val firstExpression = someCMBooleanField()
        val secondExpression = someCMBooleanField()
        val expected = IfMissingOrNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissingOrNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMBooleanField CMBooleanField CMBooleanField`() {
        val firstExpression = someCMBooleanField()
        val secondExpression = someCMBooleanField()
        val additionalExpression = someCMBooleanField()
        val expected = IfMissingOrNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissingOrNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMNumberList CMNumberList`() {
        val firstExpression = someCMNumberList()
        val secondExpression = someCMNumberList()
        val expected = IfMissingOrNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissingOrNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMNumberList CMNumberList CMNumberList`() {
        val firstExpression = someCMNumberList()
        val secondExpression = someCMNumberList()
        val additionalExpression = someCMNumberList()
        val expected = IfMissingOrNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissingOrNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMStringList CMStringList`() {
        val firstExpression = someCMStringList()
        val secondExpression = someCMStringList()
        val expected = IfMissingOrNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissingOrNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMStringList CMStringList CMStringList`() {
        val firstExpression = someCMStringList()
        val secondExpression = someCMStringList()
        val additionalExpression = someCMStringList()
        val expected = IfMissingOrNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissingOrNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMBooleanList CMBooleanList`() {
        val firstExpression = someCMBooleanList()
        val secondExpression = someCMBooleanList()
        val expected = IfMissingOrNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissingOrNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support if missing or null with CMBooleanList CMBooleanList CMBooleanList`() {
        val firstExpression = someCMBooleanList()
        val secondExpression = someCMBooleanList()
        val additionalExpression = someCMBooleanList()
        val expected = IfMissingOrNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissingOrNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMNumberField CMNumberField`() {
        val firstExpression = someCMNumberField()
        val secondExpression = someCMNumberField()
        val expected = CoalesceExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = coalesce(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMNumberField CMNumberField CMNumberField`() {
        val firstExpression = someCMNumberField()
        val secondExpression = someCMNumberField()
        val additionalExpression = someCMNumberField()
        val expected = CoalesceExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = coalesce(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMStringField CMStringField`() {
        val firstExpression = someCMStringField()
        val secondExpression = someCMStringField()
        val expected = CoalesceExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = coalesce(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMStringField CMStringField CMStringField`() {
        val firstExpression = someCMStringField()
        val secondExpression = someCMStringField()
        val additionalExpression = someCMStringField()
        val expected = CoalesceExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = coalesce(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMBooleanField CMBooleanField`() {
        val firstExpression = someCMBooleanField()
        val secondExpression = someCMBooleanField()
        val expected = CoalesceExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = coalesce(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMBooleanField CMBooleanField CMBooleanField`() {
        val firstExpression = someCMBooleanField()
        val secondExpression = someCMBooleanField()
        val additionalExpression = someCMBooleanField()
        val expected = CoalesceExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = coalesce(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMNumberList CMNumberList`() {
        val firstExpression = someCMNumberList()
        val secondExpression = someCMNumberList()
        val expected = CoalesceExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = coalesce(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMNumberList CMNumberList CMNumberList`() {
        val firstExpression = someCMNumberList()
        val secondExpression = someCMNumberList()
        val additionalExpression = someCMNumberList()
        val expected = CoalesceExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = coalesce(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMStringList CMStringList`() {
        val firstExpression = someCMStringList()
        val secondExpression = someCMStringList()
        val expected = CoalesceExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = coalesce(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMStringList CMStringList CMStringList`() {
        val firstExpression = someCMStringList()
        val secondExpression = someCMStringList()
        val additionalExpression = someCMStringList()
        val expected = CoalesceExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = coalesce(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMBooleanList CMBooleanList`() {
        val firstExpression = someCMBooleanList()
        val secondExpression = someCMBooleanList()
        val expected = CoalesceExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = coalesce(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support coalesce with CMBooleanList CMBooleanList CMBooleanList`() {
        val firstExpression = someCMBooleanList()
        val secondExpression = someCMBooleanList()
        val additionalExpression = someCMBooleanList()
        val expected = CoalesceExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = coalesce(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}