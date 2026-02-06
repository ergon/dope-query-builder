package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectInnerValuesExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object inner values expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_INNER_VALUES(`objectField`)",
        )
        val underTest = ObjectInnerValuesExpression(someObjectField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object inner values function`() {
        val objectExpression = someObjectField()
        val expected = ObjectInnerValuesExpression(objectExpression)

        val actual = objectExpression.getInnerValues()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
