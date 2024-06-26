package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import kotlin.test.assertEquals
import kotlin.test.Test

class CrystalMapAdapter {
    @Test
    fun `should convert CMField with Number`() {
        val cmField = CMField<Number>("testName", "testPath")

        val actual: Field<NumberType> = cmField.asField()

        assertEquals("`${cmField.path}`.`${cmField.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMField with String`() {
        val cmField = CMField<String>("testName", "testPath")

        val actual: Field<StringType> = cmField.asField()

        assertEquals("`${cmField.path}`.`${cmField.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMField with Boolean`() {
        val cmField = CMField<Boolean>("testName", "testPath")

        val actual: Field<BooleanType> = cmField.asField()

        assertEquals("`${cmField.path}`.`${cmField.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMList string`() {
        val cmList = CMList<String>("testName", "testPath")

        val actual: Field<ArrayType<StringType>> = cmList.asArrayField()

        assertEquals("`${cmList.path}`.`${cmList.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMList number`() {
        val cmList = CMList<Number>("testName", "testPath")

        val actual: Field<ArrayType<NumberType>> = cmList.asArrayField()

        assertEquals("`${cmList.path}`.`${cmList.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMList boolean`() {
        val cmList = CMList<Boolean>("testName", "testPath")

        val actual: Field<ArrayType<BooleanType>> = cmList.asArrayField()

        assertEquals("`${cmList.path}`.`${cmList.name}`", actual.toDopeQuery().queryString)
    }
}
