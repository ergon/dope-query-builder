package ch.ergon.dope.buildTest

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.access.get
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.filter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.mapIndexed
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toStr
import ch.ergon.dope.resolvable.expression.unaliased.type.getNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessOrEqualThan
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RangeTransformationTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should filter and transform array`() {
        val expected = "SELECT ARRAY (`it` * `it`) FOR `i`:`it` IN `numberArrayField` " +
            "WHEN `i` <= 2 END[0] = 9"

        val actual = create.select(
            someNumberArrayField().filter { _, i -> i.isLessOrEqualThan(2) }
                .mapIndexed(iteratorName = "it", indexName = "i") {
                        it, _ ->
                    it.mul(it)
                }.get(0).isEqualTo(9),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should filter and transform unnested array`() {
        val expected = "SELECT ARRAY `it` FOR `i`:`it` WITHIN `numberArrayField` " +
            "WHEN `i` <= 2 END"

        val actual = create.select(
            someNumberArrayField().filter { _, i -> i.isLessOrEqualThan(2) }
                .mapIndexedUnnested(iteratorName = "it", indexName = "i") {
                        it, _ ->
                    it
                },
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should transform array and get first instance to use in string function`() {
        val expected = "SELECT CONCAT(\"test\", FIRST `it` FOR `i`:`it` IN `stringArrayField` END)"

        val actual = create.select(
            concat(
                "test",
                someStringArrayField().mapIndexed(iteratorName = "it", indexName = "i") {
                        it, _ ->
                    it
                }.first(),
            ),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should transform string into number array and get first instance to use in number function`() {
        val expected = "SELECT (FIRST TONUMBER(`it`) FOR `i`:`it` IN `stringArrayField` END - 5)"

        val actual = create.select(
            someStringArrayField().mapIndexed(iteratorName = "it", indexName = "i") {
                    it, _ ->
                it.toNumber()
            }.first().sub(5),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should transform unnested array into object and get specific field`() {
        val expected = "SELECT OBJECT CONCAT(\"id:\", TOSTRING(`i`)):`it` FOR `i`:`it` IN `numberArrayField` END.`id:1`"

        val actual = create.select(
            someNumberArrayField().mapIndexed(iteratorName = "it", indexName = "i") {
                    it, _ ->
                it
            }.toObject { _, i -> concat("id:", i.toStr()) }.getNumber("id:1"),
        ).build().queryString

        assertEquals(expected, actual)
    }
}
