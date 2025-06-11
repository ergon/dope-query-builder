package ch.ergon.dope.extensions.expression.type.function.`object`

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.objects.addAttribute
import ch.ergon.dope.extension.expression.type.objects.concat
import ch.ergon.dope.extension.expression.type.objects.innerPairs
import ch.ergon.dope.extension.expression.type.objects.length
import ch.ergon.dope.extension.expression.type.objects.names
import ch.ergon.dope.extension.expression.type.objects.pairs
import ch.ergon.dope.extension.expression.type.objects.pairsNested
import ch.ergon.dope.extension.expression.type.objects.paths
import ch.ergon.dope.extension.expression.type.objects.putAttribute
import ch.ergon.dope.extension.expression.type.objects.removeAttribute
import ch.ergon.dope.extension.expression.type.objects.renameAttribute
import ch.ergon.dope.extension.expression.type.objects.replace
import ch.ergon.dope.extension.expression.type.objects.unwrap
import ch.ergon.dope.extension.expression.type.objects.values
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someNumberFieldList
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.type.ObjectEntryPrimitive
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectAddExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectInnerPairsExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectLengthExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectNamesExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPairsExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPairsNestedExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPathsExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPutExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectRemoveExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectRenameExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectReplaceExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectUnwrapExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectValuesExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectFunctionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object add function cmObject type type`() {
        val objectExpression = someCMObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = "value".toDopeType()
        val expected =
            ObjectAddExpression(objectExpression.toDopeType(), ObjectEntryPrimitive(newAttributeKey, newAttributeValue))

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function cmObject string type`() {
        val objectExpression = someCMObjectField()
        val newAttributeKey = "key"
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectAddExpression(
            objectExpression.toDopeType(),
            ObjectEntryPrimitive(newAttributeKey.toDopeType(), newAttributeValue),
        )

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function cmObject cmType type`() {
        val objectExpression = someCMObjectField()
        val newAttributeKey = someCMStringField()
        val newAttributeValue = someNumberFieldList()
        val expected = ObjectAddExpression(
            objectExpression.toDopeType(),
            ObjectEntryPrimitive(newAttributeKey.toDopeType(), newAttributeValue),
        )

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function cmObject cmString cmType`() {
        val objectExpression = someCMObjectField()
        val newAttributeKey = someCMStringField()
        val newAttributeValue = someCMNumberList()
        val expected = ObjectAddExpression(
            objectExpression.toDopeType(),
            ObjectEntryPrimitive(newAttributeKey.toDopeType(), newAttributeValue.toDopeType()),
        )

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function type string cmType`() {
        val objectExpression = someObjectField()
        val newAttributeKey = someCMStringField()
        val newAttributeValue = someCMNumberList()
        val expected = ObjectAddExpression(
            objectExpression,
            ObjectEntryPrimitive(newAttributeKey.toDopeType(), newAttributeValue.toDopeType()),
        )

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function cmObject string cmType`() {
        val objectExpression = someCMObjectField()
        val newAttributeKey = "key"
        val newAttributeValue = someCMNumberList()
        val expected = ObjectAddExpression(
            objectExpression.toDopeType(),
            ObjectEntryPrimitive(newAttributeKey.toDopeType(), newAttributeValue.toDopeType()),
        )

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function object string cmType`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = someCMNumberList()
        val expected =
            ObjectAddExpression(objectExpression, ObjectEntryPrimitive(newAttributeKey, newAttributeValue.toDopeType()))

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function cmObject type cmType`() {
        val objectExpression = someCMObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = someCMNumberList()
        val expected = ObjectAddExpression(
            objectExpression.toDopeType(),
            ObjectEntryPrimitive(newAttributeKey, newAttributeValue.toDopeType()),
        )

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object concat function cmObject cmObject`() {
        val firstObjectExpression = someCMObjectField()
        val secondObjectExpression = someCMObjectField()
        val expected = ObjectConcatExpression(firstObjectExpression.toDopeType(), secondObjectExpression.toDopeType())

        val actual = firstObjectExpression.concat(secondObjectExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object innerPairs function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectInnerPairsExpression(objectExpression.toDopeType())

        val actual = objectExpression.innerPairs()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object length function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectLengthExpression(objectExpression.toDopeType())

        val actual = objectExpression.length()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object names function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectNamesExpression(objectExpression.toDopeType())

        val actual = objectExpression.names()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object pairs function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectPairsExpression(objectExpression.toDopeType())

        val actual = objectExpression.pairs()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object pairsNested function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectPairsNestedExpression(objectExpression.toDopeType())

        val actual = objectExpression.pairsNested()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object pairsNested function cmObject cmObject`() {
        val objectExpression = someCMObjectField()
        val options = someCMObjectField("options")
        val expected = ObjectPairsNestedExpression(objectExpression.toDopeType(), options.toDopeType())

        val actual = objectExpression.pairsNested(options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object pairsNested function type cmObject`() {
        val objectExpression = someObjectField()
        val options = someCMObjectField("options")
        val expected = ObjectPairsNestedExpression(objectExpression, options.toDopeType())

        val actual = objectExpression.pairsNested(options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object paths function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectPathsExpression(objectExpression.toDopeType())

        val actual = objectExpression.paths()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object paths function cmObject cmObject`() {
        val objectExpression = someCMObjectField()
        val options = someCMObjectField("options")
        val expected = ObjectPathsExpression(objectExpression.toDopeType(), options.toDopeType())

        val actual = objectExpression.paths(options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object paths function type cmObject`() {
        val objectExpression = someObjectField()
        val options = someCMObjectField("options")
        val expected = ObjectPathsExpression(objectExpression, options.toDopeType())

        val actual = objectExpression.paths(options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function cmObject type type`() {
        val objectExpression = someCMObjectField()
        val key = "key".toDopeType()
        val value = "value".toDopeType()
        val expected = ObjectPutExpression(objectExpression.toDopeType(), key, value)

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function cmObject type cm`() {
        val objectExpression = someCMObjectField()
        val key = "key".toDopeType()
        val value = someCMStringField()
        val expected = ObjectPutExpression(objectExpression.toDopeType(), key, value.toDopeType())

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function cmObject string type`() {
        val objectExpression = someCMObjectField()
        val key = "key"
        val value = "value".toDopeType()
        val expected = ObjectPutExpression(objectExpression.toDopeType(), key.toDopeType(), value)

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function cmObject string cmType`() {
        val objectExpression = someCMObjectField()
        val key = "key"
        val value = someCMNumberList()
        val expected = ObjectPutExpression(objectExpression.toDopeType(), key.toDopeType(), value.toDopeType())

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function cmObject cmType type`() {
        val objectExpression = someCMObjectField()
        val key = someCMStringField()
        val value = someNumberFieldList()
        val expected = ObjectPutExpression(objectExpression.toDopeType(), key.toDopeType(), value)

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function cmObject cmType cmType`() {
        val objectExpression = someCMObjectField()
        val key = someCMStringField()
        val value = someCMNumberList()
        val expected = ObjectPutExpression(objectExpression.toDopeType(), key.toDopeType(), value.toDopeType())

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function type string cmType`() {
        val objectExpression = someObjectField()
        val key = "key"
        val value = someCMNumberList()
        val expected = ObjectPutExpression(objectExpression, key.toDopeType(), value.toDopeType())

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function type type cmType`() {
        val objectExpression = someObjectField()
        val key = "key".toDopeType()
        val value = someCMNumberList()
        val expected = ObjectPutExpression(objectExpression, key, value.toDopeType())

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function type cmType cmType`() {
        val objectExpression = someObjectField()
        val key = someCMStringField()
        val value = someCMNumberList()
        val expected = ObjectPutExpression(objectExpression, key.toDopeType(), value.toDopeType())

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object putAttribute function type cmType type`() {
        val objectExpression = someObjectField()
        val key = someCMStringField()
        val value = someNumberFieldList()
        val expected = ObjectPutExpression(objectExpression, key.toDopeType(), value)

        val actual = objectExpression.putAttribute(key, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object removeAttribute function cmObject string`() {
        val objectExpression = someCMObjectField()
        val key = "key"
        val expected = ObjectRemoveExpression(objectExpression.toDopeType(), key.toDopeType())

        val actual = objectExpression.removeAttribute(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object removeAttribute function cmObject cmType`() {
        val objectExpression = someCMObjectField()
        val key = someCMStringField()
        val expected = ObjectRemoveExpression(objectExpression.toDopeType(), key.toDopeType())

        val actual = objectExpression.removeAttribute(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object removeAttribute function cmObject type`() {
        val objectExpression = someCMObjectField()
        val key = "key".toDopeType()
        val expected = ObjectRemoveExpression(objectExpression.toDopeType(), key)

        val actual = objectExpression.removeAttribute(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function cmObject string string`() {
        val objectExpression = someCMObjectField()
        val oldKey = "oldKey"
        val newKey = "newKey"
        val expected = ObjectRenameExpression(objectExpression.toDopeType(), oldKey.toDopeType(), newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function cmObject type string`() {
        val objectExpression = someCMObjectField()
        val oldKey = "oldKey".toDopeType()
        val newKey = "newKey"
        val expected = ObjectRenameExpression(objectExpression.toDopeType(), oldKey, newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function cmObject string type`() {
        val objectExpression = someCMObjectField()
        val oldKey = "oldKey"
        val newKey = "newKey".toDopeType()
        val expected = ObjectRenameExpression(objectExpression.toDopeType(), oldKey.toDopeType(), newKey)

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function cmObject cmType cmType`() {
        val objectExpression = someCMObjectField()
        val oldKey = someCMStringField()
        val newKey = someCMStringField()
        val expected = ObjectRenameExpression(objectExpression.toDopeType(), oldKey.toDopeType(), newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function cmObject string cmType`() {
        val objectExpression = someCMObjectField()
        val oldKey = "oldKey"
        val newKey = someCMStringField()
        val expected = ObjectRenameExpression(objectExpression.toDopeType(), oldKey.toDopeType(), newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function cmObject cmType string`() {
        val objectExpression = someCMObjectField()
        val oldKey = someCMStringField()
        val newKey = "newKey"
        val expected = ObjectRenameExpression(objectExpression.toDopeType(), oldKey.toDopeType(), newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function cmObject cmType type`() {
        val objectExpression = someCMObjectField()
        val oldKey = "oldKey".toDopeType()
        val newKey = someCMStringField()
        val expected = ObjectRenameExpression(objectExpression.toDopeType(), oldKey, newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function cmObject type cmType`() {
        val objectExpression = someCMObjectField()
        val oldKey = someCMStringField()
        val newKey = "newKey".toDopeType()
        val expected = ObjectRenameExpression(objectExpression.toDopeType(), oldKey.toDopeType(), newKey)

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function type cmType cmType`() {
        val objectExpression = someObjectField()
        val oldKey = someCMStringField()
        val newKey = someCMStringField()
        val expected = ObjectRenameExpression(objectExpression, oldKey.toDopeType(), newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function type string cmType`() {
        val objectExpression = someObjectField()
        val oldKey = "oldKey"
        val newKey = someCMStringField()
        val expected = ObjectRenameExpression(objectExpression, oldKey.toDopeType(), newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function type cmType string`() {
        val objectExpression = someObjectField()
        val oldKey = someCMStringField()
        val newKey = "newKey"
        val expected = ObjectRenameExpression(objectExpression, oldKey.toDopeType(), newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function type cmType type`() {
        val objectExpression = someObjectField()
        val oldKey = "oldKey".toDopeType()
        val newKey = someCMStringField()
        val expected = ObjectRenameExpression(objectExpression, oldKey, newKey.toDopeType())

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function type type cmType`() {
        val objectExpression = someObjectField()
        val oldKey = someCMStringField()
        val newKey = "newKey".toDopeType()
        val expected = ObjectRenameExpression(objectExpression, oldKey.toDopeType(), newKey)

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object renameAttribute function cmObject type type`() {
        val objectExpression = someCMObjectField()
        val oldKey = "oldKey".toDopeType()
        val newKey = "newKey".toDopeType()
        val expected = ObjectRenameExpression(objectExpression.toDopeType(), oldKey, newKey)

        val actual = objectExpression.renameAttribute(oldKey, newKey)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object replace function cmObject cmType cmType`() {
        val objectExpression = someCMObjectField()
        val oldList = someCMNumberList("old")
        val newList = someCMNumberList("new")
        val expected =
            ObjectReplaceExpression(objectExpression.toDopeType(), oldList.toDopeType(), newList.toDopeType())

        val actual = objectExpression.replace(oldList, newList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object replace function cmObject type type`() {
        val objectExpression = someCMObjectField()
        val oldList = someNumberFieldList("old")
        val newList = someNumberFieldList("new")
        val expected = ObjectReplaceExpression(objectExpression.toDopeType(), oldList, newList)

        val actual = objectExpression.replace(oldList, newList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object replace function type cmType cmType`() {
        val objectExpression = someObjectField()
        val oldList = someCMNumberList("old")
        val newList = someCMNumberList("new")
        val expected = ObjectReplaceExpression(objectExpression, oldList.toDopeType(), newList.toDopeType())

        val actual = objectExpression.replace(oldList, newList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object replace function type type cmType`() {
        val objectExpression = someObjectField()
        val oldList = someNumberFieldList("old")
        val newList = someCMNumberList("new")
        val expected = ObjectReplaceExpression(objectExpression, oldList, newList.toDopeType())

        val actual = objectExpression.replace(oldList, newList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object replace function type cmType type`() {
        val objectExpression = someObjectField()
        val oldList = someCMNumberList("old")
        val newList = someNumberFieldList("new")
        val expected = ObjectReplaceExpression(objectExpression, oldList.toDopeType(), newList)

        val actual = objectExpression.replace(oldList, newList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object replace function cmObject type cmType`() {
        val objectExpression = someCMObjectField()
        val oldList = someNumberFieldList("old")
        val newList = someCMNumberList("new")
        val expected = ObjectReplaceExpression(objectExpression.toDopeType(), oldList, newList.toDopeType())

        val actual = objectExpression.replace(oldList, newList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object replace function cmObject cmType type`() {
        val objectExpression = someCMObjectField()
        val oldList = someCMNumberList("old")
        val newList = someNumberFieldList("new")
        val expected = ObjectReplaceExpression(objectExpression.toDopeType(), oldList.toDopeType(), newList)

        val actual = objectExpression.replace(oldList, newList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object unwrap function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectUnwrapExpression(objectExpression.toDopeType())

        val actual = objectExpression.unwrap()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object values function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectValuesExpression(objectExpression.toDopeType())

        val actual = objectExpression.values()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
