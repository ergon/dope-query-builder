package ch.ergon.dope.extensions.clause

import ch.ergon.dope.extension.clause.limit
import ch.ergon.dope.extension.clause.offset
import ch.ergon.dope.extension.clause.returning
import ch.ergon.dope.extension.clause.where
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someDelete
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class DeleteClauseTest {
    @Test
    fun `should support delete where with CM`() {
        val actual: String = someDelete().where(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("DELETE FROM `someBucket` WHERE `someBooleanField`", actual)
    }

    @Test
    fun `should support delete limit with CM`() {
        val actual: String = someDelete().limit(someCMNumberField()).toDopeQuery().queryString

        assertEquals("DELETE FROM `someBucket` LIMIT `someNumberField`", actual)
    }

    @Test
    fun `should support delete offset with CM`() {
        val actual: String = someDelete().offset(someCMNumberField()).toDopeQuery().queryString

        assertEquals("DELETE FROM `someBucket` OFFSET `someNumberField`", actual)
    }

    @Test
    fun `should support delete returning with CM`() {
        val actual: String = someDelete().returning(someCMNumberField()).toDopeQuery().queryString

        assertEquals("DELETE FROM `someBucket` RETURNING `someNumberField`", actual)
    }

    @Test
    fun `should support delete returning with multiple CM`() {
        val actual: String = someDelete().returning(someCMNumberField(), someCMStringList(), someCMBooleanField()).toDopeQuery().queryString

        assertEquals("DELETE FROM `someBucket` RETURNING `someNumberField`, `someStringList`, `someBooleanField`", actual)
    }
}
