package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolvable.expression.type.meta
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.bucket.useKeys
import ch.ergon.dope.resolvable.clause.model.toNewValue
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateTest {
    @Test
    fun `should support simple update clause`() {
        val expected = "UPDATE `someBucket`"

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with set`() {
        val expected = "UPDATE `someBucket` SET META().`expiration` = 10, `stringField` = \"test\""

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).set(
                meta().expiration.toNewValue(10.toDopeType()),
                someStringField().toNewValue("test".toDopeType()),
            )
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with unset`() {
        val expected = "UPDATE `someBucket` UNSET `stringField`"

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).unset(
                someStringField(),
            )
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with where`() {
        val expected = "UPDATE `someBucket` WHERE (1 + 2) = 3"

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).where(
                1.toDopeType().add(2).isEqualTo(3),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with limit`() {
        val expected = "UPDATE `someBucket` LIMIT 1"

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).limit(
                1.toDopeType(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with returning`() {
        val expected = "UPDATE `someBucket` RETURNING `stringField`"

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).returning(
                someStringField(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with returning asterisk`() {
        val expected = "UPDATE `someBucket` RETURNING *"

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).returningAsterisk().build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with returning asterisk and bucket`() {
        val expected = "UPDATE `someBucket` RETURNING `someBucket`.*"

        val bucket = someBucket()
        val actual = QueryBuilder
            .update(
                bucket,
            ).returningAsterisk(bucket).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with returning raw`() {
        val expected = "UPDATE `someBucket` RETURNING RAW `stringField`"

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).returningRaw(
                someStringField(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with returning value`() {
        val expected = "UPDATE `someBucket` RETURNING VALUE `stringField`"

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).returningValue(
                someStringField(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with returning element`() {
        val expected = "UPDATE `someBucket` RETURNING ELEMENT `stringField`"

        val actual = QueryBuilder
            .update(
                someBucket(),
            ).returningElement(
                someStringField(),
            ).build(CouchbaseResolver()).queryString

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

        val actual = QueryBuilder
            .update(
                bucket.useKeys("keyString"),
            ).set(
                setThisNumberField.toNewValue(1.toDopeType()),
                meta(bucket).expiration.toNewValue(3600.toDopeType()),
                someStringField().toNewValue(NULL),
            ).unset(
                someStringField("unsetThisStringField"),
            ).where(
                someBooleanField().isEqualTo(false),
            ).limit(
                1.toDopeType(),
            ).returning(
                setThisNumberField,
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }
}
