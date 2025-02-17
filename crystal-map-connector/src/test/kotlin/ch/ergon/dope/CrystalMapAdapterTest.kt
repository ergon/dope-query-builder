package ch.ergon.dope

import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMConverterBooleanField
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCorruptField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.resolvable.expression.type.BooleanParameter
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.NumberParameter
import ch.ergon.dope.resolvable.expression.type.StringParameter
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import org.junit.jupiter.api.assertThrows
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class CrystalMapAdapterTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should convert CMJsonField with Number`() {
        val cmJsonField = CMJsonField<Number>("testName", "testPath")

        val actual: Field<NumberType> = cmJsonField.toDopeType()

        assertEquals("`${cmJsonField.path}`.`${cmJsonField.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonField with String`() {
        val cmJsonField = CMJsonField<String>("testName", "testPath")

        val actual: Field<StringType> = cmJsonField.toDopeType()

        assertEquals("`${cmJsonField.path}`.`${cmJsonField.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonField with Boolean`() {
        val cmJsonField = CMJsonField<Boolean>("testName", "testPath")

        val actual: Field<BooleanType> = cmJsonField.toDopeType()

        assertEquals("`${cmJsonField.path}`.`${cmJsonField.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonList string`() {
        val cmJsonList = CMJsonList<String>("testName", "testPath")

        val actual: Field<ArrayType<StringType>> = cmJsonList.toDopeType()

        assertEquals("`${cmJsonList.path}`.`${cmJsonList.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonList number`() {
        val cmJsonList = CMJsonList<Number>("testName", "testPath")

        val actual: Field<ArrayType<NumberType>> = cmJsonList.toDopeType()

        assertEquals("`${cmJsonList.path}`.`${cmJsonList.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonList boolean`() {
        val cmJsonList = CMJsonList<Boolean>("testName", "testPath")

        val actual: Field<ArrayType<BooleanType>> = cmJsonList.toDopeType()

        assertEquals("`${cmJsonList.path}`.`${cmJsonList.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should throw exception when resolving corrupt field`() {
        val string = someString()
        val corruptField = someCorruptField()

        assertThrows<IllegalArgumentException> {
            string.toDopeType(corruptField)
        }
    }

    @Test
    fun `should support parameter with number converter`() {
        val cmField = someCMConverterNumberField()
        val value = Date(1737021572000)
        val expected = DopeQuery(
            queryString = "\$testParameter",
            parameters = DopeParameters(
                namedParameters = mapOf("testParameter" to 1737021572L),
            ),
        )

        val actual: NumberParameter = value.asParameter(cmField.typeConverter, "testParameter")

        assertEquals(expected, actual.toDopeQuery(manager))
    }

    @Test
    fun `should support parameter with string converter`() {
        val cmField = someCMConverterStringField()

        val value = Date(1737021572000)
        val expected = DopeQuery(
            queryString = "\$testParameter",
            parameters = DopeParameters(
                namedParameters = mapOf("testParameter" to "1737021572"),
            ),
        )

        val actual: StringParameter = value.asParameter(cmField.typeConverter, "testParameter")

        assertEquals(expected, actual.toDopeQuery(manager))
    }

    @Test
    fun `should support parameter with boolean converter`() {
        val cmField = someCMConverterBooleanField()
        val value = Date(1737021572000)
        val expected = DopeQuery(
            queryString = "\$testParameter",
            parameters = DopeParameters(
                namedParameters = mapOf("testParameter" to true),
            ),
        )

        val actual: BooleanParameter = value.asParameter(cmField.typeConverter, "testParameter")

        assertEquals(expected, actual.toDopeQuery(manager))
    }
}
