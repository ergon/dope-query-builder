package ch.ergon.dope.extensions.expression.single.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.single.type.relational.isLike
import ch.ergon.dope.extension.expression.single.type.relational.isNotLike
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.relational.LikeExpression
import ch.ergon.dope.resolvable.expression.single.type.relational.NotLikeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LikeTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support extensions for is like with type cmString`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = LikeExpression(left, right.toDopeType())

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support extensions for is like with string cmString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = LikeExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support extensions for is like with cmString string`() {
        val left = someCMStringField()
        val right = someString()
        val expected = LikeExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support extensions for is like with cmString cmString`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = LikeExpression(left.toDopeType(), right)

        val actual = left.isLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support extensions for is not like with type cmString`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = NotLikeExpression(left, right.toDopeType())

        val actual = left.isNotLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support extensions for is not like with string cmString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = NotLikeExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support extensions for is not like with cmString`() {
        val left = someCMStringField()
        val right = someString()
        val expected = NotLikeExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support extensions for is not like with cmString type`() {
        val someCMStringField = someCMStringField()
        val right = someStringField()
        val expected = NotLikeExpression(someCMStringField.toDopeType(), right)

        val actual = someCMStringField.isNotLike(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
