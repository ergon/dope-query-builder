package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectReplaceExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object replace expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_REPLACE(`objectField`, \"key\", \"value\")",
        )
        val underTest = ObjectReplaceExpression(someObjectField(), "key".toDopeType(), "value".toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object replace function`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectReplaceExpression(objectExpression, newAttributeKey, newAttributeValue)

        val actual = objectExpression.replace(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
