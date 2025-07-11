package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.bucket.useFtsIndex
import ch.ergon.dope.resolvable.bucket.useGsiIndex
import ch.ergon.dope.resolvable.bucket.useIndex
import ch.ergon.dope.resolvable.bucket.useKeys
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.HASH_BUILD
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.HASH_PROBE
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.NESTED_LOOP
import ch.ergon.dope.resolvable.clause.joinHint.indexHint
import ch.ergon.dope.resolvable.clause.joinHint.keysHint
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class UseTest {
    @Test
    fun `should support use keys clause with string`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS \"someId\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket().useKeys("someId"),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string field`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS `stringField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket().useKeys(someStringField()),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string array expression`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS [\"someId\", \"anotherId\"]"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket().useKeys(
                    listOf("someId".toDopeType(), "anotherId".toDopeType()).toDopeType(),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string array field`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS `stringArrayField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket().useKeys(
                    someStringArrayField(),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string array subquery`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS (SELECT RAW `stringField`)"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket().useKeys(
                    QueryBuilder.selectRaw(someStringField()),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string array`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS [\"someId1\", \"someId2\"]"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket().useKeys(listOf("someId1", "someId2")),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys clause with string function`() {
        val expected = "SELECT * FROM `someBucket` USE KEYS CONCAT(\"some\", \"Id\")"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket().useKeys(
                    concat("some", "Id"),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string`() {
        val expected = "DELETE FROM `someBucket` USE KEYS \"someId\""

        val actual: String = QueryBuilder
            .deleteFrom(
                someBucket().useKeys(
                    "someId",
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string field`() {
        val expected = "DELETE FROM `someBucket` USE KEYS `stringField`"

        val actual: String = QueryBuilder
            .deleteFrom(
                someBucket().useKeys(
                    someStringField(),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string array`() {
        val expected = "DELETE FROM `someBucket` USE KEYS [\"someId\", \"anotherId\"]"

        val actual: String = QueryBuilder
            .deleteFrom(
                someBucket().useKeys(
                    listOf("someId".toDopeType(), "anotherId".toDopeType()).toDopeType(),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string array field`() {
        val expected = "DELETE FROM `someBucket` USE KEYS `stringArrayField`"

        val actual: String = QueryBuilder
            .deleteFrom(
                someBucket().useKeys(
                    someStringArrayField(),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys clause with string function`() {
        val expected = "DELETE FROM `someBucket` USE KEYS CONCAT(\"some\", \"Id\")"

        val actual: String = QueryBuilder
            .deleteFrom(
                someBucket().useKeys(
                    concat("some", "Id"),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update use keys clause with string`() {
        val expected = "UPDATE `someBucket` USE KEYS \"someId\""

        val actual: String = QueryBuilder
            .update(
                someBucket().useKeys(
                    "someId",
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update use keys clause with string field`() {
        val expected = "UPDATE `someBucket` USE KEYS `stringField`"

        val actual: String = QueryBuilder
            .update(
                someBucket().useKeys(
                    someStringField(),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update use keys clause with string array`() {
        val expected = "UPDATE `someBucket` USE KEYS [\"someId\", \"anotherId\"]"

        val actual: String = QueryBuilder
            .update(
                someBucket().useKeys(
                    listOf("someId".toDopeType(), "anotherId".toDopeType()).toDopeType(),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update use keys clause with string array field`() {
        val expected = "UPDATE `someBucket` USE KEYS `stringArrayField`"

        val actual: String = QueryBuilder
            .update(
                someBucket().useKeys(
                    someStringArrayField(),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update use keys clause with string function`() {
        val expected = "UPDATE `someBucket` USE KEYS CONCAT(\"some\", \"Id\")"

        val actual: String = QueryBuilder
            .update(
                someBucket().useKeys(
                    concat("some", "Id"),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select use index clause`() {
        val expected = "SELECT * FROM `someBucket` USE INDEX (`someIndex` USING FTS, `otherIndex`, `index3` USING GSI)"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket().useFtsIndex("someIndex").useIndex("otherIndex").useGsiIndex("index3"),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select use hash build hint`() {
        val expected = "SELECT * FROM `someBucket` JOIN `anotherBucket` USE HASH (BUILD) " +
            "ON `someBucket`.`numberField` = `anotherBucket`.`numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .join(
                someBucket("anotherBucket"),
                someNumberField(bucket = someBucket()).isEqualTo(someNumberField(bucket = someBucket("anotherBucket"))),
                hashOrNestedLoopHint = HASH_BUILD,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select use nl hint`() {
        val expected = "SELECT * FROM `someBucket` JOIN `anotherBucket` USE NL " +
            "ON `someBucket`.`numberField` = `anotherBucket`.`numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .join(
                someBucket("anotherBucket"),
                someNumberField(bucket = someBucket()).isEqualTo(someNumberField(bucket = someBucket("anotherBucket"))),
                hashOrNestedLoopHint = NESTED_LOOP,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select use keys hint`() {
        val expected = "SELECT * FROM `someBucket` JOIN `anotherBucket` USE KEYS \"someID\" " +
            "ON `someBucket`.`numberField` = `anotherBucket`.`numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .join(
                someBucket("anotherBucket"),
                someNumberField(bucket = someBucket()).isEqualTo(someNumberField(bucket = someBucket("anotherBucket"))),
                keysOrIndexHint = keysHint("someID"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select use index hint`() {
        val expected = "SELECT * FROM `someBucket` JOIN `anotherBucket` USE INDEX (`someID`) " +
            "ON `someBucket`.`numberField` = `anotherBucket`.`numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .join(
                someBucket("anotherBucket"),
                someNumberField(bucket = someBucket()).isEqualTo(someNumberField(bucket = someBucket("anotherBucket"))),
                keysOrIndexHint = indexHint("someID"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select use hash probe and index hint`() {
        val expected = "SELECT * FROM `someBucket` JOIN `anotherBucket` USE HASH (PROBE) INDEX (`someID`) " +
            "ON `someBucket`.`numberField` = `anotherBucket`.`numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .join(
                someBucket("anotherBucket"),
                someNumberField(bucket = someBucket()).isEqualTo(someNumberField(bucket = someBucket("anotherBucket"))),
                hashOrNestedLoopHint = HASH_PROBE,
                keysOrIndexHint = indexHint("someID"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select use nl and keys hint`() {
        val expected = "SELECT * FROM `someBucket` JOIN `anotherBucket` USE NL KEYS \"someID\" " +
            "ON `someBucket`.`numberField` = `anotherBucket`.`numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .join(
                someBucket("anotherBucket"),
                someNumberField(bucket = someBucket()).isEqualTo(someNumberField(bucket = someBucket("anotherBucket"))),
                hashOrNestedLoopHint = NESTED_LOOP,
                keysOrIndexHint = keysHint("someID"),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
