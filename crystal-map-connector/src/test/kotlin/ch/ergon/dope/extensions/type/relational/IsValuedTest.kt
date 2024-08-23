package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.relational.isNotValued
import ch.ergon.dope.extension.type.relational.isValued
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IsNotValuedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IsValuedExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IsValuedTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support  Valued CMFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsValuedExpression(field.toDopeType())

        val actual = field.isValued()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support  Valued CMFieldString`() {
        val field = someCMStringField()
        val expected = IsValuedExpression(field.toDopeType())

        val actual = field.isValued()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support  Valued CMFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsValuedExpression(field.toDopeType())

        val actual = field.isValued()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Valued CMFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsNotValuedExpression(field.toDopeType())

        val actual = field.isNotValued()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Valued CMFieldString`() {
        val field = someCMStringField()
        val expected = IsNotValuedExpression(field.toDopeType())

        val actual = field.isNotValued()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Valued CMFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsNotValuedExpression(field.toDopeType())

        val actual = field.isNotValued()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
