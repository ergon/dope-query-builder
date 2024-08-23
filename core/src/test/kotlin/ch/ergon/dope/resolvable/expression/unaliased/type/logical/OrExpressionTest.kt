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

class OrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support or`() {
        val expected = DopeQuery(
            "(`booleanField` OR `booleanField`)",
            emptyMap(),
            manager,
        )
        val underTest = OrExpression(someBooleanField(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "($1 OR `booleanField`)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = OrExpression(parameterValue.asParameter(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with all parameter`() {
        val parameterValue = true
        val parameterValue2 = true
        val expected = DopeQuery(
            "($1 OR $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = OrExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with second parameter`() {
        val parameterValue = false
        val expected = DopeQuery(
            "(`booleanField` OR $1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = OrExpression(someBooleanField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or function type type`() {
        val left = someBooleanField()
        val right = someBooleanField()
        val expected = OrExpression(left, right)

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support or function type boolean`() {
        val left = someBooleanField()
        val right = someBoolean()
        val expected = OrExpression(left, right.toDopeType())

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support or function boolean type`() {
        val left = someBoolean()
        val right = someBooleanField()
        val expected = OrExpression(left.toDopeType(), right)

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
