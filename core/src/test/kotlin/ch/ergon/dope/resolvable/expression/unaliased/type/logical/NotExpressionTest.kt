package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class NotExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support not`() {
        val expected = DopeQuery(
            "NOT `booleanField`",
            emptyMap(),
        )
        val underTest = NotExpression(someBooleanField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "NOT $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = NotExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not function`() {
        val field = someBooleanField()
        val expected = NotExpression(field)

        val actual = not(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not function boolean`() {
        val boolean = someBoolean()
        val expected = NotExpression(boolean.toDopeType())

        val actual = not(boolean)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
