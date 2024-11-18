package ch.ergon.dope.clauses

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.idField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.isActiveField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.nameField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.typeField
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.integrationTest.toRawValues
import ch.ergon.dope.resolvable.clause.model.setoperator.intersect
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessThan
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectQueryIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `select everything from the test bucket`() {
        val dopeQuery = QueryBuilder()
            .selectFrom(
                testBucket,
            ).build()

        val result = queryWithoutParameters(dopeQuery)

        assertEquals(15, result.rows.size)
    }

    @Test
    fun `select all employees from the test bucket`() {
        val dopeQuery = QueryBuilder()
            .selectFrom(
                testBucket,
            )
            .where(
                typeField.isEqualTo("employee"),
            ).orderBy(
                nameField,
            ).build()

        val result = queryWithoutParameters(dopeQuery)

        assertEquals(5, result.rows.size)
    }

    @Test
    fun `select all active clients from the test bucket`() {
        val dopeQuery = QueryBuilder()
            .selectFrom(
                testBucket,
            )
            .where(
                typeField.isEqualTo("client").and(isActiveField.isEqualTo(true)),
            ).build()

        val result = queryWithoutParameters(dopeQuery)

        assertEquals(2, result.rows.size)
    }

    @Test
    fun `select id, type field from the test bucket`() {
        val dopeQuery = QueryBuilder()
            .select(
                idField,
                typeField,
            )
            .from(
                testBucket,
            ).orderBy(
                typeField,
            ).limit(
                1,
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(1, result["id"])
        assertEquals("client", result["type"])
    }

    @Test
    fun `select aliased field from the test bucket`() {
        val alias = "Identification"
        val dopeQuery = QueryBuilder()
            .select(
                idField.alias(alias),
            )
            .from(
                testBucket,
            ).limit(
                1,
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)

        val result = queryResult.toMapValues()

        assertEquals(1, result[alias])
    }

    @Test
    fun `select where nothing applies from the test bucket`() {
        val dopeQuery = QueryBuilder()
            .selectFrom(
                testBucket,
            )
            .where(
                1.isEqualTo(2),
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val actual = queryResult.rows.size

        assertEquals(0, actual)
    }

    @Test
    fun `select distinct field from the test bucket`() {
        val dopeQuery = QueryBuilder()
            .selectDistinct(
                idField,
            )
            .from(
                testBucket,
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val actual = queryResult.rows.size

        assertEquals(5, actual)
    }

    @Test
    fun `select raw field from the test bucket`() {
        val dopeQuery = QueryBuilder()
            .selectRaw(
                idField,
            )
            .from(
                testBucket,
            ).limit(
                1,
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toRawValues()

        assertEquals(1, result)
    }

    @Test
    fun `intersect two select clauses`() {
        val dopeQuery = QueryBuilder()
            .select(
                idField,
            )
            .from(
                testBucket,
            )
            .where(
                typeField.isEqualTo("client").and(
                    idField.isLessThan(4),
                ),
            )
            .intersect(
                QueryBuilder()
                    .select(
                        idField,
                    )
                    .from(
                        testBucket,
                    ).where(
                        typeField.isEqualTo("client").and(
                            idField.isGreaterThan(2),
                        ),
                    ),
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(3, result["id"])
    }

    @Test
    fun `select named parameter`() {
        val parameter = 1.asParameter("parameter")
        val dopeQuery = QueryBuilder()
            .select(
                parameter.alias("namedParameter"),
            ).build()

        val queryResult = queryWithNamedParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(1, result["namedParameter"])
    }

    @Test
    fun `select positional parameter`() {
        val parameter = 1.asParameter()
        val dopeQuery = QueryBuilder()
            .select(
                parameter.alias("positionalParameter"),
            ).build()

        val queryResult = queryWithPositionalParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(1, result["positionalParameter"])
    }
}
