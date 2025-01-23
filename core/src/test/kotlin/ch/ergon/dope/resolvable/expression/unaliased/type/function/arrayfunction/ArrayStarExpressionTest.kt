package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
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
    fun `should support array star`() {
        val expected = DopeQuery(
            queryString = "ARRAY_STAR(`objectArrayField`)",
        )
        val underTest = ArrayStarExpression(someObjectArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array star with collection`() {
        val objectArray = listOf(someObjectField())
        val expected = DopeQuery(
            queryString = "ARRAY_STAR([`objectField`])",
        )
        val underTest = ArrayStarExpression(objectArray.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array star with select raw clause`() {
        val selectObjectArray = someObjectSelectRawClause().from(someBucket())
        val expected = DopeQuery(
            queryString = "ARRAY_STAR((SELECT RAW `objectField` FROM `someBucket`))",
        )
        val underTest = ArrayStarExpression(selectObjectArray.asExpression())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array star with named parameter`() {
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
    fun `should support array star with positional parameter`() {
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
    fun `should support array star extension type`() {
        val objectArray = someObjectArrayField()
        val expected = ArrayStarExpression(objectArray)

        val actual = arrayStar(objectArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array star extension collection`() {
        val objectArray = listOf(someObjectField(), someObjectField())
        val expected = ArrayStarExpression(objectArray.toDopeType())

        val actual = arrayStar(objectArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array star extension select`() {
        val selectClause = someObjectSelectRawClause()
        val expected = ArrayStarExpression(selectClause.asExpression())

        val actual = arrayStar(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support get asterisk`() {
        val expected = DopeQuery(
            queryString = "`objectArrayField`[*]",
        )
        val underTest = GetAsteriskExpression(someObjectArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support get asterisk with collection`() {
        val objectArray = listOf(someObjectField())
        val expected = DopeQuery(
            queryString = "[`objectField`][*]",
        )
        val underTest = GetAsteriskExpression(objectArray.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support get asterisk with select raw clause`() {
        val selectObjectArray = someObjectSelectRawClause().from(someBucket())
        val expected = DopeQuery(
            queryString = "(SELECT RAW `objectField` FROM `someBucket`)[*]",
        )
        val underTest = GetAsteriskExpression(selectObjectArray.asExpression())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support get asterisk with named parameter`() {
        val parameterName = "objectArray"
        val parameterValue = listOf(
            mapOf("key1" to 1, "key2" to "test"),
            mapOf("key1" to 2, "key2" to "string"),
        )
        val expected = DopeQuery(
            queryString = "\$objectArray[*]",
            parameters = DopeParameters(
                namedParameters = mapOf(parameterName to parameterValue),
            ),
        )
        val underTest = GetAsteriskExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support get asterisk with positional parameter`() {
        val parameterValue = listOf(
            mapOf("key1" to 1, "key2" to "test"),
            mapOf("key1" to 2, "key2" to "string"),
        )
        val expected = DopeQuery(
            queryString = "\$1[*]",
            parameters = DopeParameters(
                positionalParameters = listOf(parameterValue),
            ),
        )
        val underTest = GetAsteriskExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support get asterisk extension type`() {
        val objectArray = someObjectArrayField()
        val expected = GetAsteriskExpression(objectArray)

        val actual = objectArray.getAsterisk()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support get asterisk extension collection`() {
        val objectArray = listOf(someObjectField(), someObjectField())
        val expected = GetAsteriskExpression(objectArray.toDopeType())

        val actual = objectArray.getAsterisk()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support get asterisk receiver extension select`() {
        val selectClause = someObjectSelectRawClause()
        val expected = GetAsteriskExpression(selectClause.asExpression())

        val actual = selectClause.getAsterisk()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
