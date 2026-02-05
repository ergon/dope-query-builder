package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectPairsExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object pairs expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_PAIRS(`objectField`)",
        )
        val underTest = ObjectPairsExpression(someObjectField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object pairs function`() {
        val objectExpression = someObjectField()
        val expected = ObjectPairsExpression(objectExpression)

        val actual = objectExpression.getPairs()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
