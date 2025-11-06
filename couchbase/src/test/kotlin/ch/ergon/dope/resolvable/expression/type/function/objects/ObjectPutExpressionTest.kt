package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectPutExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object put expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_PUT(`objectField`, \"key\", \"value\")",
        )
        val underTest = ObjectPutExpression(someObjectField(), "key".toDopeType(), "value".toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object put function`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectPutExpression(objectExpression, newAttributeKey, newAttributeValue)

        val actual = objectExpression.putAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support object put function string`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key"
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectPutExpression(objectExpression, newAttributeKey.toDopeType(), newAttributeValue)

        val actual = objectExpression.putAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
