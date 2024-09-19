package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.FALSE
import ch.ergon.dope.resolvable.expression.unaliased.type.TRUE
import ch.ergon.dope.resolvable.expression.unaliased.type.access.get
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.exists
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.inArray
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayLength
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
            .from(create.selectAsterisk().from(someBucket()).queryAlias("asdf"))
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select with in array`() {
        val expected = "SELECT TRUE IN (SELECT RAW FALSE FROM `other`) FROM `someBucket`"

        val actual: String = create
            .select(
                TRUE.inArray(
                    create.selectRaw(FALSE).from(someBucket("other")),
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
            exists(someSelectClause().from(someBucket())),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub select in ARRAY_LENGTH`() {
        val expected = "SELECT ARRAY_LENGTH((SELECT * FROM `someBucket`))"

        val actual = create.select(
            arrayLength(someSelectClause().from(someBucket())),
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
}
