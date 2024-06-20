package ch.ergon.dope

import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.toArrayType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class UseClauseTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support use keys clause with string`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS \"someId\""

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .useKeys(
                "someId".toStringType(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string field`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS `stringField`"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .useKeys(
                someStringField(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string array`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS [\"someId\", \"anotherId\"]"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .useKeys(
                listOf("someId".toStringType(), "anotherId".toStringType()).toArrayType(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string array field`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS `stringArrayField`"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .useKeys(
                someStringArrayField(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with where clause`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS \"someId\" WHERE `numberField` = 1"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .useKeys(
                "someId".toStringType(),
            )
            .where(
                someNumberField().isEqualTo(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string function`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS CONCAT(\"some\", \"Id\")"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .useKeys(
                concat("some", "Id"),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string`() {
        val expected = "DELETE FROM `someBucket` USE KEYS \"someId\""

        val actual: String = create
            .deleteFrom(
                someBucket(),
            )
            .useKeys(
                "someId".toStringType(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string field`() {
        val expected = "DELETE FROM `someBucket` USE KEYS `stringField`"

        val actual: String = create
            .deleteFrom(
                someBucket(),
            )
            .useKeys(
                someStringField(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string array`() {
        val expected = "DELETE FROM `someBucket` USE KEYS [\"someId\", \"anotherId\"]"

        val actual: String = create
            .deleteFrom(
                someBucket(),
            )
            .useKeys(
                listOf("someId".toStringType(), "anotherId".toStringType()).toArrayType(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string array field`() {
        val expected = "DELETE FROM `someBucket` USE KEYS `stringArrayField`"

        val actual: String = create
            .deleteFrom(
                someBucket(),
            )
            .useKeys(
                someStringArrayField(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with where clause`() {
        val expected = "DELETE FROM `someBucket` USE KEYS \"someId\" WHERE `numberField` = 1"

        val actual: String = create
            .deleteFrom(
                someBucket(),
            )
            .useKeys(
                "someId".toStringType(),
            )
            .where(
                someNumberField().isEqualTo(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string function`() {
        val expected = "DELETE FROM `someBucket` USE KEYS CONCAT(\"some\", \"Id\")"

        val actual: String = create
            .deleteFrom(
                someBucket(),
            )
            .useKeys(
                concat("some", "Id"),
            ).build().queryString

        assertEquals(expected, actual)
    }
}