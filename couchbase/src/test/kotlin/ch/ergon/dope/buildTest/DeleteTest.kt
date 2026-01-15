package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteTest {
    @Test
    fun `should support delete from`() {
        val expected = "DELETE FROM `someBucket`"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with where`() {
        val expected = "DELETE FROM `someBucket` WHERE TRUE"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .where(TRUE)
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with limit`() {
        val expected = "DELETE FROM `someBucket` LIMIT 10"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .limit(10.toDopeType())
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with offset`() {
        val expected = "DELETE FROM `someBucket` AS `b` OFFSET 10"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket().alias("b"))
            .offset(10.toDopeType())
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with returning`() {
        val expected = "DELETE FROM `someBucket` RETURNING `stringField`"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .returning(someStringField())
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with multiple returning`() {
        val expected = "DELETE FROM `someBucket` RETURNING `stringField`, `numberField`"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .returning(someStringField(), someNumberField())
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with returning asterisk`() {
        val expected = "DELETE FROM `someBucket` RETURNING *"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .returningAsterisk()
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with returning asterisk and bucket`() {
        val expected = "DELETE FROM `someBucket` RETURNING `someBucket`.*"

        val bucket = someBucket()
        val actual: String = QueryBuilder
            .deleteFrom(bucket)
            .returningAsterisk(bucket)
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with returning raw`() {
        val expected = "DELETE FROM `someBucket` RETURNING RAW `stringField`"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .returningRaw(someStringField())
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with returning value`() {
        val expected = "DELETE FROM `someBucket` RETURNING VALUE `stringField`"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .returningValue(someStringField())
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with returning element`() {
        val expected = "DELETE FROM `someBucket` RETURNING ELEMENT `stringField`"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .returningElement(someStringField())
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete`() {
        val expected = "DELETE FROM `someBucket` WHERE (`someBucket`.`age` = 2 AND TRUE) LIMIT 7 OFFSET 10 RETURNING `stringField`"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .where(someNumberField("age", someBucket()).isEqualTo(2).and(TRUE))
            .limit(7.toDopeType())
            .offset(10.toDopeType())
            .returning(someStringField())
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }
}
