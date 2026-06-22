package ch.ergon.dope.extensions.expression.type.function.`object`

import ch.ergon.dope.couchbase.resolvable.expression.type.function.objects.ObjectInnerPairsExpression
import ch.ergon.dope.couchbase.resolvable.expression.type.function.objects.ObjectPairsNestedExpression
import ch.ergon.dope.couchbase.resolvable.expression.type.function.objects.ObjectPathsExpression
import ch.ergon.dope.extension.expression.type.objects.innerPairs
import ch.ergon.dope.extension.expression.type.objects.pairsNested
import ch.ergon.dope.extension.expression.type.objects.paths
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectFunctionCouchbaseTest {
    @Test
    fun `should support object innerPairs function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectInnerPairsExpression(objectExpression.toDopeType())

        val actual = objectExpression.innerPairs()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object pairsNested function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectPairsNestedExpression(objectExpression.toDopeType())

        val actual = objectExpression.pairsNested()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object pairsNested function cmObject cmObject`() {
        val objectExpression = someCMObjectField()
        val options = someCMObjectField("options")
        val expected = ObjectPairsNestedExpression(objectExpression.toDopeType(), options.toDopeType())

        val actual = objectExpression.pairsNested(options)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object pairsNested function type cmObject`() {
        val objectExpression = someObjectField()
        val options = someCMObjectField("options")
        val expected = ObjectPairsNestedExpression(objectExpression, options.toDopeType())

        val actual = objectExpression.pairsNested(options)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object paths function cmObject`() {
        val objectExpression = someCMObjectField()
        val expected = ObjectPathsExpression(objectExpression.toDopeType())

        val actual = objectExpression.paths()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object paths function cmObject cmObject`() {
        val objectExpression = someCMObjectField()
        val options = someCMObjectField("options")
        val expected = ObjectPathsExpression(objectExpression.toDopeType(), options.toDopeType())

        val actual = objectExpression.paths(options)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object paths function type cmObject`() {
        val objectExpression = someObjectField()
        val options = someCMObjectField("options")
        val expected = ObjectPathsExpression(objectExpression, options.toDopeType())

        val actual = objectExpression.paths(options)

        assertEquals(expected, actual)
    }
}
