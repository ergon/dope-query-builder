package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive.Companion.FALSE
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive.Companion.TRUE
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.inArray
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.toArrayType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class ArrayTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: DSLContext

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = DSLContext()
    }

    @Test
    fun `should support arrays`() {
        val expected = "SELECT [person.age, person.fname] FROM person"

        val actual: String = create
            .select(
                listOf(TestBucket.Person.age, TestBucket.Person.fname).toArrayType(),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple arrays`() {
        val expected = "SELECT [person.age, person.fname], [person.type, person.relation] FROM person"

        val actual: String = create
            .select(
                listOf(TestBucket.Person.age, TestBucket.Person.fname).toArrayType(),
                listOf(TestBucket.Person.type, TestBucket.Person.relation).toArrayType(),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support arrays with multiple types`() {
        val expected = "SELECT [\"test\", person.fname, 53], [TRUE] FROM person"

        val actual: String = create
            .select(
                listOf("test".toStringType(), TestBucket.Person.fname, 53.toNumberType()).toArrayType(),
                listOf(TRUE).toArrayType(),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aliased`() {
        val expected = "SELECT [person.age, person.fname] AS test FROM person"

        val actual: String = create
            .select(
                listOf(TestBucket.Person.age, TestBucket.Person.fname).toArrayType().alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple arrays aliased`() {
        val expected = "SELECT [person.age, person.fname] AS fname, [TRUE] AS true FROM person"

        val actual: String = create
            .select(
                listOf(TestBucket.Person.age, TestBucket.Person.fname).toArrayType().alias("fname"),
                listOf(TRUE).toArrayType().alias("true"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested arrays`() {
        val expected = "SELECT [person.fname, [person.age, TRUE, \"string\"], 23] AS test FROM person"

        val actual: String = create
            .select(
                listOf(
                    TestBucket.Person.fname,
                    listOf(
                        TestBucket.Person.age,
                        TRUE,
                        "string".toStringType(),
                    ).toArrayType(),
                    23.toNumberType(),
                ).toArrayType().alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string arrays`() {
        val expected = "SELECT [\"string\", \"hallo\"], 23 FROM person"

        val actual: String = create
            .select(
                listOf(
                    "string".toStringType(),
                    "hallo".toStringType(),
                ).toArrayType(),
                23.toNumberType(),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stringFunction in string arrays`() {
        val expected = "SELECT [CONCAT(\"string\", person.fname), \"hallo\"] AS test, 23 FROM person"

        val actual: String = create
            .select(
                listOf(
                    concat("string".toStringType(), TestBucket.Person.fname),
                    "hallo".toStringType(),
                ).toArrayType().alias("test"),
                23.toNumberType(),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array`() {
        val expected = "SELECT TRUE IN [person.fname, person.age, FALSE, \"string\", 23] AS test FROM person"

        val actual: String = create
            .select(
                TRUE.inArray(
                    listOf(
                        TestBucket.Person.fname,
                        TestBucket.Person.age,
                        FALSE,
                        "string".toStringType(),
                        23.toNumberType(),
                    ).toArrayType(),
                ).alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with boolean`() {
        val expected = "SELECT TRUE IN [person.fname, person.age, FALSE, \"string\", 23] AS test FROM person"

        val actual: String = create
            .select(
                true.inArray(
                    listOf(
                        TestBucket.Person.fname,
                        TestBucket.Person.age,
                        FALSE,
                        "string".toStringType(),
                        23.toNumberType(),
                    ).toArrayType(),
                ).alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with string`() {
        val expected = "SELECT \"test\" IN [person.fname, person.age, FALSE, \"string\", 23] AS test FROM person"

        val actual: String = create
            .select(
                "test".inArray(
                    listOf(
                        TestBucket.Person.fname,
                        TestBucket.Person.age,
                        FALSE,
                        "string".toStringType(),
                        23.toNumberType(),
                    ).toArrayType(),
                ).alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with number`() {
        val expected = "SELECT 3 IN [person.fname, person.age, FALSE, \"string\", 23] AS test FROM person"

        val actual: String = create
            .select(
                3.inArray(
                    listOf(
                        TestBucket.Person.fname,
                        TestBucket.Person.age,
                        FALSE,
                        "string".toStringType(),
                        23.toNumberType(),
                    ).toArrayType(),
                ).alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with boolean and collection`() {
        val expected = "SELECT TRUE IN [person.fname, person.age, FALSE, \"string\", 23] AS test FROM person"

        val actual: String = create
            .select(
                true.inArray(
                    listOf(
                        TestBucket.Person.fname,
                        TestBucket.Person.age,
                        FALSE,
                        "string".toStringType(),
                        23.toNumberType(),
                    ),
                ).alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with string and collection`() {
        val expected = "SELECT \"test\" IN [person.fname, person.age, FALSE, \"string\", 23] AS test FROM person"

        val actual: String = create
            .select(
                "test".inArray(
                    listOf(
                        TestBucket.Person.fname,
                        TestBucket.Person.age,
                        FALSE,
                        "string".toStringType(),
                        23.toNumberType(),
                    ),
                ).alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with number and collection`() {
        val expected = "SELECT 3 IN [person.fname, person.age, FALSE, \"string\", 23] AS test FROM person"

        val actual: String = create
            .select(
                3.inArray(
                    listOf(
                        TestBucket.Person.fname,
                        TestBucket.Person.age,
                        FALSE,
                        "string".toStringType(),
                        23.toNumberType(),
                    ),
                ).alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with field and collection`() {
        val expected = "SELECT person.age IN [person.fname, FALSE, \"string\", 23] AS test FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.inArray(
                    listOf(
                        TestBucket.Person.fname,
                        FALSE,
                        "string".toStringType(),
                        23.toNumberType(),
                    ),
                ).alias("test"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array as whereClause`() {
        val expected = "SELECT * FROM person WHERE person.fname IN [\"string\", \"hallo\"]"

        val actual: String = create
            .selectFrom(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.inArray(
                    listOf(
                        "string".toStringType(),
                        "hallo".toStringType(),
                    ).toArrayType(),
                ),
            ).build()

        assertEquals(expected, actual)
    }
}
