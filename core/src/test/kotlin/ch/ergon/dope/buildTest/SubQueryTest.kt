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
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.asterisk
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.collection.exists
import ch.ergon.dope.resolvable.expression.type.collection.inArray
import ch.ergon.dope.resolvable.expression.type.function.array.arrayLength
import ch.ergon.dope.resolvable.expression.type.function.conditional.decode
import ch.ergon.dope.resolvable.expression.type.function.conditional.resultsIn
import ch.ergon.dope.resolvable.expression.type.function.type.typeOf
import ch.ergon.dope.resolvable.expression.type.get
import ch.ergon.dope.resolvable.expression.type.getString
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import kotlin.test.Test
import kotlin.test.assertEquals

class SubQueryTest {
    @Test
    fun `should support sub select in from`() {
        val expected = "SELECT `stringField` FROM (SELECT * FROM `someBucket`) AS `asdf`"

        val actual: String = QueryBuilder
            .select(someStringField())
            .from(QueryBuilder.selectAsterisk().from(someBucket()).alias("asdf"))
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select with in array`() {
        val expected = "SELECT TRUE IN (SELECT RAW FALSE FROM `other`) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                TRUE.inArray(
                    QueryBuilder.selectRaw(FALSE).from(someBucket("other")).asExpression(),
                ),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in EXISTS`() {
        val expected = "SELECT EXISTS (SELECT * FROM `someBucket`)"

        val actual = QueryBuilder.select(
            exists(someSelectClause().from(someBucket()).asExpression()),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in ARRAY_LENGTH`() {
        val expected = "SELECT ARRAY_LENGTH((SELECT * FROM `someBucket`))"

        val actual = QueryBuilder.select(
            arrayLength(someSelectClause().from(someBucket()).asExpression()),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in Equals`() {
        val expected = "SELECT `stringField` = (SELECT RAW `stringField` FROM `someBucket`)[0]"

        val actual = QueryBuilder.select(
            someStringField().isEqualTo(QueryBuilder.selectRaw(someStringField()).from(someBucket()).get(0)),
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

        val actual = QueryBuilder
            .select(
                exists(someSelectClause(someString().asParameter()).from(someBucket()).asExpression()),
            ).from(
                QueryBuilder.select(someNumber().asParameter("num")).from(someBucket("other")).alias("asdf"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in conditional function`() {
        val expected = "SELECT DECODE((SELECT RAW `stringField`)[0], \"string\", 1, \"anotherString\", 2)"

        val actual = QueryBuilder
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

        val actual = QueryBuilder
            .select(
                typeOf(someSelectRawClause().asExpression()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select entry field`() {
        val expected = "SELECT (SELECT * FROM `someBucket`)[0].`name`"

        val actual = QueryBuilder
            .select(
                QueryBuilder
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

        val actual = QueryBuilder
            .selectAsterisk()
            .where(
                someNumber().inArray(
                    QueryBuilder
                        .selectRaw(someNumberField())
                        .from(someBucket()).asExpression(),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk on sub select`() {
        val sub: AliasedSelectClause<ObjectType> = QueryBuilder.select(1.toDopeType()).alias("sub")
        val expected = "SELECT `sub`.* FROM (SELECT 1) AS `sub`"

        val actual = QueryBuilder
            .select(
                sub.asterisk(),
            ).from(
                sub,
            ).build().queryString

        assertEquals(expected, actual)
    }
}
