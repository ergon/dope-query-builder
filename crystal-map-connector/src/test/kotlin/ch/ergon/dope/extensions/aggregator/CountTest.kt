package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.aggregator.count
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class CountTest {
    @Test
    fun `should support count with CMField Number`() {
        val expected = DopeQuery(
            queryString = "COUNT(`CMNumberField`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all with CMField Number`() {
        val expected = DopeQuery(
            queryString = "COUNT(ALL `CMNumberField`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMNumberField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count distinct with CMField Number`() {
        val expected = DopeQuery(
            queryString = "COUNT(DISTINCT `CMNumberField`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMNumberField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with CMField String`() {
        val expected = DopeQuery(
            queryString = "COUNT(`CMStringField`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all with CMField String`() {
        val expected = DopeQuery(
            queryString = "COUNT(ALL `CMStringField`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMStringField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count distinct with CMField String`() {
        val expected = DopeQuery(
            queryString = "COUNT(DISTINCT `CMStringField`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMStringField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with CMField Boolean`() {
        val expected = DopeQuery(
            queryString = "COUNT(`CMBooleanField`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMBooleanField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all with CMField Boolean`() {
        val expected = DopeQuery(
            queryString = "COUNT(ALL `CMBooleanField`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMBooleanField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count distinct with CMField Boolean`() {
        val expected = DopeQuery(
            queryString = "COUNT(DISTINCT `CMBooleanField`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMBooleanField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with CMList Number`() {
        val expected = DopeQuery(
            queryString = "COUNT(`CMNumberList`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMNumberList()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all with CMList Number`() {
        val expected = DopeQuery(
            queryString = "COUNT(ALL `CMNumberList`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMNumberList(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count distinct with CMList Number`() {
        val expected = DopeQuery(
            queryString = "COUNT(DISTINCT `CMNumberList`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMNumberList(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with CMList String`() {
        val expected = DopeQuery(
            queryString = "COUNT(`CMStringList`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMStringList()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all with CMList String`() {
        val expected = DopeQuery(
            queryString = "COUNT(ALL `CMStringList`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMStringList(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count distinct with CMList String`() {
        val expected = DopeQuery(
            queryString = "COUNT(DISTINCT `CMStringList`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMStringList(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with CMList Boolean`() {
        val expected = DopeQuery(
            queryString = "COUNT(`CMBooleanList`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMBooleanList()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all with CMList Boolean`() {
        val expected = DopeQuery(
            queryString = "COUNT(ALL `CMBooleanList`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMBooleanList(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count distinct with CMList Boolean`() {
        val expected = DopeQuery(
            queryString = "COUNT(DISTINCT `CMBooleanList`)",
            parameters = emptyMap(),
        )

        val actual = count(someCMBooleanList(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }
}
