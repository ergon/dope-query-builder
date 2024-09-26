package ch.ergon.dope.resolvable.expression.unaliased.type.access

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayAccessTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support array access`() {
        val expected = DopeQuery(
            "`stringArrayField`[`numberField`]",
            emptyMap(),
            emptyList(),
        )
        val underTest = ArrayAccess(someStringArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with positional parameter`() {
        val parameterValue = listOf("value")
        val expected = DopeQuery(
            "$1[`numberField`]",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayAccess(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with named parameter`() {
        val parameterValue = listOf("value")
        val parameterName = "param"
        val expected = DopeQuery(
            "\$$parameterName[`numberField`]",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayAccess(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with positional all parameters`() {
        val parameterValue = listOf("value")
        val parameterValue2 = 4
        val expected = DopeQuery(
            "$1[$2]",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = ArrayAccess(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with named all parameters`() {
        val parameterValue = listOf("value")
        val parameterValue2 = 4
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "\$$parameterName1[\$$parameterName2]",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = ArrayAccess(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with positional second parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "`stringArrayField`[$1]",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayAccess(someStringArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with named second parameter`() {
        val parameterValue = 4
        val parameterName = "param"
        val expected = DopeQuery(
            "`stringArrayField`[\$$parameterName]",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayAccess(someStringArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with mixed parameters`() {
        val parameterValue = listOf("value")
        val parameterValue2 = 4
        val parameterName = "param1"
        val expected = DopeQuery(
            "\$$parameterName[$1]",
            mapOf(parameterName to parameterValue),
            listOf(parameterValue2),
        )
        val underTest = ArrayAccess(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access function type`() {
        val array = someStringArrayField()
        val index = someNumberField()
        val expected = ArrayAccess(array, index)

        val actual = array.get(index)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array access number`() {
        val array = someStringArrayField()
        val index = 1
        val expected = ArrayAccess(array, index.toDopeType())

        val actual = array.get(index)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
