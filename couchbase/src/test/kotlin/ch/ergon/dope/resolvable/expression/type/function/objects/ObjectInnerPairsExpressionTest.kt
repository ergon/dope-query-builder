package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectInnerPairsExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object inner pairs expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_INNER_PAIRS(`objectField`)",
        )
        val underTest = ObjectInnerPairsExpression(someObjectField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object inner pairs function`() {
        val objectExpression = someObjectField()
        val expected = ObjectInnerPairsExpression(objectExpression)

        val actual = objectExpression.getInnerPairs()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
