package ch.ergon.dope.extensions.type.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.stringfunction.initCap
import ch.ergon.dope.extension.type.stringfunction.title
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.InitCapExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.TitleExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class InitCapTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support InitCap with CM string`() {
        val string = someCMStringField()
        val expected = InitCapExpression(string.toDopeType())

        val actual = initCap(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Title with CM string`() {
        val string = someCMStringField()
        val expected = TitleExpression(string.toDopeType())

        val actual = title(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
