package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotLikeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support not like with no parameters`() {
        val expected = DopeQuery(
            "`stringField` NOT LIKE `stringField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = NotLikeExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "`stringField` NOT LIKE $1",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = NotLikeExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            "`stringField` NOT LIKE \$$parameterName",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = NotLikeExpression(someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like function with type`() {
        val left = someStringField("left")
        val right = someStringField("right")
        val expected = NotLikeExpression(left, right)

        val actual = left.isNotLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not like function with string`() {
        val left = someStringField("left")
        val right = someString("right")
        val expected = NotLikeExpression(left, right.toDopeType())

        val actual = left.isNotLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
