package ch.ergon.dope.extensions.expression.type.range

import ch.ergon.dope.extension.expression.type.ObjectField
import ch.ergon.dope.extension.expression.type.function.string.concat
import ch.ergon.dope.extension.expression.type.getField
import ch.ergon.dope.extension.expression.type.range.filter
import ch.ergon.dope.extension.expression.type.range.filterIndexed
import ch.ergon.dope.extension.expression.type.range.map
import ch.ergon.dope.extension.expression.type.range.mapIndexed
import ch.ergon.dope.extension.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema
import kotlin.test.Test
import kotlin.test.assertEquals

class ForRangeSchemaTest {
    class Dummy(path: String = "") : Schema {
        val objectList: CMObjectList<Dummy2> = CMObjectList(Dummy2(path), "objectList", path)
    }

    class Dummy2(path: String = "") : Schema {
        val type: CMJsonField<String> = CMJsonField("type", path)
    }

    @Test
    fun `should support array for range with schema`() {
        val objectList = Dummy().objectList

        val actual = objectList.map(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }

        assertEquals("it", actual.iteratorName)
        assertEquals(objectList.toDopeType(), actual.range)
    }

    @Test
    fun `should support array for range with schema with condition`() {
        val schema = Dummy()
        val objectList = schema.objectList
        val condition: (ObjectField<Dummy2>) -> TypeExpression<BooleanType> = {
            it.getField(Dummy2::type).isEqualTo("test")
        }
        val transformation: (ObjectField<Dummy2>) -> TypeExpression<StringType> = {
            it.getField(Dummy2::type).toDopeType()
        }

        val actual = objectList.filter(iteratorName = "it", condition = condition).map(transformation)

        assertEquals("it", actual.iteratorName)
        assertEquals(objectList.toDopeType(), actual.range)
        assertEquals(condition, actual.cmCondition)
        assertEquals(transformation, actual.cmTransformation)
    }

    @Test
    fun `should support first for range with schema`() {
        val objectList = Dummy().objectList
        val transformation: (ObjectField<Dummy2>) -> TypeExpression<StringType> = { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }

        val actual = objectList.map(iteratorName = "it", transformation = transformation).first()

        assertEquals("it", actual.iteratorName)
        assertEquals(objectList.toDopeType(), actual.range)
        assertEquals(transformation, actual.cmTransformation)
    }

    @Test
    fun `should support first for range with schema with condition`() {
        val objectList = Dummy().objectList

        val actual = objectList.filter(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.first()

        assertEquals("it", actual.iteratorName)
        assertEquals(objectList.toDopeType(), actual.range)
    }

    @Test
    fun `should support object for range with schema`() {
        val objectList = Dummy().objectList

        val actual = objectList.map(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toObject { schema ->
            "id".toDopeType().concat(schema.getField(Dummy2::type).toDopeType())
        }

        assertEquals("it", actual.iteratorName)
        assertEquals(objectList.toDopeType(), actual.range)
    }

    @Test
    fun `should support object for range with schema with condition`() {
        val objectList = Dummy().objectList

        val actual = objectList.filter(iteratorName = "it") { schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toObject { schema ->
            "id".toDopeType().concat(schema.getField(Dummy2::type).toDopeType())
        }

        assertEquals("it", actual.iteratorName)
        assertEquals(objectList.toDopeType(), actual.range)
    }

    @Test
    fun `should support indexed array for range with schema`() {
        val objectList = Dummy().objectList

        val actual = objectList.mapIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }

        assertEquals("it", actual.iteratorName)
        assertEquals("i", actual.indexName)
        assertEquals(objectList.toDopeType(), actual.range)
    }

    @Test
    fun `should support indexed array for range with schema with condition`() {
        val objectList = Dummy().objectList

        val actual = objectList.filterIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }

        assertEquals("it", actual.iteratorName)
        assertEquals("i", actual.indexName)
        assertEquals(objectList.toDopeType(), actual.range)
    }

    @Test
    fun `should support indexed first for range with schema`() {
        val objectList = Dummy().objectList

        val actual = objectList.mapIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.first()

        assertEquals("it", actual.iteratorName)
        assertEquals("i", actual.indexName)
        assertEquals(objectList.toDopeType(), actual.range)
    }

    @Test
    fun `should support indexed first for range with schema with condition`() {
        val objectList = Dummy().objectList

        val actual = objectList.filterIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.first()

        assertEquals("it", actual.iteratorName)
        assertEquals("i", actual.indexName)
        assertEquals(objectList.toDopeType(), actual.range)
    }

    @Test
    fun `should support indexed object for range with schema`() {
        val objectList = Dummy().objectList

        val actual = objectList.mapIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toObject { _, schema ->
            "id".toDopeType().concat(schema.getField(Dummy2::type).toDopeType())
        }

        assertEquals("it", actual.iteratorName)
        assertEquals("i", actual.indexName)
        assertEquals(objectList.toDopeType(), actual.range)
    }

    @Test
    fun `should support indexed object for range with schema with condition`() {
        val objectList = Dummy().objectList

        val actual = objectList.filterIndexed(iteratorName = "it", indexName = "i") { _, schema ->
            schema.getField(Dummy2::type).isEqualTo("test")
        }.map { _, schema ->
            schema.getField(Dummy2::type).toDopeType()
        }.toObject { _, schema ->
            "id".toDopeType().concat(schema.getField(Dummy2::type).toDopeType())
        }

        assertEquals("it", actual.iteratorName)
        assertEquals("i", actual.indexName)
        assertEquals(objectList.toDopeType(), actual.range)
    }
}
