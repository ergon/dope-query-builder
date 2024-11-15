package ch.ergon.dope.extensions.type.`object`

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.objectfunction.addAttribute
import ch.ergon.dope.extension.type.objectfunction.concat
import ch.ergon.dope.extension.type.objectfunction.innerPairs
import ch.ergon.dope.extension.type.objectfunction.length
import ch.ergon.dope.extension.type.objectfunction.names
import ch.ergon.dope.extension.type.objectfunction.objectField
import ch.ergon.dope.extension.type.objectfunction.pairs
import ch.ergon.dope.extension.type.objectfunction.pairsNested
import ch.ergon.dope.extension.type.objectfunction.paths
import ch.ergon.dope.extension.type.objectfunction.putAttribute
import ch.ergon.dope.extension.type.objectfunction.removeAttribute
import ch.ergon.dope.extension.type.objectfunction.renameAttribute
import ch.ergon.dope.extension.type.objectfunction.replace
import ch.ergon.dope.extension.type.objectfunction.unwrap
import ch.ergon.dope.extension.type.objectfunction.values
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectAddExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectConcatExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectFieldExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectInnerPairsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectLengthExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectNamesExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectPairsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectPairsNestedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectPathsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectPutExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectRemoveExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectRenameExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectReplaceExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectUnwrapExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.ObjectValuesExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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
        val expected = ObjectAddExpression(objectExpression.toDopeType(), newAttributeKey, newAttributeValue)

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function cmObject string type`() {
        val objectExpression = someCMObjectField()
        val newAttributeKey = "key"
        val newAttributeValue = "value".toDopeType()
        val expected = ObjectAddExpression(objectExpression.toDopeType(), newAttributeKey.toDopeType(), newAttributeValue)

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function cmObject type cmType`() {
        val objectExpression = someCMObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = someCMNumberList()
        val expected = ObjectAddExpression(objectExpression.toDopeType(), newAttributeKey, newAttributeValue.toDopeType())

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function cmObject string cmType`() {
        val objectExpression = someCMObjectField()
        val newAttributeKey = "key"
        val newAttributeValue = someCMNumberList()
        val expected = ObjectAddExpression(objectExpression.toDopeType(), newAttributeKey.toDopeType(), newAttributeValue.toDopeType())

        val actual = objectExpression.addAttribute(newAttributeKey, newAttributeValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object add function object string cmType`() {
        val objectExpression = someObjectField()
        val newAttributeKey = "key".toDopeType()
        val newAttributeValue = someCMNumberList()
        val expected = ObjectAddExpression(objectExpression, newAttributeKey, newAttributeValue.toDopeType())

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
    fun `should support object field function type`() {
        val objectExpression = someCMObjectField()
        val key = "key".toDopeType()
        val expected = ObjectFieldExpression(objectExpression.toDopeType(), key)

        val actual = objectExpression.objectField(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object field function string`() {
        val objectExpression = someCMObjectField()
        val key = "key"
        val expected = ObjectFieldExpression(objectExpression.toDopeType(), key.toDopeType())

        val actual = objectExpression.objectField(key)

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
    fun `should support object putAttribute function cmObject type type`() {
        val objectExpression = someCMObjectField()
        val key = "key".toDopeType()
        val value = "value".toDopeType()
        val expected = ObjectPutExpression(objectExpression.toDopeType(), key, value)

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
    fun `should support object removeAttribute function cmObject string`() {
        val objectExpression = someCMObjectField()
        val key = "key"
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
        val expected = ObjectReplaceExpression(objectExpression.toDopeType(), oldList.toDopeType(), newList.toDopeType())

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
