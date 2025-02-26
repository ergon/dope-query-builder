package ch.ergon.dope.resolvable.expression.type.collection

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
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotInExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support NOT IN expression`() {
        val expected = DopeQuery(
            queryString = "`numberField` NOT IN `numberArrayField`",
        )
        val underTest = NotInExpression(someNumberField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT IN expression with named parameter as value`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "\$$parameterName NOT IN `numberArrayField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = NotInExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT IN expression with positional parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "$1 NOT IN `numberArrayField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NotInExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT IN expression with named parameter as collection`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "`numberField` NOT IN \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = NotInExpression(someNumberField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT IN expression with positional parameter as collection`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "`numberField` NOT IN $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NotInExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT IN expression with named parameters as value and collection`() {
        val parameterValue = 1
        val parameterCollectionValue = listOf(1, 2, 3)
        val parameterNameA = "paramA"
        val parameterNameB = "paramB"
        val expected = DopeQuery(
            queryString = "\$$parameterNameA NOT IN \$$parameterNameB",
            DopeParameters(namedParameters = mapOf(parameterNameA to parameterValue, parameterNameB to parameterCollectionValue)),
        )
        val underTest = NotInExpression(parameterValue.asParameter(parameterNameA), parameterCollectionValue.asParameter(parameterNameB))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT IN expression with positional parameters as value and collection`() {
        val parameterValue = 1
        val parameterCollectionValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "$1 NOT IN $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterCollectionValue)),
        )
        val underTest = NotInExpression(parameterValue.asParameter(), parameterCollectionValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT IN extension type type`() {
        val value = someNumberField()
        val collection = someNumberArrayField()
        val expected = NotInExpression(value, collection)

        val actual = value.notInArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT IN extension number type`() {
        val value = 1
        val collection = someNumberArrayField()
        val expected = NotInExpression(value.toDopeType(), collection)

        val actual = value.notInArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT IN extension string type`() {
        val value = "s"
        val collection = someStringArrayField()
        val expected = NotInExpression(value.toDopeType(), collection)

        val actual = value.notInArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT IN extension boolean type`() {
        val value = true
        val collection = someBooleanArrayField()
        val expected = NotInExpression(value.toDopeType(), collection)

        val actual = value.notInArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT IN extension type type collection`() {
        val value = someNumberField()
        val collection = listOf(someNumberField(), someNumberField())
        val expected = NotInExpression(value, collection.toDopeType())

        val actual = value.notInArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT IN extension number type collection`() {
        val value = 1
        val collection = listOf(someNumberField(), someNumberField())
        val expected = NotInExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.notInArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT IN extension string type collection`() {
        val value = "s"
        val collection = listOf(someStringField(), someStringField())
        val expected = NotInExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.notInArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NOT IN extension boolean type collection`() {
        val value = true
        val collection = listOf(someBooleanField(), someBooleanField())
        val expected = NotInExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.notInArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension type select`() {
        val value = someStringField()
        val selectClause = someSelectRawClause()
        val expected = NotInExpression(value, selectClause.asExpression())

        val actual = value.notInArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension number select`() {
        val value = 1
        val selectClause = someNumberSelectRawClause()
        val expected = NotInExpression(value.toDopeType(), selectClause.asExpression())

        val actual = value.notInArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension string select`() {
        val value = "s"
        val selectClause = someStringSelectRawClause()
        val expected = NotInExpression(value.toDopeType(), selectClause.asExpression())

        val actual = value.notInArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension boolean boolean`() {
        val value = true
        val selectClause = someBooleanSelectRawClause()
        val expected = NotInExpression(value.toDopeType(), selectClause.asExpression())

        val actual = value.notInArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not in function number collection`() {
        val left = someNumber()
        val right = listOf(someNumber())
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not in function string collection`() {
        val left = someString()
        val right = listOf(someString())
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not in function boolean collection`() {
        val left = someBoolean()
        val right = listOf(someBoolean())
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not in function numberType collection`() {
        val left = someNumberField()
        val right = listOf(someNumber())
        val expected = NotInExpression(left, right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not in function stringType collection`() {
        val left = someStringField()
        val right = listOf(someString())
        val expected = NotInExpression(left, right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not in function booleanType collection`() {
        val left = someBooleanField()
        val right = listOf(someBoolean())
        val expected = NotInExpression(left, right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
