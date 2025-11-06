package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotValuedExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support is not valued`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` IS NOT VALUED",
        )
        val underTest = IsNotValuedExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not valued function`() {
        val field = someStringField()
        val expected = IsNotValuedExpression(field)

        val actual = field.isNotValued()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
