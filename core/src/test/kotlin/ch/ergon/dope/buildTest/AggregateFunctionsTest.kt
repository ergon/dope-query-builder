package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.alias
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
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AggregateFunctionsTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: QueryBuilder
    private val person = someBucket("person")

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = QueryBuilder()
    }

    @Test
    fun `should support min with a ValidType and arrays of ValidType`() {
        val expected = "SELECT MIN(`numberField`), MIN(`stringField`), MIN(`booleanField`), " +
            "MIN(`numberArrayField`), MIN(`stringArrayField`), MIN(`booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                min(someNumberField()),
                min(someStringField()),
                min(someBooleanField()),
                min(someNumberArrayField()),
                min(someStringArrayField()),
                min(someBooleanArrayField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min all with a ValidType and arrays of ValidType`() {
        val expected = "SELECT MIN(ALL `numberField`), MIN(ALL `stringField`), MIN(ALL `booleanField`), " +
            "MIN(ALL `numberArrayField`), MIN(ALL `stringArrayField`), MIN(ALL `booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                min(someNumberField(), ALL),
                min(someStringField(), ALL),
                min(someBooleanField(), ALL),
                min(someNumberArrayField(), ALL),
                min(someStringArrayField(), ALL),
                min(someBooleanArrayField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min distinct with a ValidType and arrays of ValidType`() {
        val expected = "SELECT MIN(DISTINCT `numberField`), MIN(DISTINCT `stringField`), MIN(DISTINCT `booleanField`), " +
            "MIN(DISTINCT `numberArrayField`), MIN(DISTINCT `stringArrayField`), MIN(DISTINCT `booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                min(someNumberField(), DISTINCT),
                min(someStringField(), DISTINCT),
                min(someBooleanField(), DISTINCT),
                min(someNumberArrayField(), DISTINCT),
                min(someStringArrayField(), DISTINCT),
                min(someBooleanArrayField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min as an alias with a ValidType`() {
        val expected = "SELECT MIN(`numberField`) AS `number`, MIN(`stringField`) AS `string`, " +
            "MIN(`booleanField`) AS `boolean`, MIN(`numberArrayField`) AS `numberArray`, " +
            "MIN(`stringArrayField`) AS `stringArray`, MIN(`booleanArrayField`) AS `booleanArray` FROM `person`"

        val actual: String = create
            .select(
                min(someNumberField()).alias("number"),
                min(someStringField()).alias("string"),
                min(someBooleanField()).alias("boolean"),
                min(someNumberArrayField()).alias("numberArray"),
                min(someStringArrayField()).alias("stringArray"),
                min(someBooleanArrayField()).alias("booleanArray"),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max with a ValidType`() {
        val expected = "SELECT MAX(`numberField`), MAX(`stringField`), MAX(`booleanField`), " +
            "MAX(`numberArrayField`), MAX(`stringArrayField`), MAX(`booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                max(someNumberField()),
                max(someStringField()),
                max(someBooleanField()),
                max(someNumberArrayField()),
                max(someStringArrayField()),
                max(someBooleanArrayField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max all with a ValidType`() {
        val expected = "SELECT MAX(ALL `numberField`), MAX(ALL `stringField`), MAX(ALL `booleanField`), " +
            "MAX(ALL `numberArrayField`), MAX(ALL `stringArrayField`), MAX(ALL `booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                max(someNumberField(), ALL),
                max(someStringField(), ALL),
                max(someBooleanField(), ALL),
                max(someNumberArrayField(), ALL),
                max(someStringArrayField(), ALL),
                max(someBooleanArrayField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max distinct with a ValidType`() {
        val expected = "SELECT MAX(DISTINCT `numberField`), MAX(DISTINCT `stringField`), MAX(DISTINCT `booleanField`), " +
            "MAX(DISTINCT `numberArrayField`), MAX(DISTINCT `stringArrayField`), MAX(DISTINCT `booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                max(someNumberField(), DISTINCT),
                max(someStringField(), DISTINCT),
                max(someBooleanField(), DISTINCT),
                max(someNumberArrayField(), DISTINCT),
                max(someStringArrayField(), DISTINCT),
                max(someBooleanArrayField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max as an alias with a ValidType`() {
        val expected = "SELECT MAX(`numberField`) AS `number`, MAX(`stringField`) AS `string`, " +
            "MAX(`booleanField`) AS `boolean`, MAX(`numberArrayField`) AS `numberArray`, " +
            "MAX(`stringArrayField`) AS `stringArray`, MAX(`booleanArrayField`) AS `booleanArray` FROM `person`"

        val actual: String = create
            .select(
                max(someNumberField()).alias("number"),
                max(someStringField()).alias("string"),
                max(someBooleanField()).alias("boolean"),
                max(someNumberArrayField()).alias("numberArray"),
                max(someStringArrayField()).alias("stringArray"),
                max(someBooleanArrayField()).alias("booleanArray"),
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
    fun `should support count all as an alias`() {
        val expected = "SELECT COUNT(*) AS `everything` FROM `person`"

        val actual: String = create
            .select(
                countAsterisk().alias("everything"),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with a ValidType and arrays of ValidType`() {
        val expected = "SELECT COUNT(`numberField`), COUNT(`stringField`), COUNT(`booleanField`), " +
            "COUNT(`numberArrayField`), COUNT(`stringArrayField`), COUNT(`booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                count(someNumberField()),
                count(someStringField()),
                count(someBooleanField()),
                count(someNumberArrayField()),
                count(someStringArrayField()),
                count(someBooleanArrayField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all with a Field`() {
        val expected = "SELECT COUNT(ALL `numberField`), COUNT(ALL `stringField`), " +
            "COUNT(ALL `booleanField`), COUNT(ALL `numberArrayField`), " +
            "COUNT(ALL `stringArrayField`), COUNT(ALL `booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                count(someNumberField(), ALL),
                count(someStringField(), ALL),
                count(someBooleanField(), ALL),
                count(someNumberArrayField(), ALL),
                count(someStringArrayField(), ALL),
                count(someBooleanArrayField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count distinct with a Field`() {
        val expected = "SELECT COUNT(DISTINCT `numberField`), COUNT(DISTINCT `stringField`), " +
            "COUNT(DISTINCT `booleanField`), COUNT(DISTINCT `numberArrayField`), " +
            "COUNT(DISTINCT `stringArrayField`), COUNT(DISTINCT `booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                count(someNumberField(), DISTINCT),
                count(someStringField(), DISTINCT),
                count(someBooleanField(), DISTINCT),
                count(someNumberArrayField(), DISTINCT),
                count(someStringArrayField(), DISTINCT),
                count(someBooleanArrayField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count as an alias with a ValidType and arrays of ValidType`() {
        val expected = "SELECT COUNT(`numberField`) AS `number`, COUNT(`stringField`) AS `string`, " +
            "COUNT(`booleanField`) AS `boolean`, COUNT(`numberArrayField`) AS `numberArray`, " +
            "COUNT(`stringArrayField`) AS `stringArray`, COUNT(`booleanArrayField`) AS `booleanArray` FROM `person`"

        val actual: String = create
            .select(
                count(someNumberField()).alias("number"),
                count(someStringField()).alias("string"),
                count(someBooleanField()).alias("boolean"),
                count(someNumberArrayField()).alias("numberArray"),
                count(someStringArrayField()).alias("stringArray"),
                count(someBooleanArrayField()).alias("booleanArray"),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg with a ValidType`() {
        val expected = "SELECT ARRAY_AGG(`numberField`), ARRAY_AGG(`stringField`), ARRAY_AGG(`booleanField`), " +
            "ARRAY_AGG(`numberArrayField`), ARRAY_AGG(`stringArrayField`), ARRAY_AGG(`booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                arrayAggregate(someNumberField()),
                arrayAggregate(someStringField()),
                arrayAggregate(someBooleanField()),
                arrayAggregate(someNumberArrayField()),
                arrayAggregate(someStringArrayField()),
                arrayAggregate(someBooleanArrayField()),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg all with a ValidType`() {
        val expected = "SELECT ARRAY_AGG(ALL `numberField`), ARRAY_AGG(ALL `stringField`), " +
            "ARRAY_AGG(ALL `booleanField`), ARRAY_AGG(ALL `numberArrayField`), " +
            "ARRAY_AGG(ALL `stringArrayField`), ARRAY_AGG(ALL `booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                arrayAggregate(someNumberField(), ALL),
                arrayAggregate(someStringField(), ALL),
                arrayAggregate(someBooleanField(), ALL),
                arrayAggregate(someNumberArrayField(), ALL),
                arrayAggregate(someStringArrayField(), ALL),
                arrayAggregate(someBooleanArrayField(), ALL),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg distinct with a ValidType`() {
        val expected = "SELECT ARRAY_AGG(DISTINCT `numberField`), ARRAY_AGG(DISTINCT `stringField`), " +
            "ARRAY_AGG(DISTINCT `booleanField`), ARRAY_AGG(DISTINCT `numberArrayField`), " +
            "ARRAY_AGG(DISTINCT `stringArrayField`), ARRAY_AGG(DISTINCT `booleanArrayField`) FROM `person`"

        val actual: String = create
            .select(
                arrayAggregate(someNumberField(), DISTINCT),
                arrayAggregate(someStringField(), DISTINCT),
                arrayAggregate(someBooleanField(), DISTINCT),
                arrayAggregate(someNumberArrayField(), DISTINCT),
                arrayAggregate(someStringArrayField(), DISTINCT),
                arrayAggregate(someBooleanArrayField(), DISTINCT),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg as an alias with a ValidType`() {
        val expected = "SELECT ARRAY_AGG(`numberField`) AS `number`, ARRAY_AGG(`stringField`) AS `string`, " +
            "ARRAY_AGG(`booleanField`) AS `boolean`, ARRAY_AGG(`numberArrayField`) AS `numberArray`, " +
            "ARRAY_AGG(`stringArrayField`) AS `stringArray`, ARRAY_AGG(`booleanArrayField`) AS `booleanArray` FROM `person`"

        val actual: String = create
            .select(
                arrayAggregate(someNumberField()).alias("number"),
                arrayAggregate(someStringField()).alias("string"),
                arrayAggregate(someBooleanField()).alias("boolean"),
                arrayAggregate(someNumberArrayField()).alias("numberArray"),
                arrayAggregate(someStringArrayField()).alias("stringArray"),
                arrayAggregate(someBooleanArrayField()).alias("booleanArray"),
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
    fun `should support avg as an alias with a NumberType`() {
        val expected = "SELECT AVG(`numberField`) AS `number` FROM `person`"

        val actual: String = create
            .select(
                avg(someNumberField()).alias("number"),
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
    fun `should support mean as an alias with a NumberType`() {
        val expected = "SELECT MEAN(`numberField`) AS `number` FROM `person`"

        val actual: String = create
            .select(
                mean(someNumberField()).alias("number"),
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
    fun `should support median as an alias with a NumberType`() {
        val expected = "SELECT MEDIAN(`numberField`) AS `number` FROM `person`"

        val actual: String = create
            .select(
                median(someNumberField()).alias("number"),
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
    fun `should support sum as an alias with a NumberType`() {
        val expected = "SELECT SUM(`numberField`) AS `number` FROM `person`"

        val actual: String = create
            .select(
                sum(someNumberField()).alias("number"),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stdDev with a NumberType`() {
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
    fun `should support stdDev all with a NumberType`() {
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
    fun `should support stdDev distinct with a NumberType`() {
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
    fun `should support stdDev as an alias with a NumberType`() {
        val expected = "SELECT STDDEV(`numberField`) AS `number` FROM `person`"

        val actual: String = create
            .select(
                stdDev(someNumberField()).alias("number"),
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

    @Test
    fun `should support variance as an alias with a NumberType`() {
        val expected = "SELECT VARIANCE(`numberField`) AS `number` FROM `person`"

        val actual: String = create
            .select(
                variance(someNumberField()).alias("number"),
            ).from(
                person,
            ).build().queryString

        assertEquals(expected, actual)
    }
}
