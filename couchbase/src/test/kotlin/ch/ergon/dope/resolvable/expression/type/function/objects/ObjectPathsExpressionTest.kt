package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someObjectField
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectPathsExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support object paths expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_PATHS(`objectField`)",
        )
        val underTest = ObjectPathsExpression(someObjectField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object paths expression with options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "OBJECT_PATHS(`objectField`, `options`)",
        )
        val underTest = ObjectPathsExpression(someObjectField(), someObjectField("options"))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object paths function`() {
        val objectExpression = someObjectField()
        val expected = ObjectPathsExpression(objectExpression)

        val actual = objectExpression.getPaths()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support object paths function with options`() {
        val objectExpression = someObjectField()
        val options = someObjectField("options")
        val expected = ObjectPathsExpression(objectExpression, options)

        val actual = objectExpression.getPaths(options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
