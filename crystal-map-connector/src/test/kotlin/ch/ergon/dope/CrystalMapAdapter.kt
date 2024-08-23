package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CrystalMapAdapter {
    private lateinit var manager: DopeQueryManager

    @BeforeTest
    fun setup() {
        manager = DopeQueryManager()
    }

    @Test
    fun `should convert CMField with Number`() {
        val cmField = CMField<Number>("testName", "testPath")

        val actual: Field<NumberType> = cmField.toDopeType()

        assertEquals("`${cmField.path}`.`${cmField.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMField with String`() {
        val cmField = CMField<String>("testName", "testPath")

        val actual: Field<StringType> = cmField.toDopeType()

        assertEquals("`${cmField.path}`.`${cmField.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMField with Boolean`() {
        val cmField = CMField<Boolean>("testName", "testPath")

        val actual: Field<BooleanType> = cmField.toDopeType()

        assertEquals("`${cmField.path}`.`${cmField.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMList string`() {
        val cmList = CMList<String>("testName", "testPath")

        val actual: Field<ArrayType<StringType>> = cmList.toDopeType()

        assertEquals("`${cmList.path}`.`${cmList.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMList number`() {
        val cmList = CMList<Number>("testName", "testPath")

        val actual: Field<ArrayType<NumberType>> = cmList.toDopeType()

        assertEquals("`${cmList.path}`.`${cmList.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMList boolean`() {
        val cmList = CMList<Boolean>("testName", "testPath")

        val actual: Field<ArrayType<BooleanType>> = cmList.toDopeType()

        assertEquals("`${cmList.path}`.`${cmList.name}`", actual.toDopeQuery(manager).queryString)
    }
}
