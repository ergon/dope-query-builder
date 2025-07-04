package ch.ergon.dope.extensions.expression.type.function.type

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.type.isArray
import ch.ergon.dope.extension.expression.type.function.type.isAtom
import ch.ergon.dope.extension.expression.type.function.type.isBoolean
import ch.ergon.dope.extension.expression.type.function.type.isNumber
import ch.ergon.dope.extension.expression.type.function.type.isObject
import ch.ergon.dope.extension.expression.type.function.type.isString
import ch.ergon.dope.extension.expression.type.function.type.toArray
import ch.ergon.dope.extension.expression.type.function.type.toBool
import ch.ergon.dope.extension.expression.type.function.type.toNumber
import ch.ergon.dope.extension.expression.type.function.type.toStr
import ch.ergon.dope.extension.expression.type.function.type.typeOf
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someCMObjectList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someString
import ch.ergon.dope.resolvable.expression.type.function.type.IsArrayExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsAtomExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsBooleanExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsNumberExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsObjectExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsStringExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToArrayExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToBooleanExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToNumberExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToStringExpression
import ch.ergon.dope.resolvable.expression.type.function.type.TypeOfExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeFunctionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support isArray with CM number field`() {
        val expression = someCMNumberField()
        val expected = IsArrayExpression(expression.toDopeType())

        val actual = expression.isArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isArray with CM string field`() {
        val expression = someCMStringField()
        val expected = IsArrayExpression(expression.toDopeType())

        val actual = expression.isArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isArray with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = IsArrayExpression(expression.toDopeType())

        val actual = expression.isArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isArray with CM Object field`() {
        val expression = someCMObjectField()
        val expected = IsArrayExpression(expression.toDopeType())

        val actual = expression.isArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isArray with CM number list`() {
        val expression = someCMNumberList()
        val expected = IsArrayExpression(expression.toDopeType())

        val actual = expression.isArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isArray with CM string list`() {
        val expression = someCMStringList()
        val expected = IsArrayExpression(expression.toDopeType())

        val actual = expression.isArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isArray with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = IsArrayExpression(expression.toDopeType())

        val actual = expression.isArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isArray with CM Object list`() {
        val expression = someCMObjectList()
        val expected = IsArrayExpression(expression.toDopeType())

        val actual = expression.isArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isAtom with CM number field`() {
        val expression = someCMNumberField()
        val expected = IsAtomExpression(expression.toDopeType())

        val actual = expression.isAtom()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isAtom with CM string field`() {
        val expression = someCMStringField()
        val expected = IsAtomExpression(expression.toDopeType())

        val actual = expression.isAtom()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isAtom with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = IsAtomExpression(expression.toDopeType())

        val actual = expression.isAtom()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isAtom with CM Object field`() {
        val expression = someCMObjectField()
        val expected = IsAtomExpression(expression.toDopeType())

        val actual = expression.isAtom()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isAtom with CM number list`() {
        val expression = someCMNumberList()
        val expected = IsAtomExpression(expression.toDopeType())

        val actual = expression.isAtom()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isAtom with CM string list`() {
        val expression = someCMStringList()
        val expected = IsAtomExpression(expression.toDopeType())

        val actual = expression.isAtom()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isAtom with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = IsAtomExpression(expression.toDopeType())

        val actual = expression.isAtom()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isAtom with CM Object list`() {
        val expression = someCMObjectList()
        val expected = IsAtomExpression(expression.toDopeType())

        val actual = expression.isAtom()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isBoolean with CM number field`() {
        val expression = someCMNumberField()
        val expected = IsBooleanExpression(expression.toDopeType())

        val actual = expression.isBoolean()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isBoolean with CM string field`() {
        val expression = someCMStringField()
        val expected = IsBooleanExpression(expression.toDopeType())

        val actual = expression.isBoolean()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isBoolean with CM Object field`() {
        val expression = someCMObjectField()
        val expected = IsBooleanExpression(expression.toDopeType())

        val actual = expression.isBoolean()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isBoolean with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = IsBooleanExpression(expression.toDopeType())

        val actual = expression.isBoolean()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isBoolean with CM number list`() {
        val expression = someCMNumberList()
        val expected = IsBooleanExpression(expression.toDopeType())

        val actual = expression.isBoolean()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isBoolean with CM string list`() {
        val expression = someCMStringList()
        val expected = IsBooleanExpression(expression.toDopeType())

        val actual = expression.isBoolean()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isBoolean with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = IsBooleanExpression(expression.toDopeType())

        val actual = expression.isBoolean()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isBoolean with CM Object list`() {
        val expression = someCMObjectList()
        val expected = IsBooleanExpression(expression.toDopeType())

        val actual = expression.isBoolean()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isNumber with CM number field`() {
        val expression = someCMNumberField()
        val expected = IsNumberExpression(expression.toDopeType())

        val actual = expression.isNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isNumber with CM string field`() {
        val expression = someCMStringField()
        val expected = IsNumberExpression(expression.toDopeType())

        val actual = expression.isNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isNumber with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = IsNumberExpression(expression.toDopeType())

        val actual = expression.isNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isNumber with CM Object field`() {
        val expression = someCMObjectField()
        val expected = IsNumberExpression(expression.toDopeType())

        val actual = expression.isNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isNumber with CM number list`() {
        val expression = someCMNumberList()
        val expected = IsNumberExpression(expression.toDopeType())

        val actual = expression.isNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isNumber with CM string list`() {
        val expression = someCMStringList()
        val expected = IsNumberExpression(expression.toDopeType())

        val actual = expression.isNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isNumber with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = IsNumberExpression(expression.toDopeType())

        val actual = expression.isNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isNumber with CM Object list`() {
        val expression = someCMObjectList()
        val expected = IsNumberExpression(expression.toDopeType())

        val actual = expression.isNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isString with CM number field`() {
        val expression = someCMNumberField()
        val expected = IsStringExpression(expression.toDopeType())

        val actual = expression.isString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isString with CM string field`() {
        val expression = someCMStringField()
        val expected = IsStringExpression(expression.toDopeType())

        val actual = expression.isString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isString with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = IsStringExpression(expression.toDopeType())

        val actual = expression.isString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isString with CM Object field`() {
        val expression = someCMObjectField()
        val expected = IsStringExpression(expression.toDopeType())

        val actual = expression.isString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isString with CM number list`() {
        val expression = someCMNumberList()
        val expected = IsStringExpression(expression.toDopeType())

        val actual = expression.isString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isString with CM string list`() {
        val expression = someCMStringList()
        val expected = IsStringExpression(expression.toDopeType())

        val actual = expression.isString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isString with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = IsStringExpression(expression.toDopeType())

        val actual = expression.isString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isString with CM Object list`() {
        val expression = someCMObjectList()
        val expected = IsStringExpression(expression.toDopeType())

        val actual = expression.isString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isObject with CM number field`() {
        val expression = someCMNumberField()
        val expected = IsObjectExpression(expression.toDopeType())

        val actual = expression.isObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isObject with CM string field`() {
        val expression = someCMStringField()
        val expected = IsObjectExpression(expression.toDopeType())

        val actual = expression.isObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isObject with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = IsObjectExpression(expression.toDopeType())

        val actual = expression.isObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isObject with CM Object field`() {
        val expression = someCMObjectField()
        val expected = IsObjectExpression(expression.toDopeType())

        val actual = expression.isObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isObject with CM number list`() {
        val expression = someCMNumberList()
        val expected = IsObjectExpression(expression.toDopeType())

        val actual = expression.isObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isObject with CM string list`() {
        val expression = someCMStringList()
        val expected = IsObjectExpression(expression.toDopeType())

        val actual = expression.isObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isObject with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = IsObjectExpression(expression.toDopeType())

        val actual = expression.isObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support isObject with CM Object list`() {
        val expression = someCMObjectList()
        val expected = IsObjectExpression(expression.toDopeType())

        val actual = expression.isObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toArray with CM number field`() {
        val expression = someCMNumberField()
        val expected = ToArrayExpression(expression.toDopeType())

        val actual = expression.toArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toArray with CM string field`() {
        val expression = someCMStringField()
        val expected = ToArrayExpression(expression.toDopeType())

        val actual = expression.toArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toArray with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = ToArrayExpression(expression.toDopeType())

        val actual = expression.toArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toArray with CM Object field`() {
        val expression = someCMObjectField()
        val expected = ToArrayExpression(expression.toDopeType())

        val actual = expression.toArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toBoolean with CM number field`() {
        val expression = someCMNumberField()
        val expected = ToBooleanExpression(expression.toDopeType())

        val actual = expression.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toBoolean with CM string field`() {
        val expression = someCMStringField()
        val expected = ToBooleanExpression(expression.toDopeType())

        val actual = expression.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toBoolean with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = ToBooleanExpression(expression.toDopeType())

        val actual = expression.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toBoolean with CM Object field`() {
        val expression = someCMObjectField()
        val expected = ToBooleanExpression(expression.toDopeType())

        val actual = expression.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toBoolean with CM number list`() {
        val expression = someCMNumberList()
        val expected = ToBooleanExpression(expression.toDopeType())

        val actual = expression.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toBoolean with CM string list`() {
        val expression = someCMStringList()
        val expected = ToBooleanExpression(expression.toDopeType())

        val actual = expression.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toBoolean with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = ToBooleanExpression(expression.toDopeType())

        val actual = expression.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toBoolean with CM Object list`() {
        val expression = someCMObjectList()
        val expected = ToBooleanExpression(expression.toDopeType())

        val actual = expression.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM number field`() {
        val expression = someCMNumberField()
        val expected = ToNumberExpression(expression.toDopeType())

        val actual = expression.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM string field`() {
        val expression = someCMStringField()
        val expected = ToNumberExpression(expression.toDopeType())

        val actual = expression.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = ToNumberExpression(expression.toDopeType())

        val actual = expression.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM Object field`() {
        val expression = someCMObjectField()
        val expected = ToNumberExpression(expression.toDopeType())

        val actual = expression.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM number list`() {
        val expression = someCMNumberList()
        val expected = ToNumberExpression(expression.toDopeType())

        val actual = expression.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM string list`() {
        val expression = someCMStringList()
        val expected = ToNumberExpression(expression.toDopeType())

        val actual = expression.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = ToNumberExpression(expression.toDopeType())

        val actual = expression.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM Object list`() {
        val expression = someCMObjectList()
        val expected = ToNumberExpression(expression.toDopeType())

        val actual = expression.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM string field and string filterChars`() {
        val expression = someCMStringField()
        val filterChars = someString()
        val expected = ToNumberExpression(expression.toDopeType(), filterChars.toDopeType())

        val actual = expression.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM string field and type filterChars`() {
        val expression = someCMStringField()
        val filterChars = someString().toDopeType()
        val expected = ToNumberExpression(expression.toDopeType(), filterChars)

        val actual = expression.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with string and CM string field filterChars`() {
        val expression = someString()
        val filterChars = someCMStringField()
        val expected = ToNumberExpression(expression.toDopeType(), filterChars.toDopeType())

        val actual = expression.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with type and CM string field filterChars`() {
        val expression = someString().toDopeType()
        val filterChars = someCMStringField()
        val expected = ToNumberExpression(expression, filterChars.toDopeType())

        val actual = expression.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toNumber with CM string field and CM string field filterChars`() {
        val expression = someCMStringField()
        val filterChars = someCMStringField()
        val expected = ToNumberExpression(expression.toDopeType(), filterChars.toDopeType())

        val actual = expression.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toString with CM number field`() {
        val expression = someCMNumberField()
        val expected = ToStringExpression(expression.toDopeType())

        val actual = expression.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toString with CM string field`() {
        val expression = someCMStringField()
        val expected = ToStringExpression(expression.toDopeType())

        val actual = expression.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toString with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = ToStringExpression(expression.toDopeType())

        val actual = expression.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toString with CM Object field`() {
        val expression = someCMObjectField()
        val expected = ToStringExpression(expression.toDopeType())

        val actual = expression.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toString with CM number list`() {
        val expression = someCMNumberList()
        val expected = ToStringExpression(expression.toDopeType())

        val actual = expression.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toString with CM string list`() {
        val expression = someCMStringList()
        val expected = ToStringExpression(expression.toDopeType())

        val actual = expression.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toString with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = ToStringExpression(expression.toDopeType())

        val actual = expression.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support toString with CM Object list`() {
        val expression = someCMObjectList()
        val expected = ToStringExpression(expression.toDopeType())

        val actual = expression.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support typeOf with CM number field`() {
        val expression = someCMNumberField()
        val expected = TypeOfExpression(expression.toDopeType())

        val actual = typeOf(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support typeOf with CM string field`() {
        val expression = someCMStringField()
        val expected = TypeOfExpression(expression.toDopeType())

        val actual = typeOf(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support typeOf with CM boolean field`() {
        val expression = someCMBooleanField()
        val expected = TypeOfExpression(expression.toDopeType())

        val actual = typeOf(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support typeOf with CM Object field`() {
        val expression = someCMObjectField()
        val expected = TypeOfExpression(expression.toDopeType())

        val actual = typeOf(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support typeOf with CM number list`() {
        val expression = someCMNumberList()
        val expected = TypeOfExpression(expression.toDopeType())

        val actual = typeOf(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support typeOf with CM string list`() {
        val expression = someCMStringList()
        val expected = TypeOfExpression(expression.toDopeType())

        val actual = typeOf(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support typeOf with CM boolean list`() {
        val expression = someCMBooleanList()
        val expected = TypeOfExpression(expression.toDopeType())

        val actual = typeOf(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support typeOf with CM Object list`() {
        val expression = someCMObjectList()
        val expected = TypeOfExpression(expression.toDopeType())

        val actual = typeOf(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
