package ch.ergon.dope.extensions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.extension.select
import ch.ergon.dope.extension.selectDistinct
import ch.ergon.dope.extension.selectRaw
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringList
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class QueryBuilderTest {
    @Test
    fun `should support select with CM`() {
        val actual: String = QueryBuilder().select(someCMNumberField()).toDopeQuery().queryString

        assertEquals("SELECT someNumberField", actual)
    }

    @Test
    fun `should support select with multiple CM`() {
        val actual: String = QueryBuilder().select(someCMBooleanField(), someCMStringList()).toDopeQuery().queryString

        assertEquals("SELECT someBooleanField, someStringList", actual)
    }

    @Test
    fun `should support select distinct with CM`() {
        val actual: String = QueryBuilder().selectDistinct(someCMNumberField()).toDopeQuery().queryString

        assertEquals("SELECT DISTINCT someNumberField", actual)
    }

    @Test
    fun `should support select distinct with multiple CM`() {
        val actual: String = QueryBuilder().selectDistinct(someCMBooleanField(), someCMStringList()).toDopeQuery().queryString

        assertEquals("SELECT DISTINCT someBooleanField, someStringList", actual)
    }

    @Test
    fun `should support select raw with CM`() {
        val actual: String = QueryBuilder().selectRaw(someCMNumberField()).toDopeQuery().queryString

        assertEquals("SELECT RAW someNumberField", actual)
    }
}
