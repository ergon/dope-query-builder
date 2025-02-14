package ch.ergon.dope.extensions

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.useKeys
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.UseKeysClass.Companion.UseKeys
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support single use keys with CM`() {
        val useKeys = someCMStringField()
        val bucket = someBucket()
        val expected = UseKeys(useKeys.toDopeType(), bucket)

        val actual = bucket.useKeys(useKeys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support single use keys with CM on aliased bucket`() {
        val useKeys = someCMStringField()
        val bucket = someBucket().alias("asdf")
        val expected = UseKeys(useKeys.toDopeType(), bucket)

        val actual = bucket.useKeys(useKeys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support single multiple keys with CM`() {
        val useKeys = someCMStringList()
        val bucket = someBucket()
        val expected = UseKeys(useKeys.toDopeType(), bucket)

        val actual = bucket.useKeys(useKeys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
