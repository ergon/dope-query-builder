package ch.ergon.dope.extensions.keyspace

import ch.ergon.dope.extension.keyspace.useKeys
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someKeySpace
import ch.ergon.dope.resolvable.keyspace.UseKeysClass.Companion.UseKeys
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UseTest {
    @Test
    fun `should support single use keys with CM`() {
        val useKeys = someCMStringField()
        val keyspace = someKeySpace()
        val expected = UseKeys(useKeys.toDopeType(), keyspace)

        val actual = keyspace.useKeys(useKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support single use keys with CM on aliased keyspace`() {
        val useKeys = someCMStringField()
        val keyspace = someKeySpace().alias("asdf")
        val expected = UseKeys(useKeys.toDopeType(), keyspace)

        val actual = keyspace.useKeys(useKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support single multiple keys with CM`() {
        val useKeys = someCMStringList()
        val keyspace = someKeySpace()
        val expected = UseKeys(useKeys.toDopeType(), keyspace)

        val actual = keyspace.useKeys(useKeys)

        assertEquals(expected, actual)
    }
}
