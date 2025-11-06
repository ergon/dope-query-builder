package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectFieldExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object field expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_FIELD(`objectField`, \"key\")",
        )
        val underTest = ObjectFieldExpression(someObjectField(), "key".toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object field function`() {
        val objectExpression = someObjectField()
        val attributeKey = "key".toDopeType()
        val expected = ObjectFieldExpression(objectExpression, attributeKey)

        val actual = objectExpression.getField(attributeKey)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support object field function string`() {
        val objectExpression = someObjectField()
        val attributeKey = "key"
        val expected = ObjectFieldExpression(objectExpression, attributeKey.toDopeType())

        val actual = objectExpression.getField(attributeKey)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
