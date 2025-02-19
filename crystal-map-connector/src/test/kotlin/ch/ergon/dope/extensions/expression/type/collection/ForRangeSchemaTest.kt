package ch.ergon.dope.extensions.expression.type.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.collection.filter
import ch.ergon.dope.extension.expression.type.collection.filterIndexed
import ch.ergon.dope.extension.expression.type.collection.map
import ch.ergon.dope.extension.expression.type.collection.mapIndexed
import ch.ergon.dope.extension.expression.type.getField
import ch.ergon.dope.extension.expression.type.relational.isEqualTo
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema
import kotlin.test.Test
import kotlin.test.assertEquals

class ForRangeSchemaTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    class Dummy(path: String = "") : Schema {
        val objectList: CMObjectList<Dummy2> = CMObjectList(Dummy2(path), "objectList", path)
    }

    class Dummy2(path: String = "") : Schema {
        val type: CMJsonField<String> = CMJsonField("type", path)
    }

    @Test
    fun `should support array for range with schema`() {
        val expected = DopeQuery(
            queryString = "ARRAY `it`.`type` FOR `it` IN `objectList` END",
        )

        val actual = Dummy().objectList.map(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for range with schema with condition`() {
        val expected = DopeQuery(
            queryString = "ARRAY `it`.`type` FOR `it` IN `objectList` " +
                "WHEN `it`.`type` = \"test\" END",
        )

        val actual = Dummy().objectList.filter(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for range with schema`() {
        val expected = DopeQuery(
            queryString = "FIRST `it`.`type` FOR `it` IN `objectList` END",
        )

        val actual = Dummy().objectList.map(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.first().toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for range with schema with condition`() {
        val expected = DopeQuery(
            queryString = "FIRST `it`.`type` FOR `it` IN `objectList` " +
                "WHEN `it`.`type` = \"test\" END",
        )

        val actual = Dummy().objectList.filter(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.first().toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for range with schema`() {
        val expected = DopeQuery(
            queryString = "OBJECT CONCAT(\"id\", `it`.`type`):`it`.`type` FOR `it` IN `objectList` END",
        )

        val actual = Dummy().objectList.map(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toObject { schema ->
            concat("id", schema.getField(Dummy2::type).toDopeType())
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for range with schema with condition`() {
        val expected = DopeQuery(
            queryString = "OBJECT CONCAT(\"id\", `it`.`type`):`it`.`type` FOR `it` IN `objectList` " +
                "WHEN `it`.`type` = \"test\" END",
        )

        val actual = Dummy().objectList.filter(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toObject { schema ->
            concat("id", schema.getField(Dummy2::type).toDopeType())
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array for range with schema`() {
        val expected = DopeQuery(
            queryString = "ARRAY `it`.`type` FOR `i`:`it` IN `objectList` END",
        )

        val actual = Dummy().objectList.mapIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array for range with schema with condition`() {
        val expected = DopeQuery(
            queryString = "ARRAY `it`.`type` FOR `i`:`it` IN `objectList` " +
                "WHEN `it`.`type` = \"test\" END",
        )

        val actual = Dummy().objectList.filterIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed first for range with schema`() {
        val expected = DopeQuery(
            queryString = "FIRST `it`.`type` FOR `i`:`it` IN `objectList` END",
        )

        val actual = Dummy().objectList.mapIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.first().toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed first for range with schema with condition`() {
        val expected = DopeQuery(
            queryString = "FIRST `it`.`type` FOR `i`:`it` IN `objectList` " +
                "WHEN `it`.`type` = \"test\" END",
        )

        val actual = Dummy().objectList.filterIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.first().toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed object for range with schema`() {
        val expected = DopeQuery(
            queryString = "OBJECT CONCAT(\"id\", `it`.`type`):`it`.`type` FOR `i`:`it` IN `objectList` END",
        )

        val actual = Dummy().objectList.mapIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toObject { _, schema ->
            concat("id", schema.getField(Dummy2::type).toDopeType())
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed object for range with schema with condition`() {
        val expected = DopeQuery(
            queryString = "OBJECT CONCAT(\"id\", `it`.`type`):`it`.`type` FOR `i`:`it` IN `objectList` " +
                "WHEN `it`.`type` = \"test\" END",
        )

        val actual = Dummy().objectList.filterIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toObject { _, schema ->
            concat("id", schema.getField(Dummy2::type).toDopeType())
        }.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
