package ch.ergon.dope

import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.arrayAggregate
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.avg
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.count
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.countAsterisk
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.max
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.mean
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.median
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.min
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.stdDev
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.sum
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.variance
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class NumberFunctionsTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: QueryBuilder
    private val person = someBucket("person")

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = QueryBuilder()
    }

    @Test
    fun `should support numberType alias`() {
        val expected = "SELECT 12 AS `someNumber`"

        val actual: String = create
            .select(
                12.toNumberType().alias("someNumber"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min with a ValidType`() {
        val expected = "SELECT MIN(`numberField`), MIN(`person`.`fname`) FROM `person`"

        val actual: String = create
            .select(
                min(someNumberField()),
                min(someStringField("fname", person)),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min all with a ValidType`() {
        val expected = "SELECT MIN(ALL `numberField`), MIN(ALL `person`.`fname`) FROM `person`"

        val actual: String = create
            .select(
                min(someNumberField(), ALL),
                min(someStringField("fname", person), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min distinct with a ValidType`() {
        val expected = "SELECT MIN(DISTINCT `numberField`), MIN(DISTINCT `person`.`fname`) FROM `person`"

        val actual: String = create
            .select(
                min(someNumberField(), DISTINCT),
                min(someStringField("fname", person), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max with a ValidType`() {
        val expected = "SELECT MAX(`numberField`), MAX(`person`.`fname`) FROM `person`"

        val actual: String = create
            .select(
                max(someNumberField()),
                max(someStringField("fname", person)),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max all with a ValidType`() {
        val expected = "SELECT MAX(ALL `numberField`), MAX(ALL `person`.`fname`) FROM `person`"

        val actual: String = create
            .select(
                max(someNumberField(), ALL),
                max(someStringField("fname", person), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max distinct with a ValidType`() {
        val expected = "SELECT MAX(DISTINCT `numberField`), MAX(DISTINCT `person`.`fname`) FROM `person`"

        val actual: String = create
            .select(
                max(someNumberField(), DISTINCT),
                max(someStringField("fname", person), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all`() {
        val expected = "SELECT COUNT(*) FROM `person`"

        val actual: String = create
            .select(
                countAsterisk(),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with a Field`() {
        val expected = "SELECT COUNT(`numberField`) FROM `person`"

        val actual: String = create
            .select(
                count(someNumberField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all with a Field`() {
        val expected = "SELECT COUNT(ALL `numberField`) FROM `person`"

        val actual: String = create
            .select(
                count(someNumberField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count distinct with a Field`() {
        val expected = "SELECT COUNT(DISTINCT `numberField`) FROM `person`"

        val actual: String = create
            .select(
                count(someNumberField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg with a ValidType`() {
        val expected = "SELECT ARRAY_AGG(`numberField`), ARRAY_AGG(`person`.`fname`) FROM `person`"

        val actual: String = create
            .select(
                arrayAggregate(someNumberField()),
                arrayAggregate(someStringField("fname", person)),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg all with a ValidType`() {
        val expected = "SELECT ARRAY_AGG(ALL `numberField`), ARRAY_AGG(ALL `person`.`fname`) FROM `person`"

        val actual: String = create
            .select(
                arrayAggregate(someNumberField(), ALL),
                arrayAggregate(someStringField("fname", person), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg distinct with a ValidType`() {
        val expected = "SELECT ARRAY_AGG(DISTINCT `numberField`), ARRAY_AGG(DISTINCT `person`.`fname`) FROM `person`"

        val actual: String = create
            .select(
                arrayAggregate(someNumberField(), DISTINCT),
                arrayAggregate(someStringField("fname", person), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support avg with a NumberType`() {
        val expected = "SELECT AVG(`numberField`) FROM `person`"

        val actual: String = create
            .select(
                avg(someNumberField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support avg all with a NumberType`() {
        val expected = "SELECT AVG(ALL `numberField`) FROM `person`"

        val actual: String = create
            .select(
                avg(someNumberField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support avg distinct with a NumberType`() {
        val expected = "SELECT AVG(DISTINCT `numberField`) FROM `person`"

        val actual: String = create
            .select(
                avg(someNumberField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean with a NumberType`() {
        val expected = "SELECT MEAN(`numberField`) FROM `person`"

        val actual: String = create
            .select(
                mean(someNumberField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean all with a NumberType`() {
        val expected = "SELECT MEAN(ALL `numberField`) FROM `person`"

        val actual: String = create
            .select(
                mean(someNumberField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean distinct with a NumberType`() {
        val expected = "SELECT MEAN(DISTINCT `numberField`) FROM `person`"

        val actual: String = create
            .select(
                mean(someNumberField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median with a NumberType`() {
        val expected = "SELECT MEDIAN(`numberField`) FROM `person`"

        val actual: String = create
            .select(
                median(someNumberField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median all with a NumberType`() {
        val expected = "SELECT MEDIAN(ALL `numberField`) FROM `person`"

        val actual: String = create
            .select(
                median(someNumberField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median distinct with a NumberType`() {
        val expected = "SELECT MEDIAN(DISTINCT `numberField`) FROM `person`"

        val actual: String = create
            .select(
                median(someNumberField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum with a NumberType`() {
        val expected = "SELECT SUM(`numberField`) FROM `person`"

        val actual: String = create
            .select(
                sum(someNumberField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum all with a NumberType`() {
        val expected = "SELECT SUM(ALL `numberField`) FROM `person`"

        val actual: String = create
            .select(
                sum(someNumberField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum distinct with a NumberType`() {
        val expected = "SELECT SUM(DISTINCT `numberField`) FROM `person`"

        val actual: String = create
            .select(
                sum(someNumberField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stddev with a NumberType`() {
        val expected = "SELECT STDDEV(`numberField`) FROM `person`"

        val actual: String = create
            .select(
                stdDev(someNumberField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stddev all with a NumberType`() {
        val expected = "SELECT STDDEV(ALL `numberField`) FROM `person`"

        val actual: String = create
            .select(
                stdDev(someNumberField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stddev distinct with a NumberType`() {
        val expected = "SELECT STDDEV(DISTINCT `numberField`) FROM `person`"

        val actual: String = create
            .select(
                stdDev(someNumberField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance with a NumberType`() {
        val expected = "SELECT VARIANCE(`numberField`) FROM `person`"

        val actual: String = create
            .select(
                variance(someNumberField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance all with a NumberType`() {
        val expected = "SELECT VARIANCE(ALL `numberField`) FROM `person`"

        val actual: String = create
            .select(
                variance(someNumberField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance distinct with a NumberType`() {
        val expected = "SELECT VARIANCE(DISTINCT `numberField`) FROM `person`"

        val actual: String = create
            .select(
                variance(someNumberField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }
}
