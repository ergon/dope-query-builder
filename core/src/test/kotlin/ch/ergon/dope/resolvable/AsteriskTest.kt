package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someObject
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.expression.type.toDopeType
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
        val expected = Asterisk()

        val actual = asterisk()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support asterisk function with bucket`() {
        val bucket = someBucket()
        val expected = Asterisk(bucket)

        val actual = asterisk(bucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support asterisk function with aliased select clause`() {
        val selectClause = someSelectClause().alias("selectClause")
        val expected = Asterisk(selectClause)

        val actual = asterisk(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support asterisk function with object field`() {
        val objectField = someObjectField()
        val expected = Asterisk(objectField)

        val actual = asterisk(objectField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support asterisk function with object`() {
        val someObject = someObject()
        val expected = Asterisk(someObject.toDopeType())

        val actual = asterisk(someObject)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support asterisk function receiver extension with bucket`() {
        val bucket = someBucket()
        val expected = Asterisk(bucket)

        val actual = bucket.asterisk()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support asterisk function receiver extension with aliased select clause`() {
        val selectClause = someSelectClause().alias("selectClause")
        val expected = Asterisk(selectClause)

        val actual = selectClause.asterisk()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support asterisk function receiver extension with object field`() {
        val objectField = someObjectField()
        val expected = Asterisk(objectField)

        val actual = objectField.asterisk()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support asterisk function receiver extension with object`() {
        val someObject = someObject()
        val expected = Asterisk(someObject.toDopeType())

        val actual = someObject.asterisk()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
