package ch.ergon.dope.resolvable.expression.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
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
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayRemoveExpression
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRemove
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayRemoveExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_REMOVE`() {
        val expected = DopeQuery(
            queryString = "ARRAY_REMOVE(`numberArrayField`, `numberField`)",
        )
        val underTest = ArrayRemoveExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_REMOVE($1, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayRemoveExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE with positional parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "ARRAY_REMOVE(`numberArrayField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayRemoveExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE with all positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "ARRAY_REMOVE($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValueCollection, parameterValue)),
        )
        val underTest = ArrayRemoveExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_REMOVE(\$$parameterName, `numberField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayRemoveExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE with named parameter as value`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_REMOVE(`numberArrayField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayRemoveExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE with named and positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_REMOVE(\$$parameterName, $1)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValueCollection), positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayRemoveExpression(parameterValueCollection.asParameter(parameterName), parameterValue.asParameter())

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
        val selectClause = someNumberSelectRawClause()
        val value = someNumberField()
        val expected = ArrayRemoveExpression(selectClause.asExpression(), value)

        val actual = arrayRemove(selectClause, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension select string`() {
        val selectClause = someStringSelectRawClause()
        val value = someString()
        val expected = ArrayRemoveExpression(selectClause.asExpression(), value.toDopeType())

        val actual = arrayRemove(selectClause, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension select number`() {
        val selectClause = someNumberSelectRawClause()
        val value = someNumber()
        val expected = ArrayRemoveExpression(selectClause.asExpression(), value.toDopeType())

        val actual = arrayRemove(selectClause, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REMOVE extension select boolean`() {
        val selectClause = someBooleanSelectRawClause()
        val value = someBoolean()
        val expected = ArrayRemoveExpression(selectClause.asExpression(), value.toDopeType())

        val actual = arrayRemove(selectClause, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
