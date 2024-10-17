package ch.ergon.dope.operators.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBooleanSelectRawClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.NotWithinExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.notWithinArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotWithinExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support NOT WITHIN expression`() {
        val expected = DopeQuery(
            "`numberField` NOT WITHIN `numberArrayField`",
            emptyMap(),
        )
        val underTest = NotWithinExpression(someNumberField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT WITHIN expression with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "$1 NOT WITHIN `numberArrayField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = NotWithinExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT WITHIN expression with parameter as collection`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "`numberField` NOT WITHIN $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = NotWithinExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT WITHIN expression with parameter as value and collection`() {
        val parameterValue = 1
        val parameterCollectionValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "$1 NOT WITHIN $2",
            mapOf("$1" to parameterValue, "$2" to parameterCollectionValue),
        )
        val underTest = NotWithinExpression(parameterValue.asParameter(), parameterCollectionValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT WITHIN extension type type`() {
        val value = someNumberField()
        val collection = someNumberArrayField()
        val expected = NotWithinExpression(value, collection)

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT WITHIN extension number type`() {
        val value = 1
        val collection = someNumberArrayField()
        val expected = NotWithinExpression(value.toDopeType(), collection)

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT WITHIN extension string type`() {
        val value = "s"
        val collection = someStringArrayField()
        val expected = NotWithinExpression(value.toDopeType(), collection)

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT WITHIN extension boolean type`() {
        val value = true
        val collection = someBooleanArrayField()
        val expected = NotWithinExpression(value.toDopeType(), collection)

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT WITHIN extension type type collection`() {
        val value = someNumberField()
        val collection = listOf(someNumberField(), someNumberField())
        val expected = NotWithinExpression(value, collection.toDopeType())

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT WITHIN extension number type collection`() {
        val value = 1
        val collection = listOf(someNumberField(), someNumberField())
        val expected = NotWithinExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT WITHIN extension string type collection`() {
        val value = "s"
        val collection = listOf(someStringField(), someStringField())
        val expected = NotWithinExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT WITHIN extension boolean type collection`() {
        val value = true
        val collection = listOf(someBooleanField(), someBooleanField())
        val expected = NotWithinExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension type select`() {
        val value = someStringField()
        val collection = someSelectRawClause()
        val expected = NotWithinExpression(value, collection.asExpression())

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension number select`() {
        val value = 1
        val collection = someNumberSelectRawClause()
        val expected = NotWithinExpression(value.toDopeType(), collection.asExpression())

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension string select`() {
        val value = "s"
        val collection = someStringSelectRawClause()
        val expected = NotWithinExpression(value.toDopeType(), collection.asExpression())

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension boolean select`() {
        val value = true
        val collection = someBooleanSelectRawClause()
        val expected = NotWithinExpression(value.toDopeType(), collection.asExpression())

        val actual = value.notWithinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
