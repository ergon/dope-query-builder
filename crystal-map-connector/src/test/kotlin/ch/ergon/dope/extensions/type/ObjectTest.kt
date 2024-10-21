package ch.ergon.dope.extensions.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.get
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    class Dummy(path: String = "") : Schema {
        val objectField: CMObjectField<Dummy2> = CMObjectField(Dummy2(path), "objectField", path)
    }

    class Dummy2(path: String = "") : Schema {
        val type: CMJsonField<String> = CMJsonField("type", path)
        val otherObject: CMObjectField<Dummy3> = CMObjectField(Dummy3(path), "otherObject", path)
    }

    class Dummy3(path: String = "") : Schema {
        val something: CMJsonField<Number> = CMJsonField("something", path)
    }

    @Test
    fun `should support object get`() {
        val expected = DopeQuery(
            "`objectField`.`type`",
            DopeParameters(),
        )
        val underTest = Dummy().objectField.get { type }.toDopeType()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object get with path`() {
        val expected = DopeQuery(
            "`path`.`objectField`.`type`",
            DopeParameters(),
        )
        val underTest = Dummy("path").objectField.get { type }.toDopeType()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object nested get`() {
        val expected = DopeQuery(
            "`objectField`.`otherObject`.`something`",
            DopeParameters(),
        )
        val underTest = Dummy().objectField.get { otherObject }.get { something }.toDopeType()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object nested get with path`() {
        val expected = DopeQuery(
            "`path`.`objectField`.`otherObject`.`something`",
            DopeParameters(),
        )
        val underTest = Dummy("path").objectField.get { otherObject }.get { something }.toDopeType()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
