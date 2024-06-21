package ch.ergon.dope

import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.AnySatisfiesExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.EverySatisfiesExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IteratorManager
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.any
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.every
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.upper
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class SatisfiesTest {

    @BeforeEach
    fun setup() {
        ParameterManager.resetCounter()
        IteratorManager.resetCounter()
    }

    @Test
    fun `should support any satisfies number`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `numberArrayField` SATISFIES (`iterator1` % 2) = 1 END",
            parameters = emptyMap(),
        )

        val actual = AnySatisfiesExpression(someNumberArrayField()) { x -> x.mod(2).isEqualTo(1) }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies string`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `stringArrayField` SATISFIES UPPER(`iterator1`) = \"A\" END",
            parameters = emptyMap(),
        )

        val actual = AnySatisfiesExpression(someStringArrayField()) { x -> upper(x).isEqualTo("A") }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies boolean`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `booleanArrayField` SATISFIES `iterator1` END",
            parameters = emptyMap(),
        )

        val actual = AnySatisfiesExpression(someBooleanArrayField()) { it }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support query with any satisfies`() {
        val expected = DopeQuery(
            queryString = "(`firstName` = \"Hans\" AND ANY `iterator1` IN `hobbies` SATISFIES `iterator1` = \"Football\" END)",
            parameters = emptyMap(),
        )

        val actual = someStringField("firstName").isEqualTo("Hans")
            .and(someStringArrayField("hobbies").any { it.isEqualTo("Football") }).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies with collection`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN [`stringField`, `stringField`] SATISFIES `iterator1` = \"something\" END",
            parameters = emptyMap(),
        )

        val actual = listOf(someStringField(), someStringField()).any { it.isEqualTo("something") }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support query any satisfies with named iterator`() {
        val expected = DopeQuery(
            queryString = "(`firstName` = \"Hans\" AND ANY `hobby` IN `hobbies` SATISFIES `hobby` = \"Football\" END)",
            parameters = emptyMap(),
        )

        val actual = someStringField("firstName").isEqualTo("Hans")
            .and(someStringArrayField("hobbies").any("hobby") { it.isEqualTo("Football") })
            .toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested any satisfies`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `stringArrayField` SATISFIES " +
                "ANY `iterator2` IN `stringArrayField` SATISFIES `iterator2` = `iterator1` END END",
            parameters = emptyMap(),
        )

        val actual = someStringArrayField().any { str1 -> someStringArrayField().any { it.isEqualTo(str1) } }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies string`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `stringArrayField` SATISFIES UPPER(`iterator1`) = \"A\" END",
            parameters = emptyMap(),
        )

        val actual = EverySatisfiesExpression(someStringArrayField()) { x -> upper(x).isEqualTo("A") }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies number`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `numberArrayField` SATISFIES (`iterator1` % 2) = 1 END",
            parameters = emptyMap(),
        )

        val actual = EverySatisfiesExpression(someNumberArrayField()) { x -> x.mod(2).isEqualTo(1) }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies boolean`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `booleanArrayField` SATISFIES `iterator1` END",
            parameters = emptyMap(),
        )

        val actual = EverySatisfiesExpression(someBooleanArrayField()) { it }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support query with every satisfies`() {
        val expected = DopeQuery(
            queryString = "(`firstName` = \"Hans\" AND EVERY `iterator1` IN `hobbies` " +
                "SATISFIES `iterator1` = \"Football\" END)",
            parameters = emptyMap(),
        )

        val actual =
            someStringField("firstName").isEqualTo("Hans").and(someStringArrayField("hobbies").every { it.isEqualTo("Football") }).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies with collection`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN [`stringField`, `stringField`] SATISFIES `iterator1` = \"something\" END",
            parameters = emptyMap(),
        )

        val actual = listOf(someStringField(), someStringField()).every { it.isEqualTo("something") }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support query every satisfies with named iterator`() {
        val expected = DopeQuery(
            queryString = "(`firstName` = \"Hans\" AND EVERY `hobby` IN `hobbies` SATISFIES `hobby` = \"Football\" END)",
            parameters = emptyMap(),
        )

        val actual = someStringField("firstName").isEqualTo("Hans")
            .and(someStringArrayField("hobbies").every("hobby") { it.isEqualTo("Football") }).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested every satisfies`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `stringArrayField` SATISFIES " +
                "EVERY `iterator2` IN `stringArrayField` SATISFIES `iterator2` = `iterator1` END END",
            parameters = emptyMap(),
        )

        val actual = someStringArrayField().every { str1 -> someStringArrayField().every { it.isEqualTo(str1) } }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mixed every and any satisfies`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `stringArrayField` SATISFIES " +
                "ANY `iterator2` IN `stringArrayField` SATISFIES `iterator2` = `iterator1` END END",
            parameters = emptyMap(),
        )

        val actual = someStringArrayField().every { str1 -> someStringArrayField().any { it.isEqualTo(str1) } }.toDopeQuery()

        assertEquals(expected, actual)
    }
}
