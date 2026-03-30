package ch.ergon.dope.extensions

import ch.ergon.dope.alias
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someCMObjectList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class AliasTest {
    @Test
    fun `should support alias on cm number field`() {
        val field = someCMNumberField()
        val expected = AliasedTypeExpression(field.toDopeType(), "a")

        val actual = field.alias("a")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on cm string field`() {
        val field = someCMStringField()
        val expected = AliasedTypeExpression(field.toDopeType(), "a")

        val actual = field.alias("a")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on cm boolean field`() {
        val field = someCMBooleanField()
        val expected = AliasedTypeExpression(field.toDopeType(), "a")

        val actual = field.alias("a")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on cm number list`() {
        val field = someCMNumberList()
        val expected = AliasedTypeExpression(field.toDopeType(), "a")

        val actual = field.alias("a")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on cm string list`() {
        val field = someCMStringList()
        val expected = AliasedTypeExpression(field.toDopeType(), "a")

        val actual = field.alias("a")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on cm boolean list`() {
        val field = someCMBooleanList()
        val expected = AliasedTypeExpression(field.toDopeType(), "a")

        val actual = field.alias("a")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on cm object field`() {
        val field = someCMObjectField()
        val expected = AliasedTypeExpression(field.toDopeType(), "a")

        val actual = field.alias("a")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on cm object list`() {
        val field = someCMObjectList()
        val expected = AliasedTypeExpression(field.toDopeType(), "a")

        val actual = field.alias("a")

        assertEquals(expected, actual)
    }
}
