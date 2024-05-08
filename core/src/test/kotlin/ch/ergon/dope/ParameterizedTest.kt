package ch.ergon.dope

import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class ParameterizedTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
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

        val parameters = create.select(parameter.isEqualTo(1.toNumberType())).build().parameters

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

        val parameters = create.select(parameter.isEqualTo(someNumberField())).build().parameters

        assertEquals(parameterValue, parameters["$1"])
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

        val parameters = create.select(parameter1.isEqualTo(parameter2)).where(parameter3.isNotEqualTo(someNumberField())).build().parameters

        assertEquals(parameterValue1, parameters["$1"])
        assertEquals(parameterValue2, parameters["$2"])
        assertEquals(parameterValue3, parameters["$3"])
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

        val parameters = create.select(parameter1.isEqualTo(parameter2)).where(parameter3.isNotEqualTo(someNumberField())).build().parameters

        assertEquals(parameterValue1, parameters["$1"])
        assertEquals(parameterValue2, parameters["param"])
        assertEquals(parameterValue3, parameters["$2"])
        assertEquals(3, parameters.size)
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

        val parameters = create
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
            ).build().parameters

        assertEquals(value1, parameters["$1"])
        assertEquals(value2And4, parameters[parameterName2])
        assertEquals(value3, parameters["$2"])
        assertEquals(value2And4, parameters[parameterName4])
        assertEquals(value5, parameters[parameterName5])
        assertEquals(value6, parameters["$3"])
        assertEquals(6, parameters.size)
    }
}
