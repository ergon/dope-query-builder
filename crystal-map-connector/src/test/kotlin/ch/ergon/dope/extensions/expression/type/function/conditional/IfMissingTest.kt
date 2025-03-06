package ch.ergon.dope.extensions.expression.type.function.conditional

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.conditional.ifMissing
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someCMObjectList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.type.function.conditional.IfMissingExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IfMissingTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support if missing with CMNumberField CMNumberField`() {
        val firstExpression = someCMNumberField()
        val secondExpression = someCMNumberField()
        val expected = IfMissingExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMNumberField CMNumberField CMNumberField`() {
        val firstExpression = someCMNumberField()
        val secondExpression = someCMNumberField()
        val additionalExpression = someCMNumberField()
        val expected = IfMissingExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissing(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMStringField CMStringField`() {
        val firstExpression = someCMStringField()
        val secondExpression = someCMStringField()
        val expected = IfMissingExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMStringField CMStringField CMStringField`() {
        val firstExpression = someCMStringField()
        val secondExpression = someCMStringField()
        val additionalExpression = someCMStringField()
        val expected = IfMissingExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissing(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMBooleanField CMBooleanField`() {
        val firstExpression = someCMBooleanField()
        val secondExpression = someCMBooleanField()
        val expected = IfMissingExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMBooleanField CMBooleanField CMBooleanField`() {
        val firstExpression = someCMBooleanField()
        val secondExpression = someCMBooleanField()
        val additionalExpression = someCMBooleanField()
        val expected = IfMissingExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissing(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMObjectField CMObjectField`() {
        val firstExpression = someCMObjectField()
        val secondExpression = someCMObjectField()
        val expected = IfMissingExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMObjectField CMObjectField CMObjectField`() {
        val firstExpression = someCMObjectField()
        val secondExpression = someCMObjectField()
        val additionalExpression = someCMObjectField()
        val expected = IfMissingExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissing(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMNumberList CMNumberList`() {
        val firstExpression = someCMNumberList()
        val secondExpression = someCMNumberList()
        val expected = IfMissingExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMNumberList CMNumberList CMNumberList`() {
        val firstExpression = someCMNumberList()
        val secondExpression = someCMNumberList()
        val additionalExpression = someCMNumberList()
        val expected = IfMissingExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissing(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMStringList CMStringList`() {
        val firstExpression = someCMStringList()
        val secondExpression = someCMStringList()
        val expected = IfMissingExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMStringList CMStringList CMStringList`() {
        val firstExpression = someCMStringList()
        val secondExpression = someCMStringList()
        val additionalExpression = someCMStringList()
        val expected = IfMissingExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissing(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMBooleanList CMBooleanList`() {
        val firstExpression = someCMBooleanList()
        val secondExpression = someCMBooleanList()
        val expected = IfMissingExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMBooleanList CMBooleanList CMBooleanList`() {
        val firstExpression = someCMBooleanList()
        val secondExpression = someCMBooleanList()
        val additionalExpression = someCMBooleanList()
        val expected = IfMissingExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissing(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMObjectList CMObjectList`() {
        val firstExpression = someCMObjectList()
        val secondExpression = someCMObjectList()
        val expected = IfMissingExpression(firstExpression.toDopeType(), secondExpression.toDopeType())

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support if missing with CMObjectList CMObjectList CMObjectList`() {
        val firstExpression = someCMObjectList()
        val secondExpression = someCMObjectList()
        val additionalExpression = someCMObjectList()
        val expected = IfMissingExpression(
            firstExpression.toDopeType(),
            secondExpression.toDopeType(),
            additionalExpression.toDopeType(),
        )

        val actual = ifMissing(firstExpression, secondExpression, additionalExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
