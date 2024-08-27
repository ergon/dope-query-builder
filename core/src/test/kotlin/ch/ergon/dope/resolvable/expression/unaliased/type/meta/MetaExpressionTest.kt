package ch.ergon.dope.resolvable.expression.unaliased.type.meta

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
            "META(`someBucket`)",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field cas`() {
        val expected = DopeQuery(
            "META(`someBucket`).`cas`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.cas.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field expiration`() {
        val expected = DopeQuery(
            "META(`someBucket`).`expiration`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.expiration.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field flags`() {
        val expected = DopeQuery(
            "META(`someBucket`).`flags`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.flags.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field id`() {
        val expected = DopeQuery(
            "META(`someBucket`).`id`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.id.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field type`() {
        val expected = DopeQuery(
            "META(`someBucket`).`type`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.type.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field keyspace`() {
        val expected = DopeQuery(
            "META(`someBucket`).`keyspace`",
            emptyMap(),
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
