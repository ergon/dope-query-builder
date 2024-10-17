package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayIfNullExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_IFNULL`() {
        val array = someNumberArrayField()
        val expected = DopeQuery(
            "ARRAY_IFNULL(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayIfNullExpression(array)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_IFNULL with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_IFNULL($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayIfNullExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_IFNULL extension type`() {
        val array = someNumberArrayField()
        val expected = ArrayIfNullExpression(array)

        val actual = arrayIfNull(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_IFNULL extension select`() {
        val array = someNumberSelectRawClause()
        val expected = ArrayIfNullExpression(array.asExpression())

        val actual = arrayIfNull(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
