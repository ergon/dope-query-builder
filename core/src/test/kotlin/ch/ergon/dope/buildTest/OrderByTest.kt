package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.OrderByType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderByTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should add an Order By clause to the end`() {
        val expected = "SELECT * FROM `someBucket` ORDER BY `stringField`"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).orderBy(
                someStringField(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should add an Order By Ascending clause`() {
        val expected = "SELECT * FROM `someBucket` ORDER BY `stringField` ASC"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).orderBy(
                someStringField(),
                OrderByType.ASC,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should add an Order By Descending clause`() {
        val expected = "SELECT * FROM `someBucket` ORDER BY `stringField` DESC"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).orderBy(
                someStringField(),
                OrderByType.DESC,
            ).build().queryString

        assertEquals(expected, actual)
    }
}
