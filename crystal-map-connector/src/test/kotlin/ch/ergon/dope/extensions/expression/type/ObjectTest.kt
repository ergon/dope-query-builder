package ch.ergon.dope.extensions.expression.type

import ch.ergon.dope.helper.DateNumberConverterInstance
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema
import java.util.*

class ObjectTest {
    class Dummy(path: String = "") : Schema {
        val objectField: CMObjectField<Dummy2> = CMObjectField(Dummy2(path), "objectField", path)
    }

    class Dummy2(path: String = "") : Schema {
        val type: CMJsonField<String> = CMJsonField("type", path)
        val otherObject: CMObjectField<Dummy3> = CMObjectField(Dummy3(path), "otherObject", path)
        val converterField = CMConverterField("converterField", path, DateNumberConverterInstance)
    }

    class Dummy3(path: String = "") : Schema {
        val something: CMJsonField<Number> = CMJsonField("something", path)
    }
//
//    @Test
//    fun `should support object get`() {
//        val expected = DopeQuery(
//            "`objectField`.`type`",
//            DopeParameters(),
//        )
//        val underTest = Dummy().objectField.getField(Dummy2::type).toDopeType()
//
//        val actual = underTest
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun `should support object get with converter`() {
//        val expected = DopeQuery(
//            "`objectField`.`converterField`",
//            DopeParameters(),
//        )
//        val field: CMConverterField<Date, Number> = Dummy().objectField.getField(Dummy2::converterField)
//        val underTest = field.toDopeType()
//
//        val actual = underTest
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun `should support object get with path`() {
//        val expected = DopeQuery(
//            "`path`.`objectField`.`type`",
//            DopeParameters(),
//        )
//        val underTest = Dummy("path").objectField.getField(Dummy2::type).toDopeType()
//
//        val actual = underTest
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun `should support object nested get`() {
//        val expected = DopeQuery(
//            "`objectField`.`otherObject`.`something`",
//            DopeParameters(),
//        )
//        val underTest = Dummy().objectField.getField(Dummy2::otherObject).getField(Dummy3::something).toDopeType()
//
//        val actual = underTest
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun `should support object nested get with path`() {
//        val expected = DopeQuery(
//            "`path`.`objectField`.`otherObject`.`something`",
//            DopeParameters(),
//        )
//        val underTest = Dummy("path").objectField.getField(Dummy2::otherObject).getField(Dummy3::something).toDopeType()
//
//        val actual = underTest
//
//        assertEquals(expected, actual)
//    }
}
