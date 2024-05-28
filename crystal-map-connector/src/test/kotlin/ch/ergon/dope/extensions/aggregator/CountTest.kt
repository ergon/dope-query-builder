package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.count
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class CountTest {
    @Test
    fun `should support count with CMField Number`() {
        val actual: String = count(someCMNumberField()).toDopeQuery().queryString

        assertEquals("COUNT(`someNumberField`)", actual)
    }

    @Test
    fun `should support count all with CMField Number`() {
        val actual: String = count(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("COUNT(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support count distinct with CMField Number`() {
        val actual: String = count(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("COUNT(DISTINCT `someNumberField`)", actual)
    }

    @Test
    fun `should support count with CMField String`() {
        val actual: String = count(someCMStringField()).toDopeQuery().queryString

        assertEquals("COUNT(`someStringField`)", actual)
    }

    @Test
    fun `should support count all with CMField String`() {
        val actual: String = count(someCMStringField(), ALL).toDopeQuery().queryString

        assertEquals("COUNT(ALL `someStringField`)", actual)
    }

    @Test
    fun `should support count distinct with CMField String`() {
        val actual: String = count(someCMStringField(), DISTINCT).toDopeQuery().queryString

        assertEquals("COUNT(DISTINCT `someStringField`)", actual)
    }

    @Test
    fun `should support count with CMField Boolean`() {
        val actual: String = count(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("COUNT(`someBooleanField`)", actual)
    }

    @Test
    fun `should support count all with CMField Boolean`() {
        val actual: String = count(someCMBooleanField(), ALL).toDopeQuery().queryString

        assertEquals("COUNT(ALL `someBooleanField`)", actual)
    }

    @Test
    fun `should support count distinct with CMField Boolean`() {
        val actual: String = count(someCMBooleanField(), DISTINCT).toDopeQuery().queryString

        assertEquals("COUNT(DISTINCT `someBooleanField`)", actual)
    }

    @Test
    fun `should support count with CMList Number`() {
        val actual: String = count(someCMNumberList()).toDopeQuery().queryString

        assertEquals("COUNT(`someNumberList`)", actual)
    }

    @Test
    fun `should support count all with CMList Number`() {
        val actual: String = count(someCMNumberList(), ALL).toDopeQuery().queryString

        assertEquals("COUNT(ALL `someNumberList`)", actual)
    }

    @Test
    fun `should support count distinct with CMList Number`() {
        val actual: String = count(someCMNumberList(), DISTINCT).toDopeQuery().queryString

        assertEquals("COUNT(DISTINCT `someNumberList`)", actual)
    }

    @Test
    fun `should support count with CMList String`() {
        val actual: String = count(someCMStringList()).toDopeQuery().queryString

        assertEquals("COUNT(`someStringList`)", actual)
    }

    @Test
    fun `should support count all with CMList String`() {
        val actual: String = count(someCMStringList(), ALL).toDopeQuery().queryString

        assertEquals("COUNT(ALL `someStringList`)", actual)
    }

    @Test
    fun `should support count distinct with CMList String`() {
        val actual: String = count(someCMStringList(), DISTINCT).toDopeQuery().queryString

        assertEquals("COUNT(DISTINCT `someStringList`)", actual)
    }

    @Test
    fun `should support count with CMList Boolean`() {
        val actual: String = count(someCMBooleanList()).toDopeQuery().queryString

        assertEquals("COUNT(`someBooleanList`)", actual)
    }

    @Test
    fun `should support count all with CMList Boolean`() {
        val actual: String = count(someCMBooleanList(), ALL).toDopeQuery().queryString

        assertEquals("COUNT(ALL `someBooleanList`)", actual)
    }

    @Test
    fun `should support count distinct with CMList Boolean`() {
        val actual: String = count(someCMBooleanList(), DISTINCT).toDopeQuery().queryString

        assertEquals("COUNT(DISTINCT `someBooleanList`)", actual)
    }
}
