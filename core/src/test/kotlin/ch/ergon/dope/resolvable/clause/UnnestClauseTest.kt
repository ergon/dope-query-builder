package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class UnnestClauseTest : ParameterDependentTest {
    @Test
    fun `should support unnest`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` UNNEST `stringArrayField`",
            emptyMap(),
        )
        val underTest = UnnestClause(someStringArrayField(), someFromClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` UNNEST `stringArrayField` AS `field`",
            emptyMap(),
        )
        val underTest = AliasedUnnestClause(someStringArrayField().alias("field"), someFromClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest with parameter`() {
        val parameterValue = listOf("value")
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` UNNEST $1 AS `value`",
            mapOf("$1" to parameterValue),
        )
        val underTest = AliasedUnnestClause(parameterValue.asParameter().alias("value"), someFromClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest with parameter and parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = listOf("param")
        val expected = DopeQuery(
            "SELECT $1 FROM `someBucket` UNNEST $2 AS `value`",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = AliasedUnnestClause(
            parameterValue2.asParameter().alias("value"),
            someFromClause(parent = someSelectClause(parameterValue.asParameter())),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unnest function`() {
        val field = someNumberArrayField()
        val parentClause = someFromClause()
        val expected = UnnestClause(field, parentClause)

        val actual = parentClause.unnest(field)

        kotlin.test.assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
