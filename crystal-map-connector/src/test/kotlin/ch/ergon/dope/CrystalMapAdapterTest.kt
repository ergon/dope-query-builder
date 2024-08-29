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
        val CMJsonField = CMJsonField<Number>("testName", "testPath")

        val actual: Field<NumberType> = CMJsonField.toDopeType()

        assertEquals("`${CMJsonField.path}`.`${CMJsonField.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonField with String`() {
        val CMJsonField = CMJsonField<String>("testName", "testPath")

        val actual: Field<StringType> = CMJsonField.toDopeType()

        assertEquals("`${CMJsonField.path}`.`${CMJsonField.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonField with Boolean`() {
        val CMJsonField = CMJsonField<Boolean>("testName", "testPath")

        val actual: Field<BooleanType> = CMJsonField.toDopeType()

        assertEquals("`${CMJsonField.path}`.`${CMJsonField.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonList string`() {
        val CMJsonList = CMJsonList<String>("testName", "testPath")

        val actual: Field<ArrayType<StringType>> = CMJsonList.toDopeType()

        assertEquals("`${CMJsonList.path}`.`${CMJsonList.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonList number`() {
        val CMJsonList = CMJsonList<Number>("testName", "testPath")

        val actual: Field<ArrayType<NumberType>> = CMJsonList.toDopeType()

        assertEquals("`${CMJsonList.path}`.`${CMJsonList.name}`", actual.toDopeQuery(manager).queryString)
    }

    @Test
    fun `should convert CMJsonList boolean`() {
        val CMJsonList = CMJsonList<Boolean>("testName", "testPath")

        val actual: Field<ArrayType<BooleanType>> = CMJsonList.toDopeType()

        assertEquals("`${CMJsonList.path}`.`${CMJsonList.name}`", actual.toDopeQuery(manager).queryString)
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
