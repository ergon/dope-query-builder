package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNullExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager<CouchbaseDopeQuery>

    @Test
    fun `should support is null`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` IS NULL",
        )
        val underTest = IsNullExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is null function`() {
        val field = someStringField()
        val expected = IsNullExpression(field)

        val actual = field.isNull()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
