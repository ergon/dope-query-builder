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

        val actual = MetaExpression(someBucket()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field cas`() {
        val expected = DopeQuery(
            "META(`someBucket`).`cas`",
            emptyMap(),
        )

        val actual = MetaExpression(someBucket()).cas.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field expiration`() {
        val expected = DopeQuery(
            "META(`someBucket`).`expiration`",
            emptyMap(),
        )

        val actual = MetaExpression(someBucket()).expiration.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field flags`() {
        val expected = DopeQuery(
            "META(`someBucket`).`flags`",
            emptyMap(),
        )

        val actual = MetaExpression(someBucket()).flags.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field id`() {
        val expected = DopeQuery(
            "META(`someBucket`).`id`",
            emptyMap(),
        )

        val actual = MetaExpression(someBucket()).id.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field type`() {
        val expected = DopeQuery(
            "META(`someBucket`).`type`",
            emptyMap(),
        )

        val actual = MetaExpression(someBucket()).type.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field keyspace`() {
        val expected = DopeQuery(
            "META(`someBucket`).`keyspace`",
            emptyMap(),
        )

        val actual = MetaExpression(someBucket()).keyspace.toDopeQuery()

        assertEquals(expected, actual)
    }
}
