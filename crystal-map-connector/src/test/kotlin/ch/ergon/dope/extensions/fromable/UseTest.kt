package ch.ergon.dope.extensions.fromable

import ch.ergon.dope.extension.fromable.useKeys
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.fromable.UseKeysClass.Companion.UseKeys
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UseTest {
    @Test
    fun `should support single use keys with CM`() {
        val useKeys = someCMStringField()
        val bucket = someBucket()
        val expected = UseKeys(useKeys.toDopeType(), bucket)

        val actual = bucket.useKeys(useKeys)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support single use keys with CM on aliased bucket`() {
        val useKeys = someCMStringField()
        val bucket = someBucket().alias("asdf")
        val expected = UseKeys(useKeys.toDopeType(), bucket)

        val actual = bucket.useKeys(useKeys)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support single multiple keys with CM`() {
        val useKeys = someCMStringList()
        val bucket = someBucket()
        val expected = UseKeys(useKeys.toDopeType(), bucket)

        val actual = bucket.useKeys(useKeys)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
