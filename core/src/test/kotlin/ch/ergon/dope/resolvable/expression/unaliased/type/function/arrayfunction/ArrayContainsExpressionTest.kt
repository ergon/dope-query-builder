package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanSelectRawClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayContainsExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_CONTAINS`() {
        val expected = DopeQuery(
            "ARRAY_CONTAINS(`numberArrayField`, `numberField`)",
            emptyMap(),
        )
        val underTest = ArrayContainsExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_CONTAINS($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayContainsExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_CONTAINS(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayContainsExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_CONTAINS($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArrayContainsExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS extension`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayContainsExpression(array, value)

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension number`() {
        val array = someNumberArrayField()
        val value = 1
        val expected = ArrayContainsExpression(array, value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension string`() {
        val array = someStringArrayField()
        val value = "s"
        val expected = ArrayContainsExpression(array, value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension boolean`() {
        val array = someBooleanArrayField()
        val value = true
        val expected = ArrayContainsExpression(array, value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension select`() {
        val array = someSelectRawClause()
        val value = someStringField()
        val expected = ArrayContainsExpression(array.asExpression(), value)

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension select number`() {
        val array = someNumberSelectRawClause()
        val value = 1
        val expected = ArrayContainsExpression(array.asExpression(), value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension select string`() {
        val array = someStringSelectRawClause()
        val value = "s"
        val expected = ArrayContainsExpression(array.asExpression(), value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension select boolean`() {
        val array = someBooleanSelectRawClause()
        val value = true
        val expected = ArrayContainsExpression(array.asExpression(), value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
