package ch.ergon.dope.resolvable.expression.unaliased.type.collection

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
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class WithinExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support WITHIN expression`() {
        val expected = DopeQuery(
            queryString = "`numberField` WITHIN `numberArrayField`",
        )
        val underTest = WithinExpression(someNumberField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WITHIN expression with named parameter as value`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "\$$parameterName WITHIN `numberArrayField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = WithinExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WITHIN expression with positional parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "$1 WITHIN `numberArrayField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = WithinExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WITHIN expression with named parameter as collection`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "`numberField` WITHIN \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = WithinExpression(someNumberField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WITHIN expression with positional parameter as collection`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "`numberField` WITHIN $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = WithinExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WITHIN expression with named parameters as value and collection`() {
        val parameterValue = 1
        val parameterCollectionValue = listOf(1, 2, 3)
        val parameterNameA = "paramA"
        val parameterNameB = "paramB"
        val expected = DopeQuery(
            queryString = "\$$parameterNameA WITHIN \$$parameterNameB",
            DopeParameters(namedParameters = mapOf(parameterNameA to parameterValue, parameterNameB to parameterCollectionValue)),
        )
        val underTest = WithinExpression(parameterValue.asParameter(parameterNameA), parameterCollectionValue.asParameter(parameterNameB))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WITHIN expression with positional parameters as value and collection`() {
        val parameterValue = 1
        val parameterCollectionValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "$1 WITHIN $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterCollectionValue)),
        )
        val underTest = WithinExpression(parameterValue.asParameter(), parameterCollectionValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WITHIN extension type type`() {
        val value = someNumberField()
        val collection = someNumberArrayField()
        val expected = WithinExpression(value, collection)

        val actual = value.withinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension number type`() {
        val value = 1
        val collection = someNumberArrayField()
        val expected = WithinExpression(value.toDopeType(), collection)

        val actual = value.withinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension string type`() {
        val value = "s"
        val collection = someStringArrayField()
        val expected = WithinExpression(value.toDopeType(), collection)

        val actual = value.withinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension boolean type`() {
        val value = true
        val collection = someBooleanArrayField()
        val expected = WithinExpression(value.toDopeType(), collection)

        val actual = value.withinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension type type collection`() {
        val value = someNumberField()
        val collection = listOf(someNumberField(), someNumberField())
        val expected = WithinExpression(value, collection.toDopeType())

        val actual = value.withinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension number type collection`() {
        val value = 1
        val collection = listOf(someNumberField(), someNumberField())
        val expected = WithinExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.withinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension string type collection`() {
        val value = "s"
        val collection = listOf(someStringField(), someStringField())
        val expected = WithinExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.withinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension boolean type collection`() {
        val value = true
        val collection = listOf(someBooleanField(), someBooleanField())
        val expected = WithinExpression(value.toDopeType(), collection.toDopeType())

        val actual = value.withinArray(collection)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension type select`() {
        val value = someStringField()
        val selectClause = someSelectRawClause()
        val expected = WithinExpression(value, selectClause.asExpression())

        val actual = value.withinArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension number select`() {
        val value = 1
        val selectClause = someNumberSelectRawClause()
        val expected = WithinExpression(value.toDopeType(), selectClause.asExpression())

        val actual = value.withinArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension string select`() {
        val value = "s"
        val selectClause = someStringSelectRawClause()
        val expected = WithinExpression(value.toDopeType(), selectClause.asExpression())

        val actual = value.withinArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WITHIN extension boolean select`() {
        val value = true
        val selectClause = someBooleanSelectRawClause()
        val expected = WithinExpression(value.toDopeType(), selectClause.asExpression())

        val actual = value.withinArray(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
