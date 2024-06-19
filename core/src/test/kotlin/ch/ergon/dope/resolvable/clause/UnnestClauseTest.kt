package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class UnnestClauseTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support unnest`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` UNNEST `stringArrayField`",
            emptyMap(),
        )

        val actual = UnnestClause(someStringArrayField(), someFromClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` UNNEST `stringArrayField` AS `field`",
            emptyMap(),
        )

        val actual = AliasedUnnestClause(someStringArrayField().alias("field"), someFromClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest with parameter`() {
        val paramValue = listOf("value")
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` UNNEST $1 AS `value`",
            mapOf("$1" to paramValue),
        )

        val actual = AliasedUnnestClause(paramValue.asParameter().alias("value"), someFromClause()).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest with parameter and parent parameter`() {
        val paramValue = "param"
        val paramValue2 = listOf("param")
        val expected = DopeQuery(
            "SELECT $1 FROM `someBucket` UNNEST $2 AS `value`",
            mapOf("$1" to paramValue, "$2" to paramValue2),
        )

        val actual = AliasedUnnestClause(paramValue2.asParameter().alias("value"), someFromClause(parent = someSelectClause(paramValue.asParameter()))).build()

        assertEquals(expected, actual)
    }
}
