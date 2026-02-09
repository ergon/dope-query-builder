package ch.ergon.dope

import ch.ergon.dope.extension.expression.type.ObjectField
import ch.ergon.dope.extension.expression.type.ObjectList
import ch.ergon.dope.helper.DateBooleanConverterInstance
import ch.ergon.dope.helper.DateNumberConverterInstance
import ch.ergon.dope.helper.DateStringConverterInstance
import ch.ergon.dope.helper.SchemaDummy
import ch.ergon.dope.helper.someCMConverterBooleanField
import ch.ergon.dope.helper.someCMConverterBooleanList
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterNumberList
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCMConverterStringList
import ch.ergon.dope.helper.someCorruptField
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someString
import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.expression.type.BooleanParameter
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.NumberParameter
import ch.ergon.dope.resolvable.expression.type.StringParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
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
        assertEquals(UnaliasedBucket(cmJsonField.path), actual.bucket)
    }

    @Test
    fun `should convert CMJsonField with String`() {
        val cmJsonField = CMJsonField<String>("testName", "testPath")

        val actual: IField<StringType> = cmJsonField.toDopeType()

        assertEquals(cmJsonField.name, actual.name)
        assertEquals(UnaliasedBucket(cmJsonField.path), actual.bucket)
    }

    @Test
    fun `should convert CMJsonField with Boolean`() {
        val cmJsonField = CMJsonField<Boolean>("testName", "testPath")

        val actual: IField<BooleanType> = cmJsonField.toDopeType()

        assertEquals(cmJsonField.name, actual.name)
        assertEquals(UnaliasedBucket(cmJsonField.path), actual.bucket)
    }

    @Test
    fun `should convert CMJsonList string`() {
        val cmJsonList = CMJsonList<String>("testName", "testPath")

        val actual: IField<ArrayType<StringType>> = cmJsonList.toDopeType()

        assertEquals(cmJsonList.name, actual.name)
        assertEquals(UnaliasedBucket(cmJsonList.path), actual.bucket)
    }

    @Test
    fun `should convert CMJsonList number`() {
        val cmJsonList = CMJsonList<Number>("testName", "testPath")

        val actual: IField<ArrayType<NumberType>> = cmJsonList.toDopeType()

        assertEquals(cmJsonList.name, actual.name)
        assertEquals(UnaliasedBucket(cmJsonList.path), actual.bucket)
    }

    @Test
    fun `should convert CMJsonList boolean`() {
        val cmJsonList = CMJsonList<Boolean>("testName", "testPath")

        val actual: IField<ArrayType<BooleanType>> = cmJsonList.toDopeType()

        assertEquals(cmJsonList.name, actual.name)
        assertEquals(UnaliasedBucket(cmJsonList.path), actual.bucket)
    }

    @Test
    fun `should convert CMObjectField`() {
        val schema = SchemaDummy()
        val cmObjectField = CMObjectField(schema, "testName", "testPath")
        val expected = ObjectField(schema, "testName", "testPath")

        val actual = cmObjectField.toDopeType()

        assertEquals(expected, actual)
    }

    @Test
    fun `should convert CMObjectList`() {
        val schema = SchemaDummy()
        val cmObjectList = CMObjectList(schema, "testName", "testPath")
        val expected = ObjectList(schema, "testName", "testPath")

        val actual = cmObjectList.toDopeType()

        assertEquals(expected, actual)
    }

    @Test
    fun `should convert CMType without specific overload`() {
        val cmField: com.schwarz.crystalapi.schema.CMType = someCMConverterNumberField()
        val expected: IField<ValidType> = Field(cmField.name, cmField.path)

        val actual: IField<ValidType> = cmField.toDopeType()

        assertEquals(expected, actual)
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

    @Test
    fun `should support toDopeType with number converter field`() {
        val value = someDate()
        val cmField = someCMConverterNumberField()
        val expected = DateNumberConverterInstance.write(value)!!.toDopeType()

        val actual = value.toDopeType(cmField)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toDopeType with string converter field`() {
        val value = someDate()
        val cmField = someCMConverterStringField()
        val expected = DateStringConverterInstance.write(value)!!.toDopeType()

        val actual = value.toDopeType(cmField)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toDopeType with boolean converter field`() {
        val value = someDate()
        val cmField = someCMConverterBooleanField()
        val expected = DateBooleanConverterInstance.write(value)!!.toDopeType()

        val actual = value.toDopeType(cmField)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toDopeType with number converter list`() {
        val value = someDate()
        val cmList = someCMConverterNumberList()
        val expected = DateNumberConverterInstance.write(value)!!.toDopeType()

        val actual = value.toDopeType(cmList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toDopeType with string converter list`() {
        val value = someDate()
        val cmList = someCMConverterStringList()
        val expected = DateStringConverterInstance.write(value)!!.toDopeType()

        val actual = value.toDopeType(cmList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toDopeType with boolean converter list`() {
        val value = someDate()
        val cmList = someCMConverterBooleanList()
        val expected = DateBooleanConverterInstance.write(value)!!.toDopeType()

        val actual = value.toDopeType(cmList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support converter field toDopeType number`() {
        val value = someDate()
        val cmField = someCMConverterNumberField()
        val expected = DateNumberConverterInstance.write(value)!!.toDopeType()

        val actual = cmField.toDopeType(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support converter field toDopeType string`() {
        val value = someDate()
        val cmField = someCMConverterStringField()
        val expected = DateStringConverterInstance.write(value)!!.toDopeType()

        val actual = cmField.toDopeType(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support converter field toDopeType boolean`() {
        val value = someDate()
        val cmField = someCMConverterBooleanField()
        val expected = DateBooleanConverterInstance.write(value)!!.toDopeType()

        val actual = cmField.toDopeType(value)

        assertEquals(expected, actual)
    }
}
