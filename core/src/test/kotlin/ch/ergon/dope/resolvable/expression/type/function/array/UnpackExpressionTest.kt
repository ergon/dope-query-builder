package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someObject
import ch.ergon.dope.helper.someObjectArrayField
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.helper.someObjectSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UnpackExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support unpack on object array field`() {
        val expected = DopeQuery(
            queryString = "`objectArrayField`[*]",
        )
        val underTest = UnpackExpression(someObjectArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unpack on collection of objects`() {
        val expected = DopeQuery(
            queryString = "[`objectField`, {\"key1\" : 5, \"key2\" : \"someString\"}][*]",
        )
        val underTest = UnpackExpression(listOf(someObjectField(), someObject().toDopeType()).toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unpack`() {
        val selectObjectArray = someObjectSelectRawClause().from(someBucket())
        val expected = DopeQuery(
            queryString = "(SELECT RAW `objectField` FROM `someBucket`)[*]",
        )
        val underTest = UnpackExpression(selectObjectArray.asExpression())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unpack with named parameter`() {
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
        val underTest = UnpackExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unpack with positional parameter`() {
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
        val underTest = UnpackExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unpack extension type`() {
        val objectArray = someObjectArrayField()
        val expected = UnpackExpression(objectArray)

        val actual = objectArray.unpack()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support unpack extension collection`() {
        val objectArray = listOf(someObjectField(), someObject().toDopeType())
        val expected = UnpackExpression(objectArray.toDopeType())

        val actual = objectArray.unpack()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support unpack extension select clause`() {
        val selectClause = someObjectSelectRawClause()
        val expected = UnpackExpression(selectClause.asExpression())

        val actual = selectClause.unpack()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
