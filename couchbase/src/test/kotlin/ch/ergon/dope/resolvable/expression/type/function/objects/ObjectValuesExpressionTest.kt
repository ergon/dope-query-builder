package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectValuesExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object values expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_VALUES(`objectField`)",
        )
        val underTest = ObjectValuesExpression(someObjectField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object values function`() {
        val objectExpression = someObjectField()
        val expected = ObjectValuesExpression(objectExpression)

        val actual = objectExpression.getValues()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
