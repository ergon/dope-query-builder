package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.relational.isMissing
import ch.ergon.dope.extension.type.relational.isNotMissing
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IsMissingExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IsNotMissingExpression
import ch.ergon.dope.toDopeType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IsMissingTest {
    private lateinit var manager: DopeQueryManager

    @BeforeTest
    fun setup() {
        manager = DopeQueryManager()
    }

    @Test
    fun `should support isMissing CMFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isMissing CMFieldString`() {
        val field = someCMStringField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isMissing CMFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Missing CMFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsNotMissingExpression(field.toDopeType())

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Missing CMFieldString`() {
        val field = someCMStringField()
        val expected = IsNotMissingExpression(field.toDopeType())

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support is not Missing CMFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsNotMissingExpression(field.toDopeType())

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
