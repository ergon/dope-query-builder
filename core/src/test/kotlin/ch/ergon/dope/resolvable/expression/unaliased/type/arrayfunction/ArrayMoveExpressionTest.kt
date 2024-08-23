package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArrayMoveExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_MOVE`() {
        val expected = DopeQuery(
            "ARRAY_MOVE(`numberArrayField`, 1, 2)",
            emptyMap(),
            manager,
        )
        val underTest = ArrayMoveExpression(someNumberArrayField(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_MOVE($1, 1, 2)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ArrayMoveExpression(parameterValue.asParameter(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with second parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_MOVE(`numberArrayField`, $1, 2)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ArrayMoveExpression(someNumberArrayField(), parameterValue.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with third parameter`() {
        val parameterValue = 2
        val expected = DopeQuery(
            "ARRAY_MOVE(`numberArrayField`, 1, $1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ArrayMoveExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with first and second parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            "ARRAY_MOVE($1, $2, 2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = ArrayMoveExpression(parameterValue.asParameter(), parameterValue2.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with first and third parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 2
        val expected = DopeQuery(
            "ARRAY_MOVE($1, 1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = ArrayMoveExpression(parameterValue.asParameter(), 1.toDopeType(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE with all parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val parameterValue3 = 2
        val expected = DopeQuery(
            "ARRAY_MOVE($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
            manager,
        )
        val underTest = ArrayMoveExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE extension type type`() {
        val array = someNumberArrayField()
        val from = 1.toDopeType()
        val to = 2.toDopeType()
        val expected = ArrayMoveExpression(array, from, to)

        val actual = arrayMove(array, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension type number`() {
        val array = someNumberArrayField()
        val from = 1.toDopeType()
        val to = 2
        val expected = ArrayMoveExpression(array, from, to.toDopeType())

        val actual = arrayMove(array, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension number type`() {
        val array = someNumberArrayField()
        val from = 1
        val to = 2.toDopeType()
        val expected = ArrayMoveExpression(array, from.toDopeType(), to)

        val actual = arrayMove(array, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MOVE extension`() {
        val array = someNumberArrayField()
        val from = 1
        val to = 2
        val expected = ArrayMoveExpression(array, from.toDopeType(), to.toDopeType())

        val actual = arrayMove(array, from, to)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
