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
    fun `should convert CMFList`() {
        val cmList = CMList<String>("testName", "testPath")

        val actual: Field<ArrayType> = cmList.asArrayField()

        assertEquals("${cmList.path}.${cmList.name}", actual.toQueryString())
    }
}
