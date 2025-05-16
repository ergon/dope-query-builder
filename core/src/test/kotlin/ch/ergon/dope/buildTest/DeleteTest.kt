package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
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
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with where`() {
        val expected = "DELETE FROM `someBucket` WHERE TRUE"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .where(TRUE)
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with limit`() {
        val expected = "DELETE FROM `someBucket` LIMIT 10"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .limit(10.toDopeType())
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with offset`() {
        val expected = "DELETE FROM `someBucket` AS `b` OFFSET 10"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket().alias("b"))
            .offset(10.toDopeType())
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with returning`() {
        val expected = "DELETE FROM `someBucket` RETURNING `stringField`"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .returning(someStringField())
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with multiple returning`() {
        val expected = "DELETE FROM `someBucket` RETURNING `stringField`, `numberField`"

        val actual: String = QueryBuilder
            .deleteFrom(someBucket())
            .returning(someStringField(), someNumberField())
            .build().queryString

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
            .build().queryString

        assertEquals(expected, actual)
    }
}
