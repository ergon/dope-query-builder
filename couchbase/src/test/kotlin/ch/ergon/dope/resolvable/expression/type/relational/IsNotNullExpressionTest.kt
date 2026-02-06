package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotNullExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support is not null`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` IS NOT NULL",
        )
        val underTest = IsNotNullExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not null function`() {
        val field = someStringField()
        val expected = IsNotNullExpression(field)

        val actual = field.isNotNull()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
