package ch.ergon.dope.extensions.type.logical

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.logical.and
import ch.ergon.dope.extension.type.logical.or
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.AndExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.OrExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LogicalInfixTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support or with CMField CMField`() {
        val left = someCMBooleanField()
        val right = someCMBooleanField()
        val expected = OrExpression(left.toDopeType(), right.toDopeType())

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support or with CMField type`() {
        val left = someCMBooleanField()
        val right = someBooleanField()
        val expected = OrExpression(left.toDopeType(), right)

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support or with type CMField`() {
        val left = someBooleanField()
        val right = someCMBooleanField()
        val expected = OrExpression(left, right.toDopeType())

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support or with Boolean CMField`() {
        val left = someBoolean()
        val right = someCMBooleanField()
        val expected = OrExpression(left.toDopeType(), right.toDopeType())

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support or with CMField Boolean`() {
        val left = someCMBooleanField()
        val right = someBoolean()
        val expected = OrExpression(left.toDopeType(), right.toDopeType())

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support and with CMField CMField`() {
        val left = someCMBooleanField()
        val right = someCMBooleanField()
        val expected = AndExpression(left.toDopeType(), right.toDopeType())

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support and with CMField type`() {
        val left = someCMBooleanField()
        val right = someBooleanField()
        val expected = AndExpression(left.toDopeType(), right)

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support and with type CMField`() {
        val left = someBooleanField()
        val right = someCMBooleanField()
        val expected = AndExpression(left, right.toDopeType())

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support and with Boolean CMField`() {
        val left = someBoolean()
        val right = someCMBooleanField()
        val expected = AndExpression(left.toDopeType(), right.toDopeType())

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support and with CMField Boolean`() {
        val left = someCMBooleanField()
        val right = someBoolean()
        val expected = AndExpression(left.toDopeType(), right.toDopeType())

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
