package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectRemoveExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object remove expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_REMOVE(`objectField`, \"key\")",
        )
        val underTest = ObjectRemoveExpression(someObjectField(), "key".toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object remove function`() {
        val objectExpression = someObjectField()
        val attributeKey = "key".toDopeType()
        val expected = ObjectRemoveExpression(objectExpression, attributeKey)

        val actual = objectExpression.removeAttribute(attributeKey)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support object remove function string`() {
        val objectExpression = someObjectField()
        val attributeKey = "key"
        val expected = ObjectRemoveExpression(objectExpression, attributeKey.toDopeType())

        val actual = objectExpression.removeAttribute(attributeKey)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
