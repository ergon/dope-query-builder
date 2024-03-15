package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class CrystalMapAdapter {
    @Test
    fun `should convert CMField with Number`() {
        val cmField = CMField<Number>("testName", "testPath")

        val actual: Field<NumberType> = cmField.asNumberField()

        assertEquals("${cmField.path}.${cmField.name}", actual.toQueryString())
    }

    @Test
    fun `should convert CMField with String`() {
        val cmField = CMField<String>("testName", "testPath")

        val actual: Field<StringType> = cmField.asStringField()

        assertEquals("${cmField.path}.${cmField.name}", actual.toQueryString())
    }

    @Test
    fun `should convert CMField with Boolean`() {
        val cmField = CMField<Boolean>("testName", "testPath")

        val actual: Field<BooleanType> = cmField.asBooleanField()

        assertEquals("${cmField.path}.${cmField.name}", actual.toQueryString())
    }

    @Test
    fun `should convert CMFList string`() {
        val cmList = CMList<String>("testName", "testPath")

        val actual: Field<ArrayType<StringType>> = cmList.asStringArrayField()

        assertEquals("${cmList.path}.${cmList.name}", actual.toQueryString())
    }

    @Test
    fun `should convert CMFList number`() {
        val cmList = CMList<Number>("testName", "testPath")

        val actual: Field<ArrayType<NumberType>> = cmList.asNumberArrayField()

        assertEquals("${cmList.path}.${cmList.name}", actual.toQueryString())
    }

    @Test
    fun `should convert CMFList boolean`() {
        val cmList = CMList<Boolean>("testName", "testPath")

        val actual: Field<ArrayType<BooleanType>> = cmList.asBooleanArrayField()

        assertEquals("${cmList.path}.${cmList.name}", actual.toQueryString())
    }
}
