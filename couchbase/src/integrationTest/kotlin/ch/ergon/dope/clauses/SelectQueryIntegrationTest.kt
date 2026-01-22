package ch.ergon.dope.clauses

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.idField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.isActiveField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.nameField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.quantitiesField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testKeyspace
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.typeField
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.integrationTest.toRawValues
import ch.ergon.dope.resolvable.asterisk
import ch.ergon.dope.resolvable.clause.intersect
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.assignTo
import ch.ergon.dope.resolvable.expression.type.collection.any
import ch.ergon.dope.resolvable.expression.type.get
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.type.relational.isNull
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectQueryIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `select everything from the test keyspace`() {
        val dopeQuery = QueryBuilder
            .selectFrom(
                testKeyspace,
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals(15, queryResult.rows.size)
    }

    @Test
    fun `select all employees from the test keyspace`() {
        val dopeQuery = QueryBuilder
            .selectFrom(
                testKeyspace,
            )
            .where(
                typeField.isEqualTo("employee"),
            )
            .orderBy(
                nameField,
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals(5, queryResult.rows.size)
    }

    @Test
    fun `select all active clients from the test keyspace`() {
        val dopeQuery = QueryBuilder
            .selectFrom(
                testKeyspace,
            )
            .where(
                typeField.isEqualTo("client").and(isActiveField.isEqualTo(true)),
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals(2, queryResult.rows.size)
    }

    @Test
    fun `select id, type field from the test keyspace`() {
        val dopeQuery = QueryBuilder
            .select(
                idField,
                typeField,
            )
            .from(
                testKeyspace,
            )
            .orderBy(
                typeField,
            )
            .limit(
                1,
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(1, result["id"])
        assertEquals("client", result["type"])
    }

    @Test
    fun `select aliased field from the test keyspace`() {
        val alias = "Identification"
        val dopeQuery = QueryBuilder
            .select(
                idField.alias(alias),
            )
            .from(
                testKeyspace,
            )
            .limit(
                1,
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(1, result[alias])
    }

    @Test
    fun `select where nothing applies from the test keyspace`() {
        val dopeQuery = QueryBuilder
            .selectFrom(
                testKeyspace,
            )
            .where(
                idField.isNull(),
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals(0, queryResult.rows.size)
    }

    @Test
    fun `select distinct field from the test keyspace`() {
        val dopeQuery = QueryBuilder
            .selectDistinct(
                idField,
            )
            .from(
                testKeyspace,
            ).orderBy(
                idField,
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals(5, queryResult.rows.size)
        assertEquals(mapOf("id" to 1), queryResult.toMapValues(rowNumber = 0))
        assertEquals(mapOf("id" to 2), queryResult.toMapValues(rowNumber = 1))
        assertEquals(mapOf("id" to 3), queryResult.toMapValues(rowNumber = 2))
        assertEquals(mapOf("id" to 4), queryResult.toMapValues(rowNumber = 3))
        assertEquals(mapOf("id" to 5), queryResult.toMapValues(rowNumber = 4))
    }

    @Test
    fun `select raw field from the test keyspace`() {
        val dopeQuery = QueryBuilder
            .selectRaw(
                idField,
            )
            .from(
                testKeyspace,
            )
            .limit(
                1,
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toRawValues()

        assertEquals(1, result)
    }

    @Test
    fun `intersect two select clauses`() {
        val dopeQuery = QueryBuilder
            .select(
                idField,
            )
            .from(
                testKeyspace,
            )
            .where(
                typeField.isEqualTo("client").and(
                    idField.isLessThan(4),
                ),
            )
            .intersect(
                QueryBuilder
                    .select(
                        idField,
                    )
                    .from(
                        testKeyspace,
                    )
                    .where(
                        typeField.isEqualTo("client").and(
                            idField.isGreaterThan(2),
                        ),
                    ),
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(3, result["id"])
    }

    @Test
    fun `select named parameter`() {
        val parameter = 1.asParameter("parameter")
        val dopeQuery = QueryBuilder
            .select(
                parameter.alias("namedParameter"),
            ).build(CouchbaseResolver())

        val queryResult = queryWithNamedParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(1, result["namedParameter"])
    }

    @Test
    fun `select positional parameter`() {
        val parameter = 1.asParameter()
        val dopeQuery = QueryBuilder
            .select(
                parameter.alias("positionalParameter"),
            ).build(CouchbaseResolver())

        val queryResult = queryWithPositionalParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(1, result["positionalParameter"])
    }

    @Test
    fun `select from subquery`() {
        val subQuery = QueryBuilder
            .selectRaw(quantitiesField)
            .from(testKeyspace)
            .where(typeField.isEqualTo("order"))
            .limit(1)
            .alias("subQuery")
        val dopeQuery = QueryBuilder
            .select(subQuery)
            .from(subQuery)
            .where(
                subQuery.any { it.isGreaterOrEqualThan(1) },
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(listOf(1, 2, 3), result["subQuery"])
    }

    @Test
    fun `select with star expression`() {
        val contacts = listOf(
            mapOf("email" to "contact.a@client.com", "name" to "Contact A"),
            mapOf("email" to "contact.b@client.com", "name" to "Contact B"),
        )
        val dopeQuery = QueryBuilder
            .select(
                testKeyspace.asterisk(),
            )
            .from(
                testKeyspace,
            )
            .where(
                typeField.isEqualTo("client").and(isActiveField),
            )
            .build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals(
            mapOf("id" to 2, "isActive" to true, "name" to "client2", "type" to "client", "contacts" to contacts),
            queryResult.toMapValues(rowNumber = 0),
        )
        assertEquals(
            mapOf("id" to 4, "isActive" to true, "name" to "client4", "type" to "client", "contacts" to contacts),
            queryResult.toMapValues(rowNumber = 1),
        )
    }

    @Test
    fun `select with common table expressions and let variables`() {
        val cteSubquery = "subquery".assignTo(
            QueryBuilder
                .select(
                    idField,
                    isActiveField,
                    nameField,
                    typeField,
                )
                .from(testKeyspace)
                .where(typeField.isEqualTo("client"))
                .orderBy(idField),
        )
        val dopeQuery = QueryBuilder
            .with(
                cteSubquery,
            )
            .select(
                cteSubquery.get(0),
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(mapOf("id" to 1, "isActive" to false, "name" to "client1", "type" to "client"), result["$1"])
    }
}
