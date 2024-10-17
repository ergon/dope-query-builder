package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanSelectRawClause
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayRemoveExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_REMOVE`() {
        val expected = DopeQuery(
            "ARRAY_REMOVE(`numberArrayField`, `numberField`)",
            emptyMap(),
        )
        val underTest = ArrayRemoveExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_REMOVE($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayRemoveExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_REMOVE(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayRemoveExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_REMOVE($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArrayRemoveExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE extension type type`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayRemoveExpression(array, value)

        val actual = arrayRemove(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension type string`() {
        val array = someStringArrayField()
        val value = someString()
        val expected = ArrayRemoveExpression(array, value.toDopeType())

        val actual = arrayRemove(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension type number`() {
        val array = someNumberArrayField()
        val value = someNumber()
        val expected = ArrayRemoveExpression(array, value.toDopeType())

        val actual = arrayRemove(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension type boolean`() {
        val array = someBooleanArrayField()
        val value = someBoolean()
        val expected = ArrayRemoveExpression(array, value.toDopeType())

        val actual = arrayRemove(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension select type`() {
        val array = someNumberSelectRawClause()
        val value = someNumberField()
        val expected = ArrayRemoveExpression(array.asExpression(), value)

        val actual = arrayRemove(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension select string`() {
        val array = someStringSelectRawClause()
        val value = someString()
        val expected = ArrayRemoveExpression(array.asExpression(), value.toDopeType())

        val actual = arrayRemove(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension select number`() {
        val array = someNumberSelectRawClause()
        val value = someNumber()
        val expected = ArrayRemoveExpression(array.asExpression(), value.toDopeType())

        val actual = arrayRemove(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension select boolean`() {
        val array = someBooleanSelectRawClause()
        val value = someBoolean()
        val expected = ArrayRemoveExpression(array.asExpression(), value.toDopeType())

        val actual = arrayRemove(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
