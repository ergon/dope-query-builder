package ch.ergon.dope.resolvable.clause.joinHintTest

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.resolvable.clause.model.joinHint.UseHashOrNestedLoopHint.HASH_BUILD
import ch.ergon.dope.resolvable.clause.model.joinHint.UseHashOrNestedLoopHint.HASH_PROBE
import ch.ergon.dope.resolvable.clause.model.joinHint.UseHashOrNestedLoopHint.NESTED_LOOP
import kotlin.test.Test
import kotlin.test.assertEquals

class UseHashOrNestedLoopHintTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support use hash build`() {
        val expected = "HASH (BUILD)"
        val underTest = HASH_BUILD

        val actual = underTest.queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use hash probe`() {
        val expected = "HASH (PROBE)"
        val underTest = HASH_PROBE

        val actual = underTest.queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use nested loop`() {
        val expected = "NL"
        val underTest = NESTED_LOOP

        val actual = underTest.queryString

        assertEquals(expected, actual)
    }
}
