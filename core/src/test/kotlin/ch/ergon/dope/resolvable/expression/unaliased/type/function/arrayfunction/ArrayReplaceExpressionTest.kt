package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayReplaceExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_REPLACE`() {
        val expected = DopeQuery(
            "ARRAY_REPLACE(`numberArrayField`, 1, 2)",
            emptyMap(),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_REPLACE($1, 1, 2)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with second parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_REPLACE(`numberArrayField`, $1, 2)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), parameterValue.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with third parameter`() {
        val parameterValue = 2
        val expected = DopeQuery(
            "ARRAY_REPLACE(`numberArrayField`, 1, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with first and second parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            "ARRAY_REPLACE($1, $2, 2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), parameterValue2.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with first and third parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 2
        val expected = DopeQuery(
            "ARRAY_REPLACE($1, 1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), 1.toDopeType(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with all parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val parameterValue3 = 2
        val expected = DopeQuery(
            "ARRAY_REPLACE($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE extension type type type`() {
        val array = someNumberArrayField()
        val toReplace = someNumberField()
        val replaceWith = someNumberField("anotherNumberField")
        val expected = ArrayReplaceExpression(array, toReplace, replaceWith)

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension type type string`() {
        val array = someStringArrayField()
        val toReplace = someStringField()
        val replaceWith = someString()
        val expected = ArrayReplaceExpression(array, toReplace, replaceWith.toDopeType())

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension type string type`() {
        val array = someStringArrayField()
        val toReplace = someString()
        val replaceWith = someStringField()
        val expected = ArrayReplaceExpression(array, toReplace.toDopeType(), replaceWith)

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension type string string`() {
        val array = someStringArrayField()
        val toReplace = someString()
        val replaceWith = someString()
        val expected = ArrayReplaceExpression(array, toReplace.toDopeType(), replaceWith.toDopeType())

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension type type number`() {
        val array = someNumberArrayField()
        val toReplace = someNumberField()
        val replaceWith = someNumber()
        val expected = ArrayReplaceExpression(array, toReplace, replaceWith.toDopeType())

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension type number type`() {
        val array = someNumberArrayField()
        val toReplace = someNumber()
        val replaceWith = someNumberField()
        val expected = ArrayReplaceExpression(array, toReplace.toDopeType(), replaceWith)

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension type number number`() {
        val array = someNumberArrayField()
        val toReplace = someNumber()
        val replaceWith = someNumber()
        val expected = ArrayReplaceExpression(array, toReplace.toDopeType(), replaceWith.toDopeType())

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension type type boolean`() {
        val array = someBooleanArrayField()
        val toReplace = someBooleanField()
        val replaceWith = someBoolean()
        val expected = ArrayReplaceExpression(array, toReplace, replaceWith.toDopeType())

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension type boolean type`() {
        val array = someBooleanArrayField()
        val toReplace = someBoolean()
        val replaceWith = someBooleanField()
        val expected = ArrayReplaceExpression(array, toReplace.toDopeType(), replaceWith)

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension type boolean boolean`() {
        val array = someBooleanArrayField()
        val toReplace = someBoolean()
        val replaceWith = someBoolean()
        val expected = ArrayReplaceExpression(array, toReplace.toDopeType(), replaceWith.toDopeType())

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension with max`() {
        val array = someNumberArrayField()
        val toReplace = someNumberField()
        val replaceWith = someNumberField("anotherNumberField")
        val max = 1.toDopeType()
        val expected = ArrayReplaceExpression(array, toReplace, replaceWith, max)

        val actual = arrayReplace(array, toReplace, replaceWith, max)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
