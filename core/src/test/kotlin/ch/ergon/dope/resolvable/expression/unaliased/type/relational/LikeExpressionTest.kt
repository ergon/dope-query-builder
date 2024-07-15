package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LikeExpressionTest : ParameterDependentTest {
    @Test
    fun `should support like`() {
        val expected = DopeQuery(
            "`stringField` LIKE `stringField`",
            emptyMap(),
        )
        val underTest = LikeExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "`stringField` LIKE $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = LikeExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like function with type`() {
        val left = someStringField("left")
        val right = someStringField("right")
        val expected = LikeExpression(left, right)

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support like function with string`() {
        val left = someStringField("left")
        val right = someString("right")
        val expected = LikeExpression(left, right.toDopeType())

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
