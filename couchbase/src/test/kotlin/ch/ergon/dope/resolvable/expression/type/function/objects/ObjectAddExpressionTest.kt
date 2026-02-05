package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.type.ObjectEntryPrimitive
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectAddExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object add expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_ADD(`objectField`, \"key\", \"value\")",
        )
        val underTest =
            ObjectAddExpression(someObjectField(), ObjectEntryPrimitive("key".toDopeType(), "value".toDopeType()))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object add function`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectAddExpression(objectExpression, ObjectEntryPrimitive(newAttributeKey, newAttributeValue))

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support object add function string`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key"
        val newAttributeValue = "value".toDopeType()
        val expected =
            ObjectAddExpression(objectExpression, ObjectEntryPrimitive(newAttributeKey.toDopeType(), newAttributeValue))

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
