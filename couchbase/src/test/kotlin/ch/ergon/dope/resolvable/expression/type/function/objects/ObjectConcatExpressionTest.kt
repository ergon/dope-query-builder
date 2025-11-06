package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectConcatExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object concat expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_CONCAT(`field1`, `field2`, `field3`)",
        )
        val underTest =
            ObjectConcatExpression(someObjectField("field1"), someObjectField("field2"), listOf(someObjectField("field3")))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object concat function`() {
        val firstObjectExpression = someObjectField("field1")
        val secondObjectExpression = someObjectField("field2")
        val additionalObjectExpression = someObjectField("field3")
        val expected = ObjectConcatExpression(firstObjectExpression, secondObjectExpression, listOf(additionalObjectExpression))

        val actual = firstObjectExpression.concat(secondObjectExpression, additionalObjectExpression)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
