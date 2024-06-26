package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.inArray
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class ParameterizedQueriesTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should Support Positional Parameters As Key Easy`() {
        val expected = "SELECT $1 = 1"
        val positionalParameterActual = 1.asParameter()

        val actual = create.select(positionalParameterActual.isEqualTo(1.toDopeType())).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Named Parameters As Key Easy`() {
        val expected = "SELECT \$greeting = \"hello\""

        val actual = create.select(
            "hello".asParameter("greeting")
                .isEqualTo("hello".toDopeType()),
        ).build().queryString

        assertEquals(
            unifyString(expected),
            actual,
        )
    }

    @Test
    fun `should Support Named Parameters As Key But Missing`() {
        val expected = "There are more parameters in the query than were passed"
        try {
            create.select("hello".asParameter("greeting").isEqualTo("hello".toDopeType())).build()
        } catch (e: Exception) {
            assertEquals(expected, e.message)
        }
    }

    @Test
    fun `should Support Named Parameters With Values`() {
        val parameterActual = false.asParameter("isAwesome")
        assertEquals(false, parameterActual.toDopeQuery().parameters["isAwesome"])
    }

    @Test
    fun `should Support Positional Parameters`() {
        val expected = "SELECT * FROM `someBucket` WHERE `country` = $1"

        val actual: String =
            create.selectFrom(someBucket())
                .where(someStringField("country").isEqualTo("UnitedStates".asParameter())).build().queryString
        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Number Array Parameters`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` IN $1"

        val actual: String = create.selectFrom(someBucket())
            .where(someNumberField().inArray(listOf<Number>().asParameter())).build().queryString
        assertEquals(expected, actual)
    }

    @Test
    fun `should Support String Array Parameters`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` IN $1"

        val actual: String = create.selectFrom(someBucket())
            .where(someStringField().inArray(listOf<String>().asParameter())).build().queryString
        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Boolean Array Parameters`() {
        val expected = "SELECT * FROM `someBucket` WHERE `booleanField` IN $1"

        val actual: String = create.selectFrom(someBucket())
            .where(someBooleanField().inArray(listOf<Boolean>().asParameter())).build().queryString
        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Empty Passed Parameters`() {
        val expected = "SELECT *"

        val actual: String =
            create.selectAsterisk().build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters`() {
        val expected = "SELECT $1 AS `one`, \$MagicNumber AS `two`"

        val actual: String = create
            .select(
                "Hello".asParameter().alias("one"),
                99.asParameter("MagicNumber").alias("two"),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters 2`() {
        val expected = "SELECT $1, (\$name OR ($2 AND \$MagicNumber)) AS `one`"

        val actual: String = create
            .select(
                "Anonymous Value".asParameter(),
                true.asParameter("name").or(
                    false.asParameter().and(true.asParameter("MagicNumber")),
                ).alias("one"),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters 3`() {
        val expected = "SELECT $1, (\$name OR ($2 AND \$MagicNumber)), CONCAT(\$superMagic, $3) AS `one`"

        val actual: String = create
            .select(
                "Anonymous Value".asParameter(),
                false.asParameter("name").or(
                    true.asParameter().and(false.asParameter("MagicNumber")),
                ),
                concat(
                    "Mice".asParameter("superMagic"),
                    "Never to be seen".asParameter(),
                ).alias("one"),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters 4`() {
        val expected = "SELECT $1, (\$name OR ($2 AND \$MagicNumber)) AS `one`, CONCAT(\$superMagic, \$3)"

        val actual: String = create
            .select(
                "Super Value".asParameter(),
                false.asParameter("name").or(
                    true.asParameter().and(
                        false.asParameter("MagicNumber"),
                    ),
                ).alias("one"),
                concat(
                    "Rabbit".asParameter("superMagic"),
                    "Void".asParameter(),
                ),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Aliased Parameters`() {
        val expected = "SELECT $1 AS `one`"

        val actual: String = create.select(
            "Questionable"
                .asParameter()
                .alias(
                    "one",
                ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Prefix Operator2 Parameters`() {
        val expected = "SELECT CONCAT(\$greetingLeft, \$greetingRight)"

        val actual: String = create.select(
            concat(
                "Good Day!".asParameter("greetingLeft"),
                "Good Morning".asParameter("greetingRight"),
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `shouldSupport Prefix Operator2 Parameters Aliased`() {
        val expected = "SELECT CONCAT(\$greetingLeft, \$greetingRight) AS `concatted`"

        val actual: String = create.select(
            concat(
                "Salut".asParameter("greetingLeft"),
                ("Good Afternoon".asParameter("greetingRight")),
            ).alias("concatted"),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }
}
