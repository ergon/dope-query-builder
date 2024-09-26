package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class UnnestClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support unnest`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` UNNEST `stringArrayField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = UnnestClause(someStringArrayField(), someFromClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest`() {
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` UNNEST `stringArrayField` AS `field`",
            emptyMap(),
            emptyList(),
        )
        val underTest = AliasedUnnestClause(someStringArrayField().alias("field"), someFromClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased unnest with positional parameter`() {
        val parameterValue = listOf("value")
        val expected = DopeQuery(
            "SELECT * FROM `someBucket` UNNEST $1 AS `value`",
            emptyMap(),
            listOf(parameterValue),
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
            "SELECT * FROM `someBucket` UNNEST \$$parameterName AS `value`",
            mapOf(parameterName to parameterValue),
            emptyList(),
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
            "SELECT $1 FROM `someBucket` UNNEST $2 AS `value`",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
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
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "SELECT \$$parameterName1 FROM `someBucket` UNNEST \$$parameterName2 AS `value`",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = AliasedUnnestClause(
            parameterValue2.asParameter(parameterName2).alias("value"),
            someFromClause(parent = someSelectClause(parameterValue.asParameter(parameterName1))),
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
