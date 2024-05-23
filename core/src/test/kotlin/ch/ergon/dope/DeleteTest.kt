package ch.ergon.dope

import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive.Companion.TRUE
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class DeleteTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support delete from`() {
        val expected = "DELETE FROM `someBucket`"

        val actual: String = create
            .deleteFrom(someBucket())
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with where`() {
        val expected = "DELETE FROM `someBucket` WHERE TRUE"

        val actual: String = create
            .deleteFrom(someBucket())
            .where(TRUE)
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with limit`() {
        val expected = "DELETE FROM `someBucket` LIMIT 10"

        val actual: String = create
            .deleteFrom(someBucket())
            .limit(10.toNumberType())
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with offset`() {
        val expected = "DELETE FROM `someBucket` OFFSET 10"

        val actual: String = create
            .deleteFrom(someBucket())
            .offset(10.toNumberType())
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with returning`() {
        val expected = "DELETE FROM `someBucket` RETURNING `stringField`"

        val actual: String = create
            .deleteFrom(someBucket())
            .returning(someStringField())
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete from with multiple returning`() {
        val expected = "DELETE FROM `someBucket` RETURNING `stringField`, `numberField`"

        val actual: String = create
            .deleteFrom(someBucket())
            .returning(someStringField(), someNumberField())
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete`() {
        val expected = "DELETE FROM `someBucket` WHERE (`someBucket`.`age` = 2 AND TRUE) LIMIT 7 OFFSET 10 RETURNING `stringField`"

        val actual: String = create
            .deleteFrom(someBucket())
            .where(someNumberField("age", someBucket()).isEqualTo(2).and(TRUE))
            .limit(7.toNumberType())
            .offset(10.toNumberType())
            .returning(someStringField())
            .build().queryString

        assertEquals(expected, actual)
    }
}
