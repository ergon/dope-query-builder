package ch.ergon.dope

import ch.ergon.dope.helper.someCMConverterBooleanField
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCorruptField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.resolvable.expression.type.BooleanParameter
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.NumberParameter
import ch.ergon.dope.resolvable.expression.type.StringParameter
import ch.ergon.dope.resolvable.keyspace.UnaliasedKeySpace
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CrystalMapAdapterTest {
    @Test
    fun `should convert CMJsonField with Number`() {
        val cmJsonField = CMJsonField<Number>("testName", "testPath")

        val actual: IField<NumberType> = cmJsonField.toDopeType()

        assertEquals(cmJsonField.name, actual.name)
        assertEquals(UnaliasedKeySpace(cmJsonField.path), actual.keySpace)
    }

    @Test
    fun `should convert CMJsonField with String`() {
        val cmJsonField = CMJsonField<String>("testName", "testPath")

        val actual: IField<StringType> = cmJsonField.toDopeType()

        assertEquals(cmJsonField.name, actual.name)
        assertEquals(UnaliasedKeySpace(cmJsonField.path), actual.keySpace)
    }

    @Test
    fun `should convert CMJsonField with Boolean`() {
        val cmJsonField = CMJsonField<Boolean>("testName", "testPath")

        val actual: IField<BooleanType> = cmJsonField.toDopeType()

        assertEquals(cmJsonField.name, actual.name)
        assertEquals(UnaliasedKeySpace(cmJsonField.path), actual.keySpace)
    }

    @Test
    fun `should convert CMJsonList string`() {
        val cmJsonList = CMJsonList<String>("testName", "testPath")

        val actual: IField<ArrayType<StringType>> = cmJsonList.toDopeType()

        assertEquals(cmJsonList.name, actual.name)
        assertEquals(UnaliasedKeySpace(cmJsonList.path), actual.keySpace)
    }

    @Test
    fun `should convert CMJsonList number`() {
        val cmJsonList = CMJsonList<Number>("testName", "testPath")

        val actual: IField<ArrayType<NumberType>> = cmJsonList.toDopeType()

        assertEquals(cmJsonList.name, actual.name)
        assertEquals(UnaliasedKeySpace(cmJsonList.path), actual.keySpace)
    }

    @Test
    fun `should convert CMJsonList boolean`() {
        val cmJsonList = CMJsonList<Boolean>("testName", "testPath")

        val actual: IField<ArrayType<BooleanType>> = cmJsonList.toDopeType()

        assertEquals(cmJsonList.name, actual.name)
        assertEquals(UnaliasedKeySpace(cmJsonList.path), actual.keySpace)
    }

    @Test
    fun `should throw exception when resolving corrupt IField`() {
        val string = someString()
        val corruptField = someCorruptField()

        assertThrows<IllegalArgumentException> {
            string.toDopeType(corruptField)
        }
    }

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
