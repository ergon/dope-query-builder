package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBooleanSelectRawClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class InExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support IN expression`() {
        val expected = DopeQuery(
            queryString = "`numberField` IN `numberArrayField`",
        )
        val underTest = InExpression(someNumberField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN expression with parameter as value`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "\$param IN `numberArrayField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = InExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN expression with positional parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "$1 IN `numberArrayField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = InExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN expression with parameter as collection`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "`numberField` IN \$param",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = InExpression(someNumberField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN expression with positional parameter as collection`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "`numberField` IN $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = InExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN expression with parameter as value and collection`() {
        val parameterValue = 1
        val parameterCollectionValue = listOf(1, 2, 3)
        val parameterNameA = "paramA"
        val parameterNameB = "paramB"
        val expected = DopeQuery(
            queryString = "\$paramA IN \$paramB",
            DopeParameters(namedParameters = mapOf(parameterNameA to parameterValue, parameterNameB to parameterCollectionValue)),
        )
        val underTest = InExpression(parameterValue.asParameter(parameterNameA), parameterCollectionValue.asParameter(parameterNameB))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN expression with positional parameter as value and collection`() {
        val parameterValue = 1
        val parameterCollectionValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "$1 IN $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterCollectionValue)),
        )
        val underTest = InExpression(parameterValue.asParameter(), parameterCollectionValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support IN extension type type`() {
        val value = someNumberField()
        val collection = someNumberArrayField()
        val expected = InExpression(value, collection)

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension number type`() {
        val value = 1
        val collection = someNumberArrayField()
        val expected = InExpression(value.toDopeType(), collection)

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension string type`() {
        val value = "s"
        val collection = someStringArrayField()
        val expected = InExpression(value.toDopeType(), collection)

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension boolean type`() {
        val value = true
        val collection = someBooleanArrayField()
        val expected = InExpression(value.toDopeType(), collection)

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension type type collection`() {
        val value = someNumberField()
        val collection = listOf(someNumberField(), someNumberField())
        val expected = InExpression(value, collection.toDopeType())

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension number type collection`() {
        val value = 1
        val collection = listOf(someNumberField(), someNumberField())
        val expected = InExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension string type collection`() {
        val value = "s"
        val collection = listOf(someStringField(), someStringField())
        val expected = InExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension boolean type collection`() {
        val value = true
        val collection = listOf(someBooleanField(), someBooleanField())
        val expected = InExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.inArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension type select`() {
        val value = someStringField()
        val selectClause = someSelectRawClause()
        val expected = InExpression(value, selectClause.asExpression())

        val actual = value.inArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension number select`() {
        val value = 1
        val selectClause = someNumberSelectRawClause()
        val expected = InExpression(value.toDopeType(), selectClause.asExpression())

        val actual = value.inArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension string select`() {
        val value = "s"
        val selectClause = someStringSelectRawClause()
        val expected = InExpression(value.toDopeType(), selectClause.asExpression())

        val actual = value.inArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support IN extension boolean boolean`() {
        val value = true
        val selectClause = someBooleanSelectRawClause()
        val expected = InExpression(value.toDopeType(), selectClause.asExpression())

        val actual = value.inArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
