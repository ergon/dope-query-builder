package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTest {
    private lateinit var create: QueryBuilder

    @BeforeEach
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should get no parameter as result`() {
        val parameters = create.select(1.isEqualTo(1)).build().parameters

        assertEquals(true, parameters.isEmpty())
    }

    @Test
    fun `should get one parameter as result`() {
        val parameter = 1.asParameter("test")

        val parameters = create.select(parameter.isEqualTo(1.toDopeType())).build().parameters

        assertEquals(1, parameters.size)
    }

    @Test
    fun `should get correct value from named parameter`() {
        val parameterName = "test"
        val parameterValue = 5
        val parameter = parameterValue.asParameter(parameterName)

        val parameters = create.select(parameter.isEqualTo(someNumberField())).build().parameters

        assertEquals(parameterValue, parameters[parameterName])
    }

    @Test
    fun `should get correct value from unnamed parameter`() {
        val parameterValue = 2
        val parameter = parameterValue.asParameter()

        val parameters = create.select(parameter.isEqualTo(someNumberField())).build().positionalParameters

        assertEquals(parameterValue, parameters[0])
    }

    @Test
    fun `should get null when there is no such parameter`() {
        val parameters = create.select(2.asParameter().isEqualTo(someNumberField())).build().parameters

        assertEquals(null, parameters["$2"])
    }

    @Test
    fun `should get correct values from multiple unnamed parameters`() {
        val parameterValue1 = 63
        val parameter1 = parameterValue1.asParameter()
        val parameterValue2 = 143
        val parameter2 = parameterValue2.asParameter()
        val parameterValue3 = 80
        val parameter3 = parameterValue3.asParameter()

        val parameters = create
            .select(parameter1.isEqualTo(parameter2))
            .where(parameter3.isNotEqualTo(someNumberField()))
            .build().positionalParameters

        assertEquals(parameterValue1, parameters[0])
        assertEquals(parameterValue2, parameters[1])
        assertEquals(parameterValue3, parameters[2])
        assertEquals(3, parameters.size)
    }

    @Test
    fun `should get correct values from mixed parameters 1`() {
        val parameterValue1 = 63
        val parameter1 = parameterValue1.asParameter()
        val parameterValue2 = 143
        val parameter2 = parameterValue2.asParameter("param")
        val parameterValue3 = 80
        val parameter3 = parameterValue3.asParameter()
        val underTest = create.select(parameter1.isEqualTo(parameter2)).where(parameter3.isNotEqualTo(someNumberField())).build()

        val parameters = underTest.parameters
        val positionalParameters = underTest.positionalParameters

        assertEquals(parameterValue1, positionalParameters[0])
        assertEquals(parameterValue3, positionalParameters[1])
        assertEquals(parameterValue2, parameters["param"])
        assertEquals(1, parameters.size)
        assertEquals(2, positionalParameters.size)
    }

    @Test
    fun `should get correct values from mixed parameters 2`() {
        val value1 = "Super Value"
        val value2And4 = false
        val value3 = true
        val value5 = "Rabbit"
        val value6 = "Void"
        val parameter1 = value1.asParameter()
        val parameterName2 = "name"
        val parameter2 = value2And4.asParameter(parameterName2)
        val parameter3 = value3.asParameter()
        val parameterName4 = "MagicNumber"
        val parameter4 = value2And4.asParameter(parameterName4)
        val parameterName5 = "superMagic"
        val parameter5 = value5.asParameter(parameterName5)
        val parameter6 = value6.asParameter()
        val underTest = create
            .select(
                parameter1,
                parameter2.or(
                    parameter3.and(
                        parameter4,
                    ),
                ).alias("one"),
                concat(
                    parameter5,
                    parameter6,
                ),
            ).build()

        val parameters = underTest.parameters
        val positionalParameters = underTest.positionalParameters

        assertEquals(value1, positionalParameters[0])
        assertEquals(value2And4, parameters[parameterName2])
        assertEquals(value3, positionalParameters[1])
        assertEquals(value2And4, parameters[parameterName4])
        assertEquals(value5, parameters[parameterName5])
        assertEquals(value6, positionalParameters[2])
        assertEquals(3, parameters.size)
        assertEquals(3, positionalParameters.size)
    }

    @Test
    fun `should get correct value inside array parameters`() {
        val parameterValue = 3
        val parameter = parameterValue.asParameter()

        val parameters = create.select(listOf(parameter).toDopeType()).build().positionalParameters

        assertEquals(parameterValue, parameters[0])
        assertEquals(1, parameters.size)
    }
}
