package ch.ergon.dope.buildTest

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
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
        val parameters = create.select(someNumber().isEqualTo(someNumber())).build().parameters

        assertEquals(DopeParameters(), parameters)
    }

    @Test
    fun `should get one parameter as result`() {
        val parameter = someNumber().asParameter(someString())

        val namedParameters = create.select(parameter.isEqualTo(someNumber().toDopeType())).build().parameters.namedParameters

        assertEquals(1, namedParameters.size)
    }

    @Test
    fun `should get correct value from named parameter`() {
        val parameterName = someString("param")
        val parameterValue = someNumber(5)
        val parameter = parameterValue.asParameter(parameterName)

        val namedParameters = create.select(parameter.isEqualTo(someNumberField())).build().parameters.namedParameters

        assertEquals(parameterValue, namedParameters[parameterName])
    }

    @Test
    fun `should get correct value from unnamed parameter`() {
        val parameterValue = someNumber(2)
        val parameter = parameterValue.asParameter()

        val positionalParameters = create.select(parameter.isEqualTo(someNumberField())).build().parameters.positionalParameters

        assertEquals(parameterValue, positionalParameters[0])
    }

    @Test
    fun `should get empty map when there is no named parameter`() {
        val parameters = create.select(someNumber(2).asParameter().isEqualTo(someNumberField())).build().parameters

        assertEquals(0, parameters.namedParameters.size)
    }

    @Test
    fun `should get correct values from multiple unnamed parameters`() {
        val parameterValue1 = someNumber(63)
        val parameter1 = parameterValue1.asParameter()
        val parameterValue2 = someNumber(143)
        val parameter2 = parameterValue2.asParameter()
        val parameterValue3 = someNumber(80)
        val parameter3 = parameterValue3.asParameter()

        val positionalParameters = create
            .select(parameter1.isEqualTo(parameter2))
            .where(parameter3.isNotEqualTo(someNumberField()))
            .build().parameters.positionalParameters

        assertEquals(parameterValue1, positionalParameters[0])
        assertEquals(parameterValue2, positionalParameters[1])
        assertEquals(parameterValue3, positionalParameters[2])
        assertEquals(3, positionalParameters.size)
    }

    @Test
    fun `should get correct values from mixed parameters 1`() {
        val parameterValue1 = someNumber(63)
        val parameter1 = parameterValue1.asParameter()
        val parameterValue2 = someNumber(143)
        val parameter2 = parameterValue2.asParameter(someString("param"))
        val parameterValue3 = someNumber(80)
        val parameter3 = parameterValue3.asParameter()
        val underTest = create.select(parameter1.isEqualTo(parameter2)).where(parameter3.isNotEqualTo(someNumberField())).build()

        val namedParameters = underTest.parameters.namedParameters
        val positionalParameters = underTest.parameters.positionalParameters

        assertEquals(parameterValue1, positionalParameters[0])
        assertEquals(parameterValue3, positionalParameters[1])
        assertEquals(parameterValue2, namedParameters["param"])
        assertEquals(1, namedParameters.size)
        assertEquals(2, positionalParameters.size)
    }

    @Test
    fun `should get correct values from mixed parameters 2`() {
        val value1 = someString("Super Value")
        val value2 = someBoolean(false)
        val value3 = someBoolean(true)
        val value4 = someBoolean(false)
        val value5 = someString("Rabbit")
        val value6 = someString("Void")
        val parameter1 = value1.asParameter()
        val parameterName2 = someString("name")
        val parameter2 = value2.asParameter(parameterName2)
        val parameter3 = value3.asParameter()
        val parameterName4 = someString("MagicNumber")
        val parameter4 = value4.asParameter(parameterName4)
        val parameterName5 = someString("superMagic")
        val parameter5 = value5.asParameter(parameterName5)
        val parameter6 = value6.asParameter()
        val underTest = create
            .select(
                parameter1,
                parameter2.or(
                    parameter3.and(
                        parameter4,
                    ),
                ).alias(someString("one")),
                concat(
                    parameter5,
                    parameter6,
                ),
            ).build()

        val namedParameters = underTest.parameters.namedParameters
        val positionalParameters = underTest.parameters.positionalParameters

        assertEquals(value1, positionalParameters[0])
        assertEquals(value2, namedParameters[parameterName2])
        assertEquals(value3, positionalParameters[1])
        assertEquals(value4, namedParameters[parameterName4])
        assertEquals(value5, namedParameters[parameterName5])
        assertEquals(value6, positionalParameters[2])
        assertEquals(3, namedParameters.size)
        assertEquals(3, positionalParameters.size)
    }

    @Test
    fun `should get correct value inside array parameters`() {
        val parameterValue = someNumber(3)
        val parameter = parameterValue.asParameter()

        val parameters = create.select(listOf(parameter).toDopeType()).build().parameters.positionalParameters

        assertEquals(parameterValue, parameters[0])
        assertEquals(1, parameters.size)
    }

    @Test
    fun `should get correct mixed parameters from query`() {
        val namedParameter1 = someString()
        val namedParameter1name = someString("namedParameter1")
        val namedParameter2 = someNumber()
        val namedParameter2name = someString("namedParameter2")
        val positionalParameter1 = someString()
        val positionalParameter2 = someBoolean()
        val positionalParameter3 = someNumber()
        val expected = DopeQuery(
            "SELECT CONCAT($$namedParameter1name, $1) WHERE $2 LIMIT $$namedParameter2name OFFSET $3",
            DopeParameters(
                mapOf(namedParameter1name to namedParameter1, namedParameter2name to namedParameter2),
                listOf(positionalParameter1, positionalParameter2, positionalParameter3),
            ),
        )

        val actual = create.select(
            concat(namedParameter1.asParameter(namedParameter1name), positionalParameter1.asParameter()),
        ).where(
            positionalParameter2.asParameter(),
        ).limit(
            namedParameter2.asParameter(namedParameter2name),
        ).offset(
            positionalParameter3.asParameter(),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should get correct mixed parameters from array`() {
        val parameterValue1 = someNumber()
        val parameterValue2 = someNumber()
        val parameterName2 = someString("namedParameter1")
        val parameterValue3 = someNumber()
        val parameterValue4 = someNumber()
        val parameterName4 = someString("namedParameter2")
        val expected = DopeQuery(
            queryString = "SELECT [$1, $$parameterName2, $2, $$parameterName4]",
            parameters = DopeParameters(
                namedParameters = mapOf(parameterName2 to parameterValue2, parameterName4 to parameterValue4),
                positionalParameters = listOf(parameterValue1, parameterValue3),
            ),
        )

        val actual = create.select(
            listOf(
                parameterValue1.asParameter(),
                parameterValue2.asParameter(parameterName2),
                parameterValue3.asParameter(),
                parameterValue4.asParameter(parameterName4),
            ).toDopeType(),
        ).build()

        assertEquals(expected, actual)
    }
}
