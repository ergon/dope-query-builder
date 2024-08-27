package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.relational.any
import ch.ergon.dope.extension.type.relational.every
import ch.ergon.dope.extension.type.relational.isEqualTo
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.IteratorManager
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema
import kotlin.test.Test
import kotlin.test.assertEquals

class SatisfiesTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    class Dummy(path: String = "") : Schema {
        val objectList: CMObjectList<Dummy2> = CMObjectList(Dummy2(path), "objectList", path)
        val stringList: CMList<String> = CMList("stringList", path)
        val numberList: CMList<Number> = CMList("numberList", path)
        val booleanList: CMList<Boolean> = CMList("booleanList", path)
    }

    class Dummy2(path: String = "") : Schema {
        val type: CMField<String> = CMField("type", path)
        val otherObjectList: CMObjectList<Dummy3> = CMObjectList(Dummy3(path), "otherObjectList", path)
    }

    class Dummy3(path: String = "") : Schema {
        val something: CMField<Number> = CMField("something", path)
    }

    @Test
    fun `should support any satisfies with schema`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `objectList` SATISFIES `iterator1`.`type` = \"some value\" END",
            parameters = emptyMap(),
        )

        val actual = Dummy().objectList.any { schema ->
            schema.field { type }.isEqualTo("some value")
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies with dope schema array`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `objectList` SATISFIES `iterator1`.`type` = \"some value\" END",
            parameters = emptyMap(),
        )

        val actual = Dummy().objectList.toDopeType().any { schema ->
            schema.field { type }.isEqualTo("some value")
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies with cmList number`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `numberList` SATISFIES (`iterator1` % 2) = 1 END",
            parameters = emptyMap(),
        )

        val actual = Dummy().numberList.any { it.mod(2).isEqualTo(1) }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies with cmList string`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `stringList` SATISFIES UPPER(`iterator1`) = \"some value\" END",
            parameters = emptyMap(),
        )

        val actual = Dummy().stringList.any { upper(it).isEqualTo("some value") }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies with cmList boolean`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `booleanList` SATISFIES `iterator1` END",
            parameters = emptyMap(),
        )

        val actual = Dummy().booleanList.any { it }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested any satisfies with schema`() {
        val expected = DopeQuery(
            queryString = "ANY `iterator1` IN `objectList` SATISFIES ANY `iterator2` IN `iterator1`.`otherObjectList` " +
                "SATISFIES `iterator2`.`something` = 3 END END",
            parameters = emptyMap(),
        )

        val actual = Dummy().objectList.any { schema ->
            schema.field { otherObjectList }.any { schemaIterator -> schemaIterator.field { something }.isEqualTo(3) }
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies with schema`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `objectList` SATISFIES `iterator1`.`type` = \"some value\" END",
            parameters = emptyMap(),
        )

        val actual = Dummy().objectList.every { schema ->
            schema.field { type }.isEqualTo("some value")
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies with dope schema array`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `objectList` SATISFIES `iterator1`.`type` = \"some value\" END",
            parameters = emptyMap(),
        )

        val actual = Dummy().objectList.toDopeType().every { schema ->
            schema.field { type }.isEqualTo("some value")
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies with cmList number`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `numberList` SATISFIES (`iterator1` % 2) = 1 END",
            parameters = emptyMap(),
        )

        val actual = Dummy().numberList.every { it.mod(2).isEqualTo(1) }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies with cmList string`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `stringList` SATISFIES UPPER(`iterator1`) = \"some value\" END",
            parameters = emptyMap(),
        )

        val actual = Dummy().stringList.every { upper(it).isEqualTo("some value") }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support every satisfies with cmList boolean`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `booleanList` SATISFIES `iterator1` END",
            parameters = emptyMap(),
        )

        val actual = Dummy().booleanList.every { it }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested every satisfies with schema`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `objectList` SATISFIES EVERY `iterator2` IN `iterator1`.`otherObjectList`" +
                " SATISFIES `iterator2`.`something` = 3 END END",
            parameters = emptyMap(),
        )

        val actual = Dummy().objectList.every { schema ->
            schema.field { otherObjectList }.every { schemaIterator -> schemaIterator.field { something }.isEqualTo(3) }
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested every and any satisfies with schema`() {
        val expected = DopeQuery(
            queryString = "EVERY `iterator1` IN `objectList` SATISFIES ANY `iterator2` IN `iterator1`.`otherObjectList`" +
                " SATISFIES `iterator2`.`something` = 3 END END",
            parameters = emptyMap(),
        )

        val actual = Dummy().objectList.every { schema ->
            schema.field { otherObjectList }.any { schemaIterator -> schemaIterator.field { something }.isEqualTo(3) }
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
