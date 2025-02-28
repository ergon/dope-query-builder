package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.helper.someSelectClause
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
    fun `should support asterisk with aliased select clause`() {
        val expected = DopeQuery(
            queryString = "`selectClause`.*",
        )
        val underTest = Asterisk(someSelectClause().alias("selectClause"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk with object field`() {
        val expected = DopeQuery(
            queryString = "`objectField`.*",
        )
        val underTest = Asterisk(someObjectField())

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

    @Test
    fun `should support asterisk function with aliased select clause`() {
        val expected = DopeQuery(
            queryString = "`selectClause`.*",
        )
        val underTest = asterisk(someSelectClause().alias("selectClause"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk function with object field`() {
        val expected = DopeQuery(
            queryString = "`objectField`.*",
        )
        val underTest = asterisk(someObjectField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk function receiver extension with bucket`() {
        val expected = DopeQuery(
            queryString = "`someBucket`.*",
        )
        val underTest = someBucket().asterisk()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk function receiver extension with aliased select clause`() {
        val expected = DopeQuery(
            queryString = "`selectClause`.*",
        )
        val underTest = someSelectClause().alias("selectClause").asterisk()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk function receiver extension with object field`() {
        val expected = DopeQuery(
            queryString = "`objectField`.*",
        )
        val underTest = someObjectField().asterisk()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
