package ch.ergon.dope

import ch.ergon.dope.couchbase.resolvable.expression.type.BooleanParameter
import ch.ergon.dope.couchbase.resolvable.expression.type.NumberParameter
import ch.ergon.dope.couchbase.resolvable.expression.type.StringParameter
import ch.ergon.dope.helper.someCMConverterBooleanField
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterTest {
    @Test
    fun `should support parameter with number converter`() {
        val cmField = someCMConverterNumberField()
        val value = 1737021572L
        val date = Date(value)
        val parameterName = "testParameter"

        val actual: NumberParameter = date.asParameter(cmField.typeConverter, parameterName)

        assertEquals(value, actual.value)
        assertEquals(parameterName, actual.parameterName)
    }

    @Test
    fun `should support parameter with string converter`() {
        val cmField = someCMConverterStringField()
        val value = "1737021572000"
        val date = Date(value.toLong())
        val parameterName = "testParameter"

        val actual: StringParameter = date.asParameter(cmField.typeConverter, parameterName)

        assertEquals(value, actual.value)
        assertEquals(parameterName, actual.parameterName)
    }

    @Test
    fun `should support parameter with boolean converter`() {
        val cmField = someCMConverterBooleanField()
        val value = Date(1737021572000)
        val parameterName = "testParameter"

        val actual: BooleanParameter = value.asParameter(cmField.typeConverter, parameterName)

        assertEquals(true, actual.value)
        assertEquals(parameterName, actual.parameterName)
    }
}
