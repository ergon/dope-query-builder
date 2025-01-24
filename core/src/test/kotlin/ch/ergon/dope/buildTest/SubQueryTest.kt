package ch.ergon.dope.buildTest

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.expression.unaliased.type.FALSE
import ch.ergon.dope.resolvable.expression.unaliased.type.TRUE
import ch.ergon.dope.resolvable.expression.unaliased.type.access.get
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.exists
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.inArray
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayLength
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.decode
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.resultsIn
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.typeOf
import ch.ergon.dope.resolvable.expression.unaliased.type.getString
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SubQueryTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support sub select in from`() {
        val expected = "SELECT `stringField` FROM (SELECT * FROM `someBucket`) AS `asdf`"

        val actual: String = create
            .select(someStringField())
            .from(create.selectAsterisk().from(someBucket()).alias("asdf"))
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select with in array`() {
        val expected = "SELECT TRUE IN (SELECT RAW FALSE FROM `other`) FROM `someBucket`"

        val actual: String = create
            .select(
                TRUE.inArray(
                    create.selectRaw(FALSE).from(someBucket("other")).asExpression(),
                ),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in EXISTS`() {
        val expected = "SELECT EXISTS (SELECT * FROM `someBucket`)"

        val actual = create.select(
            exists(someSelectClause().from(someBucket()).asExpression()),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in ARRAY_LENGTH`() {
        val expected = "SELECT ARRAY_LENGTH((SELECT * FROM `someBucket`))"

        val actual = create.select(
            arrayLength(someSelectClause().from(someBucket()).asExpression()),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in Equals`() {
        val expected = "SELECT `stringField` = (SELECT RAW `stringField` FROM `someBucket`)[0]"

        val actual = create.select(
            someStringField().isEqualTo(create.selectRaw(someStringField()).from(someBucket()).get(0)),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select with multiple parameters`() {
        val expected = DopeQuery(
            "SELECT EXISTS (SELECT \$1 FROM `someBucket`) FROM (SELECT \$num FROM `other`) AS `asdf`",
            DopeParameters(
                namedParameters = mapOf("num" to 5),
                positionalParameters = listOf("someString"),
            ),
        )

        val actual = create
            .select(
                exists(someSelectClause(someString().asParameter()).from(someBucket()).asExpression()),
            ).from(
                create.select(someNumber().asParameter("num")).from(someBucket("other")).alias("asdf"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in conditional function`() {
        val expected = "SELECT DECODE((SELECT RAW `stringField`)[0], \"string\", 1, \"anotherString\", 2)"

        val actual = create
            .select(
                decode(
                    someStringSelectRawClause().get(0),
                    "string".resultsIn(1),
                    "anotherString".resultsIn(2),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in type function`() {
        val expected = "SELECT TYPE((SELECT RAW `stringField`))"

        val actual = create
            .select(
                typeOf(someSelectRawClause().asExpression()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select entry field`() {
        val expected = "SELECT (SELECT * FROM `someBucket`)[0].`name`"

        val actual = create
            .select(
                create
                    .selectAsterisk()
                    .from(someBucket()).asExpression()
                    .get(0)
                    .getString("name"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select entry field with in array`() {
        val expected = "SELECT * WHERE 5 IN (SELECT RAW `numberField` FROM `someBucket`)"

        val actual = create
            .selectAsterisk()
            .where(
                someNumber().inArray(
                    create
                        .selectRaw(someNumberField())
                        .from(someBucket()).asExpression(),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
