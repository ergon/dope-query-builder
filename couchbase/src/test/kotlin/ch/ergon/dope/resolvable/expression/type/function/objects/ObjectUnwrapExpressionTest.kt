package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectUnwrapExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object unwrap expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_UNWRAP(`objectField`)",
        )
        val underTest = ObjectUnwrapExpression(someObjectField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object unwrap function`() {
        val objectExpression = someObjectField()
        val expected = ObjectUnwrapExpression(objectExpression)

        val actual = objectExpression.unwrap()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
