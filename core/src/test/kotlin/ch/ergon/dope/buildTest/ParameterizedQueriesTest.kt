package ch.ergon.dope.buildTest

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.collection.inArray
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.logic.or
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedQueriesTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should Support Positional Parameters As Key Easy`() {
        val expected = "SELECT $1 = 1"
        val positionalParameterActual = 1.asParameter()

        val actual = QueryBuilder.select(positionalParameterActual.isEqualTo(1.toDopeType())).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Named Parameters As Key Easy`() {
        val expected = "SELECT \$greeting = \"hello\""

        val actual = QueryBuilder.select(
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
            QueryBuilder.select("hello".asParameter("greeting").isEqualTo("hello".toDopeType())).build()
        } catch (e: Exception) {
            assertEquals(expected, e.message)
        }
    }

    @Test
    fun `should Support Named Parameters With Values`() {
        val parameterActual = false.asParameter("isAwesome")
        assertEquals(false, parameterActual.toDopeQuery(manager).parameters.namedParameters["isAwesome"])
    }

    @Test
    fun `should Support Positional Parameters`() {
        val expected = "SELECT * FROM `someBucket` WHERE `country` = $1"

        val actual: String =
            QueryBuilder.selectFrom(someBucket())
                .where(someStringField("country").isEqualTo("UnitedStates".asParameter())).build().queryString
        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Number Array Parameters`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` IN $1"

        val actual: String = QueryBuilder.selectFrom(someBucket())
            .where(someNumberField().inArray(listOf<Number>().asParameter())).build().queryString
        assertEquals(expected, actual)
    }

    @Test
    fun `should Support String Array Parameters`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` IN $1"

        val actual: String = QueryBuilder.selectFrom(someBucket())
            .where(someStringField().inArray(listOf<String>().asParameter())).build().queryString
        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Boolean Array Parameters`() {
        val expected = "SELECT * FROM `someBucket` WHERE `booleanField` IN $1"

        val actual: String = QueryBuilder.selectFrom(someBucket())
            .where(someBooleanField().inArray(listOf<Boolean>().asParameter())).build().queryString
        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Empty Passed Parameters`() {
        val expected = "SELECT *"

        val actual: String =
            QueryBuilder.selectAsterisk().build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters`() {
        val expected = "SELECT $1 AS `one`, \$MagicNumber AS `two`"

        val actual: String = QueryBuilder
            .select(
                "Hello".asParameter().alias("one"),
                99.asParameter("MagicNumber").alias("two"),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Parameters 2`() {
        val expected = "SELECT $1, (\$name OR ($2 AND \$MagicNumber)) AS `one`"

        val actual: String = QueryBuilder
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

        val actual: String = QueryBuilder
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

        val actual: String = QueryBuilder
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

        val actual: String = QueryBuilder.select(
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

        val actual: String = QueryBuilder.select(
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

        val actual: String = QueryBuilder.select(
            concat(
                "Salut".asParameter("greetingLeft"),
                ("Good Afternoon".asParameter("greetingRight")),
            ).alias("concatted"),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should use different parameter managers for parallel queries`() {
        val parameterValue1 = someNumber()
        val parameterValue2 = someNumber()
        val expected = QueryBuilder
            .selectFrom(
                someBucket(),
            )
            .where(
                parameterValue1.asParameter().isEqualTo(parameterValue2.asParameter()),
            ).build()

        val actual = QueryBuilder
            .selectFrom(
                someBucket(),
            )
            .where(
                parameterValue1.asParameter().isEqualTo(parameterValue2.asParameter()),
            ).build()

        assertEquals(expected.queryString, actual.queryString)
        assertEquals(expected.parameters.positionalParameters, actual.parameters.positionalParameters)
    }
}
