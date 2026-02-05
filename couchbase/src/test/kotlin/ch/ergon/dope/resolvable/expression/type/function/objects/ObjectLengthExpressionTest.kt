package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectLengthExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object length expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_LENGTH(`objectField`)",
        )
        val underTest = ObjectLengthExpression(someObjectField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object length function`() {
        val objectExpression = someObjectField()
        val expected = ObjectLengthExpression(objectExpression)

        val actual = objectExpression.getLength()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
