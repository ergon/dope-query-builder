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
    private lateinit var create: DSLContext

    @BeforeTest
    fun setup() {
        create = DSLContext()
    }

    @Test
    fun `should support arrays`() {
        val person = someBucket("person")
        val expected = "SELECT [person.fname, strField] FROM person"

        val actual: String = create
            .select(
                listOf(someStringField("fname", person), someStringField()).toArrayType(),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple arrays`() {
        val person = someBucket("person")
        val expected = "SELECT [strField], [strField] FROM person"

        val actual: String = create
            .select(
                listOf(someStringField()).toArrayType(),
                listOf(someStringField()).toArrayType(),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support arrays with multiple types`() {
        val person = someBucket("person")
        val expected = "SELECT [\"test\", 53, TRUE, strField, person.age, boolField] FROM person"

        val actual: String = create
            .select(
                listOf(
                    "test".toStringType(),
                    53.toNumberType(),
                    TRUE,
                    someStringField(),
                    someNumberField("age", person),
                    someBooleanField(),
                ).toArrayType(),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aliased`() {
        val person = someBucket("person")
        val expected = "SELECT [strField] AS test FROM person"

        val actual: String = create
            .select(
                listOf(someStringField()).toArrayType().alias("test"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple arrays aliased`() {
        val person = someBucket("person")
        val expected = "SELECT [strField] AS fname, [strField] AS true FROM person"

        val actual: String = create
            .select(
                listOf(someStringField()).toArrayType().alias("fname"),
                listOf(someStringField()).toArrayType().alias("true"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested arrays`() {
        val person = someBucket("person")
        val expected = "SELECT [strField, [person.age, TRUE, \"string\"], 23] AS test FROM person"

        val actual: String = create
            .select(
                listOf(
                    someStringField(),
                    listOf(
                        someNumberField("age", person),
                        TRUE,
                        "string".toStringType(),
                    ).toArrayType(),
                    23.toNumberType(),
                ).toArrayType().alias("test"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string arrays`() {
        val person = someBucket("person")
        val expected = "SELECT [\"string\", \"hallo\"] FROM person"

        val actual: String = create
            .select(
                listOf(
                    "string".toStringType(),
                    "hallo".toStringType(),
                ).toArrayType(),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stringFunction in string arrays`() {
        val person = someBucket("person")
        val expected = "SELECT [CONCAT(\"string\", strField), \"hallo\"] AS test, 23 FROM person"

        val actual: String = create
            .select(
                listOf(
                    concat("string".toStringType(), someStringField()),
                    "hallo".toStringType(),
                ).toArrayType().alias("test"),
                23.toNumberType(),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array`() {
        val person = someBucket("person")
        val expected = "SELECT TRUE IN [FALSE] FROM person"

        val actual: String = create
            .select(
                TRUE.inArray(
                    listOf(
                        FALSE,
                    ).toArrayType(),
                ),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array aliased`() {
        val person = someBucket("person")
        val expected = "SELECT TRUE IN [FALSE] AS test FROM person"

        val actual: String = create
            .select(
                TRUE.inArray(
                    listOf(
                        FALSE,
                    ).toArrayType(),
                ).alias("test"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array as whereClause`() {
        val person = someBucket("person")
        val expected = "SELECT * FROM person WHERE strField IN [\"string\", \"hallo\"]"

        val actual: String = create
            .selectFrom(
                person,
            ).where(
                someStringField().inArray(
                    listOf(
                        "string".toStringType(),
                        "hallo".toStringType(),
                    ).toArrayType(),
                ),
            ).build()

        assertEquals(expected, actual)
    }
}
