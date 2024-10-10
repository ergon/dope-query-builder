package ch.ergon.dope.resolvable.clause.joinHintTest

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.resolvable.clause.model.joinHint.HashOrNestedLoopHint.HASH_BUILD
import ch.ergon.dope.resolvable.clause.model.joinHint.HashOrNestedLoopHint.HASH_PROBE
import ch.ergon.dope.resolvable.clause.model.joinHint.HashOrNestedLoopHint.NESTED_LOOP
import kotlin.test.Test
import kotlin.test.assertEquals

class HashOrNestedLoopHintTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support use hash build`() {
        val expected = DopeQuery(
            "HASH (BUILD)",
        )
        val underTest = HASH_BUILD

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use hash probe`() {
        val expected = DopeQuery(
            "HASH (PROBE)",
        )
        val underTest = HASH_PROBE

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use nested loop`() {
        val expected = DopeQuery(
            "NL",
        )
        val underTest = NESTED_LOOP

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
