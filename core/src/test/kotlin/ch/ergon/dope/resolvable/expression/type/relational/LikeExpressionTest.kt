package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LikeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support like with no parameters`() {
        val expected = DopeQuery(
            queryString = "`stringField` LIKE `stringField`",
        )
        val underTest = LikeExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "`stringField` LIKE $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LikeExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "`stringField` LIKE \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LikeExpression(someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like function with type`() {
        val left = someStringField("left")
        val right = someStringField("right")
        val expected = LikeExpression(left, right)

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support like function with string`() {
        val left = someStringField("left")
        val right = someString("right")
        val expected = LikeExpression(left, right.toDopeType())

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support like function with string type`() {
        val left = someString("left")
        val right = someStringField("right")
        val expected = LikeExpression(left.toDopeType(), right)

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support like function with string string`() {
        val left = someString("left")
        val right = someString("right")
        val expected = LikeExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
