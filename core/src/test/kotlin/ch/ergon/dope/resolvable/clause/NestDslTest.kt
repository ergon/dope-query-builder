package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeysClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class NestDslTest {
    private fun parentClause(): ISelectFromClause<ObjectType> =
        QueryBuilder.selectAsterisk().from(UnaliasedBucket("parent"))

    @Test
    fun `should support standard nest with condition`() {
        val nestable = UnaliasedBucket("nest")
        val parentClause = parentClause()
        val expected = StandardNestOnConditionClause(nestable, condition = TRUE, parentClause = parentClause)

        val actual = parentClause.nest(nestable, TRUE)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with keys`() {
        val nestable = UnaliasedBucket("nest")
        val keys = Field<ArrayType<StringType>>("keys", "")
        val parentClause = parentClause()
        val expected = StandardNestOnKeysClause(nestable, keys = keys, parentClause = parentClause)

        val actual = parentClause.nest(nestable, keys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with keys collection`() {
        val nestable = UnaliasedBucket("nest")
        val keys = listOf("key1", "key2")
        val parentClause = parentClause()
        val expected = StandardNestOnKeysClause(nestable, keys = keys.toDopeType(), parentClause = parentClause)

        val actual = parentClause.nest(nestable, keys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with key`() {
        val nestable = UnaliasedBucket("nest")
        val key = Field<StringType>("key", "")
        val parentClause = parentClause()
        val expected = StandardNestOnKeyClause(nestable, key = key, bucket = null, parentClause = parentClause)

        val actual = parentClause.nest(nestable, key = key)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with key string`() {
        val nestable = UnaliasedBucket("nest")
        val parentClause = parentClause()
        val expected = StandardNestOnKeyClause(nestable, key = "key".toDopeType(), bucket = null, parentClause = parentClause)

        val actual = parentClause.nest(nestable, key = "key")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with key for bucket`() {
        val nestable = UnaliasedBucket("nest")
        val bucket = UnaliasedBucket("bucket")
        val key = Field<StringType>("key", "")
        val parentClause = parentClause()
        val expected = StandardNestOnKeyClause(nestable, key = key, bucket = bucket, parentClause = parentClause)

        val actual = parentClause.nest(nestable, key = key, bucket = bucket)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with condition`() {
        val nestable = UnaliasedBucket("nest")
        val parentClause = parentClause()
        val expected = InnerNestOnConditionClause(nestable, condition = TRUE, parentClause = parentClause)

        val actual = parentClause.innerNest(nestable, TRUE)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with keys`() {
        val nestable = UnaliasedBucket("nest")
        val keys = Field<ArrayType<StringType>>("keys", "")
        val parentClause = parentClause()
        val expected = InnerNestOnKeysClause(nestable, keys = keys, parentClause = parentClause)

        val actual = parentClause.innerNest(nestable, keys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with keys collection`() {
        val nestable = UnaliasedBucket("nest")
        val keys = listOf("key1", "key2")
        val parentClause = parentClause()
        val expected = InnerNestOnKeysClause(nestable, keys = keys.toDopeType(), parentClause = parentClause)

        val actual = parentClause.innerNest(nestable, keys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with key`() {
        val nestable = UnaliasedBucket("nest")
        val key = Field<StringType>("key", "")
        val parentClause = parentClause()
        val expected = InnerNestOnKeyClause(nestable, key = key, bucket = null, parentClause = parentClause)

        val actual = parentClause.innerNest(nestable, key = key)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with key string`() {
        val nestable = UnaliasedBucket("nest")
        val parentClause = parentClause()
        val expected = InnerNestOnKeyClause(nestable, key = "key".toDopeType(), bucket = null, parentClause = parentClause)

        val actual = parentClause.innerNest(nestable, key = "key")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with key for bucket`() {
        val nestable = UnaliasedBucket("nest")
        val bucket = UnaliasedBucket("bucket")
        val key = Field<StringType>("key", "")
        val parentClause = parentClause()
        val expected = InnerNestOnKeyClause(nestable, key = key, bucket = bucket, parentClause = parentClause)

        val actual = parentClause.innerNest(nestable, key = key, bucket = bucket)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with condition`() {
        val nestable = UnaliasedBucket("nest")
        val parentClause = parentClause()
        val expected = LeftNestOnConditionClause(nestable, condition = TRUE, parentClause = parentClause)

        val actual = parentClause.leftNest(nestable, TRUE)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with keys`() {
        val nestable = UnaliasedBucket("nest")
        val keys = Field<ArrayType<StringType>>("keys", "")
        val parentClause = parentClause()
        val expected = LeftNestOnKeysClause(nestable, keys = keys, parentClause = parentClause)

        val actual = parentClause.leftNest(nestable, keys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with keys collection`() {
        val nestable = UnaliasedBucket("nest")
        val keys = listOf("key1", "key2")
        val parentClause = parentClause()
        val expected = LeftNestOnKeysClause(nestable, keys = keys.toDopeType(), parentClause = parentClause)

        val actual = parentClause.leftNest(nestable, keys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with key`() {
        val nestable = UnaliasedBucket("nest")
        val key = Field<StringType>("key", "")
        val parentClause = parentClause()
        val expected = LeftNestOnKeyClause(nestable, key = key, bucket = null, parentClause = parentClause)

        val actual = parentClause.leftNest(nestable, key = key)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with key string`() {
        val nestable = UnaliasedBucket("nest")
        val parentClause = parentClause()
        val expected = LeftNestOnKeyClause(nestable, key = "key".toDopeType(), bucket = null, parentClause = parentClause)

        val actual = parentClause.leftNest(nestable, key = "key")

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with key for bucket`() {
        val nestable = UnaliasedBucket("nest")
        val bucket = UnaliasedBucket("bucket")
        val key = Field<StringType>("key", "")
        val parentClause = parentClause()
        val expected = LeftNestOnKeyClause(nestable, key = key, bucket = bucket, parentClause = parentClause)

        val actual = parentClause.leftNest(nestable, key = key, bucket = bucket)

        assertEquals(expected, actual)
    }
}
