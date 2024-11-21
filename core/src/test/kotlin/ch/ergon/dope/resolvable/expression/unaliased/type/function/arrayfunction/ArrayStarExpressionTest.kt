package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectArrayField
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.helper.someObjectSelectRawClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayStarExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_STAR`() {
        val expected = DopeQuery(
            queryString = "ARRAY_STAR(`objectArrayField`)",
        )
        val underTest = ArrayStarExpression(someObjectArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_STAR with named parameter`() {
        val parameterName = "objectArray"
        val parameterValue = listOf(
            mapOf("key1" to 1, "key2" to "test"),
            mapOf("key1" to 2, "key2" to "string"),
        )
        val expected = DopeQuery(
            queryString = "ARRAY_STAR(\$objectArray)",
            parameters = DopeParameters(
                namedParameters = mapOf(parameterName to parameterValue),
            ),
        )
        val underTest = ArrayStarExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_STAR with positional parameter`() {
        val parameterValue = listOf(
            mapOf("key1" to 1, "key2" to "test"),
            mapOf("key1" to 2, "key2" to "string"),
        )
        val expected = DopeQuery(
            queryString = "ARRAY_STAR(\$1)",
            parameters = DopeParameters(
                positionalParameters = listOf(parameterValue),
            ),
        )
        val underTest = ArrayStarExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_STAR extension type`() {
        val objectArray = someObjectArrayField()
        val expected = ArrayStarExpression(objectArray)

        val actual = arrayStar(objectArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_STAR extension collection`() {
        val objectArray = listOf(someObjectField(), someObjectField())
        val expected = ArrayStarExpression(objectArray.toDopeType())

        val actual = arrayStar(objectArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_STAR extension select`() {
        val selectClause = someObjectSelectRawClause()
        val expected = ArrayStarExpression(selectClause.asExpression())

        val actual = arrayStar(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_STAR receiver extension type`() {
        val objectArray = someObjectArrayField()
        val expected = ArrayStarExpression(objectArray)

        val actual = objectArray.arrayStar()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_STAR receiver extension collection`() {
        val objectArray = listOf(someObjectField(), someObjectField())
        val expected = ArrayStarExpression(objectArray.toDopeType())

        val actual = objectArray.arrayStar()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_STAR receiver extension select`() {
        val selectClause = someObjectSelectRawClause()
        val expected = ArrayStarExpression(selectClause.asExpression())

        val actual = selectClause.arrayStar()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
