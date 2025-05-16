package ch.ergon.dope.resolvable.expression.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBooleanSelectRawClause
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayReplaceExpression
import ch.ergon.dope.resolvable.expression.type.function.array.arrayReplace
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayReplaceExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_REPLACE`() {
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(`numberArrayField`, 1, 2)",
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE($1, 1, 2)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with second positional parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(`numberArrayField`, $1, 2)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), parameterValue.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with third positional parameter`() {
        val parameterValue = 2
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(`numberArrayField`, 1, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with first and second positional parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE($1, $2, 2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), parameterValue2.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with first and third positional parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 2
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE($1, 1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), 1.toDopeType(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with all positional parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val parameterValue3 = 2
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE($1, $2, $3)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2, parameterValue3)),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(\$$parameterName, 1, 2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(parameterName), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with second named parameter`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(`numberArrayField`, \$$parameterName, 2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), parameterValue.asParameter(parameterName), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with third named parameter`() {
        val parameterValue = 2
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(`numberArrayField`, 1, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with first and second named parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param1"
        val parameterValue2 = 1
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(\$$parameterName, \$$parameterName2, 2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = ArrayReplaceExpression(
            parameterValue.asParameter(parameterName),
            parameterValue2.asParameter(parameterName2),
            2.toDopeType(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with first and third named parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param1"
        val parameterValue2 = 2
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(\$$parameterName, 1, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = ArrayReplaceExpression(
            parameterValue.asParameter(parameterName),
            1.toDopeType(),
            parameterValue2.asParameter(parameterName2),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with all named parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param1"
        val parameterValue2 = 1
        val parameterName2 = "param2"
        val parameterValue3 = 2
        val parameterName3 = "param3"
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(\$$parameterName, \$$parameterName2, \$$parameterName3)",
            DopeParameters(
                namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2, parameterName3 to parameterValue3),
            ),
        )
        val underTest = ArrayReplaceExpression(
            parameterValue.asParameter(parameterName),
            parameterValue2.asParameter(parameterName2),
            parameterValue3.asParameter(parameterName3),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with mixed named and positional parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param1"
        val parameterValue2 = 1
        val parameterValue3 = 2
        val expected = DopeQuery(
            queryString = "ARRAY_REPLACE(\$$parameterName, $1, $2)",
            DopeParameters(
                namedParameters = mapOf(parameterName to parameterValue),
                positionalParameters = listOf(parameterValue2, parameterValue3),
            ),
        )
        val underTest =
            ArrayReplaceExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(), parameterValue3.asParameter())

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

    @Test
    fun `should support ARRAY_REPLACE extension select type type`() {
        val selectClause = someNumberSelectRawClause()
        val toReplace = someNumberField()
        val replaceWith = someNumberField("anotherNumberField")
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace, replaceWith)

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select type string`() {
        val selectClause = someStringSelectRawClause()
        val toReplace = someStringField()
        val replaceWith = someString()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace, replaceWith.toDopeType())

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select string type`() {
        val selectClause = someStringSelectRawClause()
        val toReplace = someString()
        val replaceWith = someStringField()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace.toDopeType(), replaceWith)

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select string string`() {
        val selectClause = someStringSelectRawClause()
        val toReplace = someString()
        val replaceWith = someString()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace.toDopeType(), replaceWith.toDopeType())

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select type number`() {
        val selectClause = someNumberSelectRawClause()
        val toReplace = someNumberField()
        val replaceWith = someNumber()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace, replaceWith.toDopeType())

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select number type`() {
        val selectClause = someNumberSelectRawClause()
        val toReplace = someNumber()
        val replaceWith = someNumberField()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace.toDopeType(), replaceWith)

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select number number`() {
        val selectClause = someNumberSelectRawClause()
        val toReplace = someNumber()
        val replaceWith = someNumber()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace.toDopeType(), replaceWith.toDopeType())

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select type boolean`() {
        val selectClause = someBooleanSelectRawClause()
        val toReplace = someBooleanField()
        val replaceWith = someBoolean()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace, replaceWith.toDopeType())

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select boolean type`() {
        val selectClause = someBooleanSelectRawClause()
        val toReplace = someBoolean()
        val replaceWith = someBooleanField()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace.toDopeType(), replaceWith)

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select boolean boolean`() {
        val selectClause = someBooleanSelectRawClause()
        val toReplace = someBoolean()
        val replaceWith = someBoolean()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace.toDopeType(), replaceWith.toDopeType())

        val actual = arrayReplace(selectClause, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPLACE extension select with max`() {
        val selectClause = someNumberSelectRawClause()
        val toReplace = someNumberField()
        val replaceWith = someNumberField("anotherNumberField")
        val max = 1.toDopeType()
        val expected = ArrayReplaceExpression(selectClause.asExpression(), toReplace, replaceWith, max)

        val actual = arrayReplace(selectClause, toReplace, replaceWith, max)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
