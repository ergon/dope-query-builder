package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support and with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "($1 AND `booleanField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = AndExpression(parameterValue.asParameter(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with all positional parameters`() {
        val parameterValue = true
        val parameterValue2 = true
        val expected = DopeQuery(
            "($1 AND $2)",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = AndExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with second positional parameter`() {
        val parameterValue = false
        val expected = DopeQuery(
            "(`booleanField` AND $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = AndExpression(someBooleanField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = DopeQuery(
            "($$parameterName AND `booleanField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = AndExpression(parameterValue.asParameter(parameterName), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with all named parameters`() {
        val parameterValue = true
        val parameterValue2 = false
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "($$parameterName1 AND $$parameterName2)",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = AndExpression(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not function`() {
        val field = someBooleanField()
        val expected = NotExpression(field)

        val actual = not(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not function boolean`() {
        val boolean = someBoolean()
        val expected = NotExpression(boolean.toDopeType())

        val actual = not(boolean)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
