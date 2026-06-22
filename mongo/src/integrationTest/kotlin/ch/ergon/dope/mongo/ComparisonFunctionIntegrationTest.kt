package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.age
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.comparison.greatestOf
import ch.ergon.dope.resolvable.expression.type.function.comparison.leastOf
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ComparisonFunctionIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `greatest of field and literals returns the maximum`() {
        val query = QueryBuilder
            .select(greatestOf(age, 20.toDopeType(), 25.toDopeType()).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(30, result[0]["result"])
    }

    @Test
    fun `greatest of two fewer-than-field literals still picks field`() {
        val query = QueryBuilder
            .select(greatestOf(age, 100.toDopeType()).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(100, result[0]["result"])
    }

    @Test
    fun `least of field and literals returns the minimum`() {
        val query = QueryBuilder
            .select(leastOf(age, 20.toDopeType(), 25.toDopeType()).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(20, result[0]["result"])
    }

    @Test
    fun `least of field and a larger literal picks the field`() {
        val query = QueryBuilder
            .select(leastOf(age, 100.toDopeType()).alias("result"))
            .from(users)
            .where(name.isEqualTo("Mike Taylor"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(35, result[0]["result"])
    }
}
