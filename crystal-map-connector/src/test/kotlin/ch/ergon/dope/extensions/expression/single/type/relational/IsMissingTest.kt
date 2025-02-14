package ch.ergon.dope.extensions.expression.single.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.single.type.relational.isMissing
import ch.ergon.dope.extension.expression.single.type.relational.isNotMissing
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.single.type.relational.IsMissingExpression
import ch.ergon.dope.resolvable.expression.single.type.relational.IsNotMissingExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IsMissingTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support isMissing CMJsonFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isMissing CMJsonFieldString`() {
        val field = someCMStringField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isMissing CMJsonFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isMissing CMJsonFieldObject`() {
        val field = someCMObjectField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Missing CMJsonFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsNotMissingExpression(field.toDopeType())

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Missing CMJsonFieldString`() {
        val field = someCMStringField()
        val expected = IsNotMissingExpression(field.toDopeType())

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Missing CMJsonFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsNotMissingExpression(field.toDopeType())

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Missing CMJsonFieldObject`() {
        val field = someCMObjectField()
        val expected = IsNotMissingExpression(field.toDopeType())

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
