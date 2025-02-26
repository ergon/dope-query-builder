package ch.ergon.dope.extensions.expression.type.function.conditional

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.conditional.ifNull
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someCMObjectList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.type.function.conditional.IfNullExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IfNullTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support if null with CMNumberField CMNumberField`() {
        val firstExpression = someCMNumberField()
        val secondExpression = someCMNumberField()
        val expected = IfNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMNumberField CMNumberField CMNumberField`() {
        val firstExpression = someCMNumberField()
        val secondExpression = someCMNumberField()
        val additionalExpression = someCMNumberField()
        val expected = IfNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMStringField CMStringField`() {
        val firstExpression = someCMStringField()
        val secondExpression = someCMStringField()
        val expected = IfNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMStringField CMStringField CMStringField`() {
        val firstExpression = someCMStringField()
        val secondExpression = someCMStringField()
        val additionalExpression = someCMStringField()
        val expected = IfNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMBooleanField CMBooleanField`() {
        val firstExpression = someCMBooleanField()
        val secondExpression = someCMBooleanField()
        val expected = IfNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMBooleanField CMBooleanField CMBooleanField`() {
        val firstExpression = someCMBooleanField()
        val secondExpression = someCMBooleanField()
        val additionalExpression = someCMBooleanField()
        val expected = IfNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMObjectField CMObjectField`() {
        val firstExpression = someCMObjectField()
        val secondExpression = someCMObjectField()
        val expected = IfNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMObjectField CMObjectField CMObjectField`() {
        val firstExpression = someCMObjectField()
        val secondExpression = someCMObjectField()
        val additionalExpression = someCMObjectField()
        val expected = IfNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMNumberList CMNumberList`() {
        val firstExpression = someCMNumberList()
        val secondExpression = someCMNumberList()
        val expected = IfNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMNumberList CMNumberList CMNumberList`() {
        val firstExpression = someCMNumberList()
        val secondExpression = someCMNumberList()
        val additionalExpression = someCMNumberList()
        val expected = IfNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMStringList CMStringList`() {
        val firstExpression = someCMStringList()
        val secondExpression = someCMStringList()
        val expected = IfNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMStringList CMStringList CMStringList`() {
        val firstExpression = someCMStringList()
        val secondExpression = someCMStringList()
        val additionalExpression = someCMStringList()
        val expected = IfNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMBooleanList CMBooleanList`() {
        val firstExpression = someCMBooleanList()
        val secondExpression = someCMBooleanList()
        val expected = IfNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMBooleanList CMBooleanList CMBooleanList`() {
        val firstExpression = someCMBooleanList()
        val secondExpression = someCMBooleanList()
        val additionalExpression = someCMBooleanList()
        val expected = IfNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMObjectList CMObjectList`() {
        val firstExpression = someCMObjectList()
        val secondExpression = someCMObjectList()
        val expected = IfNullExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if null with CMObjectList CMObjectList CMObjectList`() {
        val firstExpression = someCMObjectList()
        val secondExpression = someCMObjectList()
        val additionalExpression = someCMObjectList()
        val expected = IfNullExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifNull(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
