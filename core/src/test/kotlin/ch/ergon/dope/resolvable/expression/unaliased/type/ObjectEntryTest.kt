package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectEntryTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object entry`() {
        val expected = DopeQuery(
            "`stringField` : `stringField`",
            DopeParameters(),
        )
        val underTest = ObjectEntry(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object entry with parameter`() {
        val value = "test"
        val expected = DopeQuery(
            "`stringField` : $1",
            DopeParameters(positionalParameters = listOf(value)),
        )
        val underTest = ObjectEntry(someStringField(), value.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object entry function`() {
        val expected = ObjectEntry(someStringField(), someStringField())

        val actual = someStringField().to(someStringField())

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object entry field`() {
        val expected = DopeQuery(
            "`objectField`.`someString`",
            DopeParameters(),
        )
        val underTest = ObjectEntryField<StringType>(someObjectField(), someString())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object entry field function`() {
        val objectField = someObjectField()
        val key = someString()
        val expected = ObjectEntryField<StringType>(objectField, key)

        val actual = objectField.get<StringType>(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
