package ch.ergon.dope.extensions.expression.type

import ch.ergon.dope.extension.expression.type.getField
import ch.ergon.dope.helper.DateNumberConverterInstance
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectTest {
    class Dummy(path: String = "") : Schema {
        val objectField: CMObjectField<Dummy2> = CMObjectField(Dummy2(path), "objectField", path)
    }

    class Dummy2(path: String = "") : Schema {
        val type: CMJsonField<String> = CMJsonField("type", path)
        val otherObject: CMObjectField<Dummy3> = CMObjectField(Dummy3(path), "otherObject", path)
        val converterField: CMConverterField<Date, Number> = CMConverterField("converterField", path, DateNumberConverterInstance)
    }

    class Dummy3(path: String = "") : Schema {
        val something: CMJsonField<Number> = CMJsonField("something", path)
    }

    @Test
    fun `should support object get`() {
        val actual = Dummy().objectField.getField(Dummy2::type).toDopeType()

        assertEquals("type", actual.name)
    }

    @Test
    fun `should support object get with converter`() {
        val actual = Dummy().objectField.getField(Dummy2::converterField).toDopeType()

        assertEquals("converterField", actual.name)
    }

    @Test
    fun `should support object get with path`() {
        val actual = Dummy("path").objectField.getField(Dummy2::type).toDopeType()

        assertEquals("type", actual.name)
    }

    @Test
    fun `should support object nested get`() {
        val actual = Dummy().objectField.getField(Dummy2::otherObject).getField(Dummy3::something).toDopeType()

        assertEquals("something", actual.name)
    }

    @Test
    fun `should support object nested get with path`() {
        val actual = Dummy("path").objectField.getField(Dummy2::otherObject).getField(Dummy3::something).toDopeType()

        assertEquals("something", actual.name)
    }
}
