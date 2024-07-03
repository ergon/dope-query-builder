package ch.ergon.dope.resolvable.expression.unaliased.type.meta

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MetaExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support meta`() {
        val expected = DopeQuery(
            "META(`someBucket`)",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field cas`() {
        val expected = DopeQuery(
            "META(`someBucket`).`cas`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.cas.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field expiration`() {
        val expected = DopeQuery(
            "META(`someBucket`).`expiration`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.expiration.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field flags`() {
        val expected = DopeQuery(
            "META(`someBucket`).`flags`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.flags.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field id`() {
        val expected = DopeQuery(
            "META(`someBucket`).`id`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.id.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field type`() {
        val expected = DopeQuery(
            "META(`someBucket`).`type`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.type.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field keyspace`() {
        val expected = DopeQuery(
            "META(`someBucket`).`keyspace`",
            emptyMap(),
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.keyspace.toDopeQuery()

        assertEquals(expected, actual)
    }
}
