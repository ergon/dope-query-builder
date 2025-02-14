package ch.ergon.dope.resolvable.expression.single.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import kotlin.test.Test
import kotlin.test.assertEquals

class MetaExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support meta`() {
        val expected = DopeQuery(
            queryString = "META(`someBucket`)",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field cas`() {
        val expected = DopeQuery(
            queryString = "META(`someBucket`).`cas`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.cas.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field expiration`() {
        val expected = DopeQuery(
            queryString = "META(`someBucket`).`expiration`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.expiration.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field flags`() {
        val expected = DopeQuery(
            queryString = "META(`someBucket`).`flags`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.flags.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field id`() {
        val expected = DopeQuery(
            queryString = "META(`someBucket`).`id`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.id.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field type`() {
        val expected = DopeQuery(
            queryString = "META(`someBucket`).`type`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.type.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field keyspace`() {
        val expected = DopeQuery(
            queryString = "META(`someBucket`).`keyspace`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.keyspace.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta function`() {
        val expected = MetaExpression(null)

        val actual = meta()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support meta function with bucket`() {
        val bucket = someBucket()
        val expected = MetaExpression(bucket)

        val actual = meta(bucket)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
