package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import kotlin.test.Test
import kotlin.test.assertEquals

class CrystalMapAdapter {
    @Test
    fun `should convert CMJsonField with Number`() {
        val cmField = CMJsonField<Number>("testName", "testPath")

        val actual: Field<NumberType> = cmField.toDopeType()

        assertEquals("`${cmField.path}`.`${cmField.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMJsonField with String`() {
        val cmField = CMJsonField<String>("testName", "testPath")

        val actual: Field<StringType> = cmField.toDopeType()

        assertEquals("`${cmField.path}`.`${cmField.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMJsonField with Boolean`() {
        val cmField = CMJsonField<Boolean>("testName", "testPath")

        val actual: Field<BooleanType> = cmField.toDopeType()

        assertEquals("`${cmField.path}`.`${cmField.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMJsonList string`() {
        val cmList = CMJsonList<String>("testName", "testPath")

        val actual: Field<ArrayType<StringType>> = cmList.toDopeType()

        assertEquals("`${cmList.path}`.`${cmList.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMJsonList number`() {
        val cmList = CMJsonList<Number>("testName", "testPath")

        val actual: Field<ArrayType<NumberType>> = cmList.toDopeType()

        assertEquals("`${cmList.path}`.`${cmList.name}`", actual.toDopeQuery().queryString)
    }

    @Test
    fun `should convert CMJsonList boolean`() {
        val cmList = CMJsonList<Boolean>("testName", "testPath")

        val actual: Field<ArrayType<BooleanType>> = cmList.toDopeType()

        assertEquals("`${cmList.path}`.`${cmList.name}`", actual.toDopeQuery().queryString)
    }
}
