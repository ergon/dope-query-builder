package ch.ergon.dope.extensions.type.arithmetic

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.arithmetic.add
import ch.ergon.dope.extension.type.arithmetic.div
import ch.ergon.dope.extension.type.arithmetic.mod
import ch.ergon.dope.extension.type.arithmetic.mul
import ch.ergon.dope.extension.type.arithmetic.sub
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.AdditionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.DivisionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.ModuloExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.MultiplicationExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.SubtractionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberInfixTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support add with CMJsonField CMJsonField`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = AdditionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support add with CMJsonField type`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = AdditionExpression(left.toDopeType(), right)

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support add with type CMJsonField`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = AdditionExpression(left, right.toDopeType())

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support add with Number CMJsonField`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = AdditionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support add with CMJsonField Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = AdditionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support sub with CMJsonField CMJsonField`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = SubtractionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support sub with CMJsonField type`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = SubtractionExpression(left.toDopeType(), right)

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support sub with type CMJsonField`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = SubtractionExpression(left, right.toDopeType())

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support sub with Number CMJsonField`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = SubtractionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support sub with CMJsonField Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = SubtractionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support sub with Date CMConverterNumber`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = SubtractionExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support mul with CMJsonField CMJsonField`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = MultiplicationExpression(left.toDopeType(), right.toDopeType())

        val actual = left.mul(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mul with CMJsonField type`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = MultiplicationExpression(left.toDopeType(), right)

        val actual = left.mul(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mul with type CMJsonField`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = MultiplicationExpression(left, right.toDopeType())

        val actual = left.mul(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mul with Number CMJsonField`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = MultiplicationExpression(left.toDopeType(), right.toDopeType())

        val actual = left.mul(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mul with CMJsonField Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = MultiplicationExpression(left.toDopeType(), right.toDopeType())

        val actual = left.mul(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mul with Date CMConverterNumber`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = MultiplicationExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.mul(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support div with CMJsonField CMJsonField`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = DivisionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support div with CMJsonField type`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = DivisionExpression(left.toDopeType(), right)

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support div with type CMJsonField`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = DivisionExpression(left, right.toDopeType())

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support div with Number CMJsonField`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = DivisionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support div with CMJsonField Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = DivisionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mod with CMJsonField CMJsonField`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = ModuloExpression(left.toDopeType(), right.toDopeType())

        val actual = left.mod(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mod with CMJsonField type`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = ModuloExpression(left.toDopeType(), right)

        val actual = left.mod(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mod with type CMJsonField`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = ModuloExpression(left, right.toDopeType())

        val actual = left.mod(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mod with Number CMJsonField`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = ModuloExpression(left.toDopeType(), right.toDopeType())

        val actual = left.mod(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mod with CMJsonField Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = ModuloExpression(left.toDopeType(), right.toDopeType())

        val actual = left.mod(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support mod with Date CMConverterNumber`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = ModuloExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.mod(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
