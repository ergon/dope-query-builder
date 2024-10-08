package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayPrependExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_PREPEND`() {
        val expected = DopeQuery(
            "ARRAY_PREPEND(`numberField`, `numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayPrependExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PREPEND with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_PREPEND(`numberField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayPrependExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PREPEND with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_PREPEND($1, `numberArrayField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayPrependExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PREPEND with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_PREPEND($2, $1)",
            mapOf("$2" to parameterValue, "$1" to parameterValueCollection),
        )
        val underTest = ArrayPrependExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PREPEND extension type type`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayPrependExpression(array, value)

        val actual = arrayPrepend(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_PREPEND extension type string`() {
        val array = someStringArrayField()
        val value = someString()
        val expected = ArrayPrependExpression(array, value.toDopeType())

        val actual = arrayPrepend(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_PREPEND extension type number`() {
        val array = someNumberArrayField()
        val value = someNumber()
        val expected = ArrayPrependExpression(array, value.toDopeType())

        val actual = arrayPrepend(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_PREPEND extension type boolean`() {
        val array = someBooleanArrayField()
        val value = someBoolean()
        val expected = ArrayPrependExpression(array, value.toDopeType())

        val actual = arrayPrepend(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
