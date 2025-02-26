package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class UnnestClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support unnest`() {
        val expected = DopeQuery(
            queryString = "SELECT * FROM `someBucket` UNNEST `stringArrayField`",
        )
        val underTest = UnnestClause(someStringArrayField(), someFromClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest`() {
        val expected = DopeQuery(
            queryString = "SELECT * FROM `someBucket` UNNEST `stringArrayField` AS `field`",
        )
        val underTest = AliasedUnnestClause(someStringArrayField().alias("field"), someFromClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest with positional parameter`() {
        val parameterValue = listOf("value")
        val expected = DopeQuery(
            queryString = "SELECT * FROM `someBucket` UNNEST $1 AS `value`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = AliasedUnnestClause(parameterValue.asParameter().alias("value"), someFromClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest with named parameter`() {
        val parameterValue = listOf("value")
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SELECT * FROM `someBucket` UNNEST \$$parameterName AS `value`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = AliasedUnnestClause(parameterValue.asParameter(parameterName).alias("value"), someFromClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest with positional parameter and positional parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = listOf("param")
        val expected = DopeQuery(
            queryString = "SELECT $1 FROM `someBucket` UNNEST $2 AS `value`",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = AliasedUnnestClause(
            parameterValue2.asParameter().alias("value"),
            someFromClause(parent = someSelectClause(parameterValue.asParameter())),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest with named parameter and named parent parameter`() {
        val parameterValue = "param"
        val parameterValue2 = listOf("param")
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "SELECT \$$parameterName FROM `someBucket` UNNEST \$$parameterName2 AS `value`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = AliasedUnnestClause(
            parameterValue2.asParameter(parameterName2).alias("value"),
            someFromClause(parent = someSelectClause(parameterValue.asParameter(parameterName))),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unnest function`() {
        val field = someNumberArrayField()
        val parentClause = someFromClause()
        val expected = UnnestClause(field, parentClause)

        val actual = parentClause.unnest(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
