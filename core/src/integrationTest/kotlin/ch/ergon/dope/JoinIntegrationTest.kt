package ch.ergon.dope

import ch.ergon.dope.helper.BaseIntegrationTest
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.meta.meta
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class JoinIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `select all orders joined on employee id`() {
        val employeeAlias = testBucket.alias("e")
        val orderAlias = testBucket.alias("o")
        val employeeNameField = Field<StringType>("name", employeeAlias.alias)
        val orderNumberField = Field<StringType>("orderNumber", orderAlias.alias)
        val orderEmployeeIdField = Field<StringType>("employee", orderAlias.alias)
        val dopeQuery = QueryBuilder()
            .select(
                employeeNameField,
                orderNumberField,
            )
            .from(
                employeeAlias,
            )
            .join(
                orderAlias,
                orderEmployeeIdField.isEqualTo(meta(employeeAlias).id),
            )
            .build()

        tryUntil {
            val actual = queryWithoutParameters(dopeQuery)

            assertEquals(5, actual.rows.size)
            assertEquals(
                mapOf("name" to "employee1", "orderNumber" to "order1"),
                actual.rows[0].contentAs<Map<String, String>>(),
            )
        }
    }
}
