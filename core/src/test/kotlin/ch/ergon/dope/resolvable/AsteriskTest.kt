package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import kotlin.test.Test
import kotlin.test.assertEquals

class AsteriskTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support asterisk`() {
        val expected = DopeQuery(
            queryString = "*",
        )
        val underTest = Asterisk()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk with bucket`() {
        val expected = DopeQuery(
            queryString = "`someBucket`.*",
        )
        val underTest = Asterisk(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk function`() {
        val expected = DopeQuery(
            queryString = "*",
        )
        val underTest = asterisk()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk function with bucket`() {
        val expected = DopeQuery(
            queryString = "`someBucket`.*",
        )
        val underTest = asterisk(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
