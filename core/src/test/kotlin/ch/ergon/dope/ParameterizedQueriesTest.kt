package ch.ergon.dope

import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive.Companion.FALSE
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive.Companion.TRUE
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class ParameterizedQueriesTest {

    private lateinit var builder: StringBuilder
    private lateinit var create: DSLContext

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = DSLContext()
    }

    @Test
    fun `should Support Positional Parameters As Key Easy`() {
        val expected = "SELECT $1 = 1"
        val positionalParameterActual = 1.toNumberType().asParameter()

        val actual = create.select(positionalParameterActual.isEqualTo(1.toNumberType())).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Named Parameters As Key Easy`() {
        val expected = "SELECT \$greeting = \"hello\""

        val actual = create.select(
            "hello".toStringType().asParameter("greeting")
                .isEqualTo("hello".toStringType()),
        ).build()

        assertEquals(
            unifyString(expected),
            actual,
        )
    }

    @Test
    fun `should Support Named Parameters As Key But Missing`() {
        val expected = "There are more parameters in the query than were passed"
        try {
            create.select("hello".toStringType().asParameter("greeting").isEqualTo("hello".toStringType())).build()
        } catch (e: Exception) {
            assertEquals(expected, e.message)
        }
    }

    @Test
    fun `shouldSupport Named Parameters With Values`() {
        val parameterActual = FALSE.asParameter("isAwesome")
        assertEquals(FALSE, parameterActual.previousValue)
    }

    @Test
    fun `should Support Positional Parameters`() {
        val expected = "SELECT * FROM someBucket WHERE country = $1"

        val actual: String =
            create.selectFrom(someBucket())
                .where(someStringField("country").isEqualTo("UnitedStates".toStringType().asParameter())).build()
        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Empty Passed Parameters`() {
        val expected = "SELECT *"

        val actual: String =
            create.selectAll().build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters`() {
        val expected = "SELECT $1 AS one, \$MagicNumber AS two"

        val actual: String = create
            .select(
                "Hello".toStringType().asParameter().alias("one"),
                99.toNumberType().asParameter("MagicNumber").alias("two"),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters 2`() {
        val expected = "SELECT $1, \$name OR $2 AND \$MagicNumber AS one"

        val actual: String = create
            .select(
                "Anonymous Value".toStringType().asParameter(),
                TRUE.asParameter("name").or(
                    FALSE.asParameter().and(TRUE.asParameter("MagicNumber")),
                ).alias("one"),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters 3`() {
        val expected = "SELECT $1, \$name OR $2 AND \$MagicNumber, CONCAT(\$superMagic, $3) AS one"

        val actual: String = create
            .select(
                "Anonymous Value".toStringType().asParameter(),
                FALSE.asParameter("name").or(
                    TRUE.asParameter().and(FALSE.asParameter("MagicNumber")),
                ),
                concat(
                    "Mice".toStringType().asParameter("superMagic"),
                    "Never to be seen".toStringType().asParameter(),
                ).alias("one"),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters 4`() {
        val expected = "SELECT $1, \$name OR $2 AND \$MagicNumber AS one, CONCAT(\$superMagic, \$3)"

        val actual: String = create
            .select(
                "Super Value".toStringType().asParameter(),
                FALSE.asParameter("name").or(
                    TRUE.asParameter().and(
                        FALSE.asParameter("MagicNumber"),
                    ),
                ).alias("one"),
                concat(
                    "Rabbit".toStringType().asParameter("superMagic"),
                    "Void".toStringType().asParameter(),
                ),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Aliased Parameters`() {
        val expected = "SELECT $1 AS one"

        val actual: String = create.select(
            "Questionable".toStringType()
                .asParameter()
                .alias(
                    "one",
                ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Prefix Operator2 Parameters`() {
        val expected = "SELECT CONCAT(\$greetingLeft, \$greetingRight)"

        val actual: String = create.select(
            concat(
                "Good Day!".toStringType().asParameter("greetingLeft"),
                "Good Morning".toStringType().asParameter("greetingRight"),
            ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `shouldSupport Prefix Operator2 Parameters Aliased`() {
        val expected = "SELECT CONCAT(\$greetingLeft, \$greetingRight) AS concatted"

        val actual: String = create.select(
            concat(
                "Salut".toStringType().asParameter("greetingLeft"),
                ("Good Afternoon".toStringType().asParameter("greetingRight")),
            )
                .alias("concatted"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }
}
