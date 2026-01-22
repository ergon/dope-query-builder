package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.couchbase.resolvable.expression.type.meta
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someKeyspace
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.toNewValue
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.resolvable.keyspace.useKeys
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateTest {
    @Test
    fun `should support simple update clause`() {
        val expected = "UPDATE `someBucket`"

        val actual = QueryBuilder
            .update(
                someKeyspace(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with set`() {
        val expected = "UPDATE `someBucket` SET META().`expiration` = 10, `stringField` = \"test\""

        val actual = QueryBuilder
            .update(
                someKeyspace(),
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
                someKeyspace(),
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
                someKeyspace(),
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
                someKeyspace(),
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
                someKeyspace(),
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
                someKeyspace(),
            ).returningAsterisk().build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with returning asterisk and bucket`() {
        val expected = "UPDATE `someBucket` RETURNING `someBucket`.*"

        val bucket = someKeyspace()
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
                someKeyspace(),
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
                someKeyspace(),
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
                someKeyspace(),
            ).returningElement(
                someStringField(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause as a complex query`() {
        val keyspace = someKeyspace().alias("sb")
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
                keyspace.useKeys("keyString"),
            ).set(
                setThisNumberField.toNewValue(1.toDopeType()),
                meta(keyspace).expiration.toNewValue(3600.toDopeType()),
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
