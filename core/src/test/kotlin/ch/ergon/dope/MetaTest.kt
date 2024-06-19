package ch.ergon.dope

import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.resolvable.expression.unaliased.type.meta.meta
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MetaTest {
    private lateinit var create: QueryBuilder

    @BeforeEach
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support meta expression with bucket`() {
        val expected = "SELECT META(`someBucket`) FROM `someBucket`"

        val actual: String = create
            .select(
                meta(someBucket()),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta expression without a bucket`() {
        val expected = "SELECT META() FROM `someBucket`"

        val actual: String = create
            .select(
                meta(),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta expression fields`() {
        val expected = "SELECT META().`cas`, META().`expiration`, META().`flags`, META().`id`, " +
            "META().`keyspace`, META().`type` FROM `someBucket`"

        val actual: String = create
            .select(
                meta().cas,
                meta().expiration,
                meta().flags,
                meta().id,
                meta().keyspace,
                meta().type,
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
