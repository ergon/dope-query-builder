package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectPairsNestedExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object pairs nested expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_PAIRS_NESTED(`objectField`)",
        )
        val underTest = ObjectPairsNestedExpression(someObjectField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object pairs nested expression with options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_PAIRS_NESTED(`objectField`, `options`)",
        )
        val underTest = ObjectPairsNestedExpression(someObjectField(), someObjectField("options"))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object pairs nested function`() {
        val objectExpression = someObjectField()
        val expected = ObjectPairsNestedExpression(objectExpression)

        val actual = objectExpression.getNestedPairs()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support object pairs nested function with options`() {
        val objectExpression = someObjectField()
        val options = someObjectField("options")
        val expected = ObjectPairsNestedExpression(objectExpression, options)

        val actual = objectExpression.getNestedPairs(options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
