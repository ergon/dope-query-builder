package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectEntryPrimitiveTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object entry`() {
        val expected = DopeQuery(
            "`stringField` : `stringField`",
            DopeParameters(),
        )
        val underTest = ObjectEntryPrimitive(someStringField(), someStringField())

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
        val underTest = ObjectEntryPrimitive(someStringField(), value.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object entry function`() {
        val key = someStringField("key")
        val value = someStringField("value")
        val expected = ObjectEntryPrimitive(key, value)

        val actual = key.toObjectEntry(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object entry function with string`() {
        val key = someString()
        val value = someStringField()
        val expected = ObjectEntryPrimitive(key.toDopeType(), value)

        val actual = key.toObjectEntry(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object entry field`() {
        val expected = DopeQuery(
            "`objectField`.`someString`",
            DopeParameters(),
        )
        val underTest = ObjectEntry<StringType>(someObjectField(), someString())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object entry field string function`() {
        val objectField = someObjectField()
        val key = someString()
        val expected = ObjectEntry<StringType>(objectField, key)

        val actual: ObjectEntry<StringType> = objectField.getString(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object entry field number function`() {
        val objectField = someObjectField()
        val key = someString()
        val expected = ObjectEntry<NumberType>(objectField, key)

        val actual: ObjectEntry<NumberType> = objectField.getNumber(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object entry field boolean function`() {
        val objectField = someObjectField()
        val key = someString()
        val expected = ObjectEntry<BooleanType>(objectField, key)

        val actual: ObjectEntry<BooleanType> = objectField.getBoolean(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object entry field object function`() {
        val objectField = someObjectField()
        val key = someString()
        val expected = ObjectEntry<ObjectType>(objectField, key)

        val actual: ObjectEntry<ObjectType> = objectField.getObject(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object entry field array of strings function`() {
        val objectField = someObjectField()
        val key = someString()
        val expected = ObjectEntry<ArrayType<StringType>>(objectField, key)

        val actual: ObjectEntry<ArrayType<StringType>> = objectField.getArray(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
