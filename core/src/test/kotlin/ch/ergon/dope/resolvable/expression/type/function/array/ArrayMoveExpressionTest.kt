package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayMoveExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_MOVE`() {
        val expected = DopeQuery(
            queryString = "ARRAY_MOVE(`numberArrayField`, 1, 2)",
        )
        val underTest = ArrayMoveExpression(someNumberArrayField(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_MOVE($1, 1, 2)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayMoveExpression(parameterValue.asParameter(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_MOVE(\$$parameterName, 1, 2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayMoveExpression(parameterValue.asParameter(parameterName), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with positional second parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "ARRAY_MOVE(`numberArrayField`, $1, 2)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayMoveExpression(someNumberArrayField(), parameterValue.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with named second parameter`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_MOVE(`numberArrayField`, \$$parameterName, 2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayMoveExpression(someNumberArrayField(), parameterValue.asParameter(parameterName), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with positional third parameter`() {
        val parameterValue = 2
        val expected = DopeQuery(
            queryString = "ARRAY_MOVE(`numberArrayField`, 1, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayMoveExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with named third parameter`() {
        val parameterValue = 2
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_MOVE(`numberArrayField`, 1, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayMoveExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with positional first and second parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            queryString = "ARRAY_MOVE($1, $2, 2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = ArrayMoveExpression(parameterValue.asParameter(), parameterValue2.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with all named parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val parameterValue3 = 2
        val parameterName = "param1"
        val parameterName2 = "param2"
        val parameterName3 = "param3"
        val expected = DopeQuery(
            queryString = "ARRAY_MOVE(\$$parameterName, \$$parameterName2, \$$parameterName3)",
            DopeParameters(
                namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2, parameterName3 to parameterValue3),
            ),
        )
        val underTest = ArrayMoveExpression(
            parameterValue.asParameter(parameterName),
            parameterValue2.asParameter(parameterName2),
            parameterValue3.asParameter(parameterName3),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE extension type type type`() {
        val array = someNumberArrayField()
        val from = 1.toDopeType()
        val to = 2.toDopeType()
        val expected = ArrayMoveExpression(array, from, to)

        val actual = arrayMove(array, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension type type number`() {
        val array = someNumberArrayField()
        val from = 1.toDopeType()
        val to = 2
        val expected = ArrayMoveExpression(array, from, to.toDopeType())

        val actual = arrayMove(array, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension type number type`() {
        val array = someNumberArrayField()
        val from = 1
        val to = 2.toDopeType()
        val expected = ArrayMoveExpression(array, from.toDopeType(), to)

        val actual = arrayMove(array, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension type number number`() {
        val array = someNumberArrayField()
        val from = 1
        val to = 2
        val expected = ArrayMoveExpression(array, from.toDopeType(), to.toDopeType())

        val actual = arrayMove(array, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension select type type`() {
        val selectClause = someSelectRawClause()
        val from = 1.toDopeType()
        val to = 2.toDopeType()
        val expected = ArrayMoveExpression(selectClause.asExpression(), from, to)

        val actual = arrayMove(selectClause, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension select type number`() {
        val selectClause = someSelectRawClause()
        val from = 1.toDopeType()
        val to = 2
        val expected = ArrayMoveExpression(selectClause.asExpression(), from, to.toDopeType())

        val actual = arrayMove(selectClause, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension select number type`() {
        val selectClause = someSelectRawClause()
        val from = 1
        val to = 2.toDopeType()
        val expected = ArrayMoveExpression(selectClause.asExpression(), from.toDopeType(), to)

        val actual = arrayMove(selectClause, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension select number number`() {
        val selectClause = someSelectRawClause()
        val from = 1
        val to = 2
        val expected = ArrayMoveExpression(selectClause.asExpression(), from.toDopeType(), to.toDopeType())

        val actual = arrayMove(selectClause, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
