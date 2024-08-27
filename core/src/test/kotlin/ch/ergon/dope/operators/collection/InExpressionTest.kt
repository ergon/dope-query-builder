package ch.ergon.dope.operators.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.InExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.inArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class InExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support IN expression`() {
        val expected = DopeQuery(
            "`numberField` IN `numberArrayField`",
            emptyMap(),
        )
        val underTest = InExpression(someNumberField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN expression with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "$1 IN `numberArrayField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = InExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN expression with parameter as collection`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "`numberField` IN $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = InExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN expression with parameter as value and collection`() {
        val parameterValue = 1
        val parameterCollectionValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "$1 IN $2",
            mapOf("$1" to parameterValue, "$2" to parameterCollectionValue),
        )
        val underTest = InExpression(parameterValue.asParameter(), parameterCollectionValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN extension type type`() {
        val value = someNumberField()
        val collection = someNumberArrayField()
        val expected = InExpression(value, collection)

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension number type`() {
        val value = 1
        val collection = someNumberArrayField()
        val expected = InExpression(value.toDopeType(), collection)

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension string type`() {
        val value = "s"
        val collection = someStringArrayField()
        val expected = InExpression(value.toDopeType(), collection)

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension boolean type`() {
        val value = true
        val collection = someBooleanArrayField()
        val expected = InExpression(value.toDopeType(), collection)

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension type type collection`() {
        val value = someNumberField()
        val collection = listOf(someNumberField(), someNumberField())
        val expected = InExpression(value, collection.toDopeType())

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension number type collection`() {
        val value = 1
        val collection = listOf(someNumberField(), someNumberField())
        val expected = InExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension string type collection`() {
        val value = "s"
        val collection = listOf(someStringField(), someStringField())
        val expected = InExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension boolean type collection`() {
        val value = true
        val collection = listOf(someBooleanField(), someBooleanField())
        val expected = InExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
