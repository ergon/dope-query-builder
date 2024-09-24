package ch.ergon.dope

import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCorruptField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import org.junit.jupiter.api.assertThrows
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
}
