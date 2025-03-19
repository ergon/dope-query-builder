package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.bucket.useKeys
import ch.ergon.dope.resolvable.clause.model.to
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.meta
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support simple update clause`() {
        val expected = "UPDATE `someBucket`"

        val actual = create
            .update(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with set`() {
        val expected = "UPDATE `someBucket` SET META().`expiration` = 10, `stringField` = \"test\""

        val actual = create
            .update(
                someBucket(),
            ).set(
                meta().expiration to 10.toDopeType(),
                someStringField() to "test".toDopeType(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with unset`() {
        val expected = "UPDATE `someBucket` UNSET `stringField`"

        val actual = create
            .update(
                someBucket(),
            ).unset(
                someStringField(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with where`() {
        val expected = "UPDATE `someBucket` WHERE (1 + 2) = 3"

        val actual = create
            .update(
                someBucket(),
            ).where(
                1.toDopeType().add(2).isEqualTo(3),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with limit`() {
        val expected = "UPDATE `someBucket` LIMIT 1"

        val actual = create
            .update(
                someBucket(),
            ).limit(
                1.toDopeType(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with returning`() {
        val expected = "UPDATE `someBucket` RETURNING `stringField`"

        val actual = create
            .update(
                someBucket(),
            ).returning(
                someStringField(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause as a complex query`() {
        val bucket = someBucket().alias("sb")
        val setThisNumberField = someNumberField("setThisNumberField")
        val expected = "UPDATE `someBucket` AS `sb` " +
            "USE KEYS \"keyString\" " +
            "SET `setThisNumberField` = 1, META(`sb`).`expiration` = 3600, `stringField` = NULL " +
            "UNSET `unsetThisStringField` " +
            "WHERE `booleanField` = FALSE " +
            "LIMIT 1 " +
            "RETURNING `setThisNumberField`"

        val actual = create
            .update(
                bucket.useKeys("keyString"),
            ).set(
                setThisNumberField to 1.toDopeType(),
                meta(someBucket().alias("sb")).expiration to 3600.toDopeType(),
                someStringField() to NULL,
            ).unset(
                someStringField("unsetThisStringField"),
            ).where(
                someBooleanField().isEqualTo(false),
            ).limit(
                1.toDopeType(),
            ).returning(
                setThisNumberField,
            ).build().queryString

        assertEquals(expected, actual)
    }
}
