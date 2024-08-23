package ch.ergon.dope.operators.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.AnySatisfiesExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.EverySatisfiesExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.any
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.every
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.upper
import kotlin.test.Test
import kotlin.test.assertEquals

class SatisfiesTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support any satisfies number`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `numberArrayField` SATISFIES (`iterator1` % 2) = 1 END",
            parameters = emptyMap(),
        )
        val underTest = AnySatisfiesExpression(someNumberArrayField()) { x -> x.mod(2).isEqualTo(1) }

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies string`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `stringArrayField` SATISFIES UPPER(`iterator1`) = \"A\" END",
            parameters = emptyMap(),
        )
        val underTest = AnySatisfiesExpression(someStringArrayField()) { x -> upper(x).isEqualTo("A") }

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies boolean`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `booleanArrayField` SATISFIES `iterator1` END",
            parameters = emptyMap(),
        )
        val underTest = AnySatisfiesExpression(someBooleanArrayField()) { it }

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support query with any satisfies`() {
        val expected = DopeQuery(
            queryString = "(`firstName` = \"Hans\" AND ANY `iterator1` IN `hobbies` SATISFIES `iterator1` = \"Football\" END)",
            parameters = emptyMap(),
        )

        val actual = someStringField("firstName").isEqualTo("Hans")
            .and(someStringArrayField("hobbies").any { it.isEqualTo("Football") }).toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support query with any satisfies and named iterator`() {
        val expected = DopeQuery(
            queryString = "ANY `hobby` IN `hobbies` SATISFIES `hobby` = \"Football\" END",
            parameters = emptyMap(),
        )

        val actual = someStringArrayField("hobbies").any("hobby") { it.isEqualTo("Football") }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies with collection`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN [`stringField`, `stringField`] SATISFIES `iterator1` = \"something\" END",
            parameters = emptyMap(),
        )

        val actual = listOf(someStringField(), someStringField()).any { it.isEqualTo("something") }.toDopeQuery(manager)

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
            .toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested any satisfies`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `stringArrayField` SATISFIES " +
                "ANY `iterator2` IN `stringArrayField` SATISFIES `iterator2` = `iterator1` END END",
            parameters = emptyMap(),
        )

        val actual = someStringArrayField().any { str1 -> someStringArrayField().any { it.isEqualTo(str1) } }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies string`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `stringArrayField` SATISFIES UPPER(`iterator1`) = \"A\" END",
            parameters = emptyMap(),
        )
        val underTest = EverySatisfiesExpression(someStringArrayField()) { x -> upper(x).isEqualTo("A") }

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies number`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `numberArrayField` SATISFIES (`iterator1` % 2) = 1 END",
            parameters = emptyMap(),
        )
        val underTest = EverySatisfiesExpression(someNumberArrayField()) { x -> x.mod(2).isEqualTo(1) }

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies boolean`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `booleanArrayField` SATISFIES `iterator1` END",
            parameters = emptyMap(),
        )
        val underTest = EverySatisfiesExpression(someBooleanArrayField()) { it }

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support query with every satisfies and named iterator`() {
        val expected = DopeQuery(
            queryString = "EVERY `hobby` IN `hobbies` SATISFIES `hobby` = \"Football\" END",
            parameters = emptyMap(),
        )

        val actual = someStringArrayField("hobbies").every("hobby") { it.isEqualTo("Football") }.toDopeQuery(manager)

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
            someStringField("firstName").isEqualTo("Hans").and(someStringArrayField("hobbies").every { it.isEqualTo("Football") }).toDopeQuery(
                manager,
            )

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies with collection`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN [`stringField`, `stringField`] SATISFIES `iterator1` = \"something\" END",
            parameters = emptyMap(),
        )

        val actual = listOf(someStringField(), someStringField()).every { it.isEqualTo("something") }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support query every satisfies with named iterator`() {
        val expected = DopeQuery(
            queryString = "(`firstName` = \"Hans\" AND EVERY `hobby` IN `hobbies` SATISFIES `hobby` = \"Football\" END)",
            parameters = emptyMap(),
        )

        val actual = someStringField("firstName").isEqualTo("Hans")
            .and(someStringArrayField("hobbies").every("hobby") { it.isEqualTo("Football") }).toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested every satisfies`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `stringArrayField` SATISFIES " +
                "EVERY `iterator2` IN `stringArrayField` SATISFIES `iterator2` = `iterator1` END END",
            parameters = emptyMap(),
        )

        val actual = someStringArrayField().every { str1 -> someStringArrayField().every { it.isEqualTo(str1) } }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mixed every and any satisfies`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `stringArrayField` SATISFIES " +
                "ANY `iterator2` IN `stringArrayField` SATISFIES `iterator2` = `iterator1` END END",
            parameters = emptyMap(),
        )

        val actual = someStringArrayField().every { str1 -> someStringArrayField().any { it.isEqualTo(str1) } }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
