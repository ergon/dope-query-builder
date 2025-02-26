package ch.ergon.dope.extensions.expression.type.function.conditional

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.conditional.nvl
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMConverterBooleanField
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.resolvable.expression.type.function.conditional.NvlExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NvlTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support nvl CMNumberField CMNumberField`() {
        val initialExpression = someCMNumberField()
        val substituteExpression = someCMNumberField()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMStringField CMStringField`() {
        val initialExpression = someCMStringField()
        val substituteExpression = someCMStringField()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMBooleanField CMBooleanField`() {
        val initialExpression = someCMBooleanField()
        val substituteExpression = someCMBooleanField()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMNumberList CMNumberList`() {
        val initialExpression = someCMNumberList()
        val substituteExpression = someCMNumberList()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMStringList CMStringList`() {
        val initialExpression = someCMStringList()
        val substituteExpression = someCMStringList()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMBooleanList CMBooleanList`() {
        val initialExpression = someCMBooleanList()
        val substituteExpression = someCMBooleanList()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMNumberField number`() {
        val initialExpression = someCMNumberField()
        val substituteExpression = someNumber()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMStringField string`() {
        val initialExpression = someCMStringField()
        val substituteExpression = someString()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMBooleanField boolean`() {
        val initialExpression = someCMBooleanField()
        val substituteExpression = someBoolean()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMNumberField date`() {
        val initialExpression = someCMConverterNumberField()
        val substituteExpression = someDate()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toInstant().epochSecond.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMStringField date`() {
        val initialExpression = someCMConverterStringField()
        val substituteExpression = someDate()
        val expected = NvlExpression(initialExpression.toDopeType(), substituteExpression.toInstant().epochSecond.toString().toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl CMBooleanField date`() {
        val initialExpression = someCMConverterBooleanField()
        val substituteExpression = someDate()
        val expected = NvlExpression(initialExpression.toDopeType(), true.toDopeType())

        val actual = nvl(initialExpression, substituteExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
