package ch.ergon.dope.resolvable.expression.unaliased.type.access

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayAccessTest : ParameterDependentTest {
    @Test
    fun `should support array access`() {
        val expected = DopeQuery(
            "`stringArrayField`[`numberField`]",
            emptyMap(),
        )
        val underTest = ArrayAccess(someStringArrayField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with parameter`() {
        val parameterValue = listOf("value")
        val expected = DopeQuery(
            "$1[`numberField`]",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayAccess(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with all parameters`() {
        val parameterValue = listOf("value")
        val parameterValue2 = 4
        val expected = DopeQuery(
            "$1[$2]",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayAccess(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with second parameters`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "`stringArrayField`[$1]",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayAccess(someStringArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access function type`() {
        val array = someStringArrayField()
        val index = someNumberField()
        val expected = ArrayAccess(array, index)

        val actual = array.get(index)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array access number`() {
        val array = someStringArrayField()
        val index = 1
        val expected = ArrayAccess(array, index.toDopeType())

        val actual = array.get(index)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
