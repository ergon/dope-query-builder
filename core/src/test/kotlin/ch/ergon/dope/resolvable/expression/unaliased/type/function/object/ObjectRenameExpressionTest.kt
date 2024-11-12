package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectRenameExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object rename expression`() {
        val expected = DopeQuery(
            queryString = "OBJECT_RENAME(`objectField`, \"name\", \"newName\")",
        )
        val underTest = ObjectRenameExpression(someObjectField(), "name".toDopeType(), "newName".toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object rename function`() {
        val objectExpression = someObjectField()
        val name = "name".toDopeType()
        val newName = "newName".toDopeType()
        val expected = ObjectRenameExpression(objectExpression, name, newName)

        val actual = objectExpression.renameAttribute(name, newName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object rename function string`() {
        val objectExpression = someObjectField()
        val name = "name"
        val newName = "newName"
        val expected = ObjectRenameExpression(objectExpression, name.toDopeType(), newName.toDopeType())

        val actual = objectExpression.renameAttribute(name, newName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
