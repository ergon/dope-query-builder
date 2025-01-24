package ch.ergon.dope.resolvable.expression.unaliased.type.access

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectRawClause
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
            queryString = "`stringArrayField`[`numberField`]",
        )
        val underTest = ArrayAccess(someStringArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with positional parameter`() {
        val parameterValue = listOf("value")
        val expected = DopeQuery(
            queryString = "$1[`numberField`]",
            DopeParameters(positionalParameters = listOf(parameterValue)),
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
            queryString = "\$$parameterName[`numberField`]",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
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
            queryString = "$1[$2]",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = ArrayAccess(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with named all parameters`() {
        val parameterValue = listOf("value")
        val parameterValue2 = 4
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "\$$parameterName[\$$parameterName2]",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = ArrayAccess(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array access with positional second parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            queryString = "`stringArrayField`[$1]",
            DopeParameters(positionalParameters = listOf(parameterValue)),
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
            queryString = "`stringArrayField`[\$$parameterName]",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
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
            queryString = "\$$parameterName[$1]",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue), positionalParameters = listOf(parameterValue2)),
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

    @Test
    fun `should support array access select type`() {
        val selectClause = someSelectRawClause()
        val index = someNumberField()
        val expected = ArrayAccess(selectClause.asExpression(), index)

        val actual = selectClause.get(index)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array access select number`() {
        val selectClause = someSelectRawClause()
        val index = 1
        val expected = ArrayAccess(selectClause.asExpression(), index.toDopeType())

        val actual = selectClause.get(index)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
