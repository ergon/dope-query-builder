package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someFromClause
import ch.ergon.dope.helper.someKeySpace
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.asterisk
import ch.ergon.dope.resolvable.clause.except
import ch.ergon.dope.resolvable.clause.exceptAll
import ch.ergon.dope.resolvable.clause.intersect
import ch.ergon.dope.resolvable.clause.intersectAll
import ch.ergon.dope.resolvable.clause.union
import ch.ergon.dope.resolvable.clause.unionAll
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.avg
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.MISSING
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.assignTo
import ch.ergon.dope.resolvable.expression.type.case
import ch.ergon.dope.resolvable.expression.type.collection.any
import ch.ergon.dope.resolvable.expression.type.collection.inArray
import ch.ergon.dope.resolvable.expression.type.condition
import ch.ergon.dope.resolvable.expression.type.function.conditional.resultsIn
import ch.ergon.dope.resolvable.expression.type.function.date.nowString
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.get
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.logic.not
import ch.ergon.dope.resolvable.expression.type.logic.or
import ch.ergon.dope.resolvable.expression.type.otherwise
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.type.relational.isLike
import ch.ergon.dope.resolvable.expression.type.relational.isNotEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isNotNull
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class QueryBuilderTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should Equal Simple String`() {
        val expected = "SELECT `stringField`\n" + "FROM `someBucket`"

        val actual = QueryBuilder
            .select(
                someStringField(),
            ).from(
                someKeySpace(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Equal Simple String 2`() {
        val expected = "SELECT *\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Ian\"\n"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someKeySpace(),
            ).where(
                someStringField().isEqualTo("Ian".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where`() {
        val expected = "SELECT *\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Ian\"\n"

        val actual: String = QueryBuilder.selectFrom(
            someKeySpace(),
        ).where(
            someStringField().isEqualTo("Ian".toDopeType()),
        ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Equal Simple String 3`() {
        val expected = "SELECT *\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Ian\"\n"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someKeySpace(),
            ).where(
                someStringField().isEqualTo("Ian".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Select Distinct`() {
        val expected = "SELECT DISTINCT `stringField`, `numberField`\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Ian\"\n"

        val actual: String = QueryBuilder.selectDistinct(
            someStringField(),
            someNumberField(),
        ).from(
            someKeySpace(),
        ).where(
            someStringField().isEqualTo("Ian".toDopeType()),
        ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Alias`() {
        val expected = "SELECT `stringField` AS `firstName`\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Peter\""

        val actual: String = QueryBuilder
            .select(
                someStringField().alias("firstName"),
            ).from(
                someKeySpace(),
            ).where(
                someStringField().isEqualTo("Peter".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Alias Mixed In First`() {
        val expected =
            "SELECT `stringField` AS `FirstName`, `stringField` FROM `someBucket` WHERE `stringField` = \"Jackson\""

        val actual: String = QueryBuilder
            .select(
                someStringField().alias("FirstName"),
                someStringField(),
            ).from(someKeySpace()).where(
                someStringField().isEqualTo("Jackson".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Alias Mixed In Last`() {
        val expected =
            "SELECT `stringField`, `stringField` AS `LastName` FROM `someBucket` WHERE `stringField` = \"Jackson\""

        val actual: String = QueryBuilder
            .select(
                someStringField(),
                someStringField().alias("LastName"),
            ).from(someKeySpace()).where(
                someStringField().isEqualTo("Jackson".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Simple Condition`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` < 50"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someKeySpace(),
            ).where(
                someNumberField().isLessThan(50.toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Chained Conditions`() {
        val expected = "SELECT * FROM `someBucket` WHERE (`numberField` < 50 AND `stringField` = \"Mr.\")"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someKeySpace(),
            ).where(
                someNumberField().isLessThan(50.toDopeType()).and(
                    someStringField().isEqualTo("Mr.".toDopeType()),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Chained Conditions And Expressions`() {
        val expected = "SELECT * FROM `someBucket` WHERE (`numberField` < 50 AND `stringField` = \"Mr.\")"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someKeySpace(),
            ).where(
                someNumberField().isLessThan(
                    (45 + 5).toDopeType(),
                ).and(
                    someStringField().isEqualTo(
                        ("M" + "r.").toDopeType(),
                    ),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should support is not equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 != 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                12.toDopeType().isNotEqualTo(5.toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with numberField and number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` != 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                someNumberField().isNotEqualTo(5.toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with number and numberField`() {
        val expected = "SELECT * FROM `someBucket` WHERE 3 != `numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                3.toDopeType().isNotEqualTo(someNumberField()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" != \"hallo\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                "test".toDopeType().isNotEqualTo("hallo".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with stringField and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` != \"5\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                someStringField().isNotEqualTo("5".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 >= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                12.toDopeType().isGreaterOrEqualThan(5.toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with numberField and number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` >= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                someNumberField().isGreaterOrEqualThan(5.toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number and numberField`() {
        val expected = "SELECT * FROM `someBucket` WHERE 3 >= `numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                3.toDopeType().isGreaterOrEqualThan(someNumberField()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" >= \"hallo\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                "test".toDopeType().isGreaterOrEqualThan("hallo".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with stringField and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` >= \"5\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                someStringField().isGreaterOrEqualThan("5".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with boolean and stringField`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" >= `stringField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                "test".toDopeType().isGreaterOrEqualThan(someStringField()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 <= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                12.toDopeType().isLessOrEqualThan(5.toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with numberField and number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` <= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                someNumberField().isLessOrEqualThan(5.toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number and numberField`() {
        val expected = "SELECT * FROM `someBucket` WHERE 3 <= `numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                3.toDopeType().isLessOrEqualThan(someNumberField()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" <= \"hallo\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                "test".toDopeType().isLessOrEqualThan("hallo".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with stringField and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` <= \"5\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someKeySpace())
            .where(
                someStringField().isLessOrEqualThan("5".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Where With Many Chained Conditions`() {
        val expected = "SELECT * FROM `someBucket` WHERE (`numberField` < 50 AND (`stringField` = \"Mr.\" AND `stringField` = \"friend\"))"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someKeySpace(),
            ).where(
                someNumberField().isLessThan(
                    (45 + 5).toDopeType(),
                ).and(
                    someStringField().isEqualTo(
                        "Mr.".toDopeType(),
                    ).and(
                        someStringField().isEqualTo("friend".toDopeType()),
                    ),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Like`() {
        val expected = "SELECT `stringField` FROM `someBucket` WHERE `email` LIKE \"%@yahoo.com\""

        val actual: String = QueryBuilder
            .select(
                someStringField(),
            ).from(
                someKeySpace(),
            ).where(
                someStringField("email").isLike(
                    "%@yahoo.com".toDopeType(),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should support like with string function`() {
        val expected = "SELECT `stringField` FROM `someBucket` WHERE `email` LIKE CONCAT(`name`, \"%\", \"@gmail.com\")"

        val actual: String = QueryBuilder
            .select(
                someStringField(),
            ).from(
                someKeySpace(),
            ).where(
                someStringField("email").isLike(concat(someStringField("name"), "%", "@gmail.com")),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Like Chained`() {
        val expected = "SELECT `stringField`, `numberField` FROM `someBucket` WHERE (`email` LIKE \"%@gmail.com\" AND `numberField` = 46)"

        val actual: String = QueryBuilder
            .select(
                someStringField(),
                someNumberField(),
            ).from(
                someKeySpace(),
            ).where(
                someStringField("email").isLike(
                    "%@gmail.com".toDopeType(),
                ).and(
                    someNumberField().isEqualTo(46.toDopeType()),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Null`() {
        val expected = "SELECT NULL"

        val actual: String = QueryBuilder
            .select(
                NULL,
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Missing`() {
        val expected = "SELECT MISSING"

        val actual: String = QueryBuilder
            .select(
                MISSING,
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Long Complex Query`() {
        val expected = "SELECT ((1 = 1 AND 2 = 2) AND 3 = 3) AS `what` FROM `someBucket` WHERE (1 = 1 AND \"run\" = \"run\")"

        val actual: String = QueryBuilder
            .select(
                1.toDopeType().isEqualTo(
                    1.toDopeType(),
                ).and(
                    2.toDopeType().isEqualTo(
                        2.toDopeType(),
                    ),
                ).and(
                    3.toDopeType().isEqualTo(
                        3.toDopeType(),
                    ),
                ).alias("what"),
            ).from(
                someKeySpace(),
            ).where(
                1.toDopeType().isEqualTo(
                    1.toDopeType(),
                ).and(
                    "run".toDopeType().isEqualTo(
                        "run".toDopeType(),
                    ),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Simple Boolean Value True`() {
        val expected = "SELECT TRUE"

        val actual: String = QueryBuilder
            .select(TRUE).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Simple Boolean Value False`() {
        val expected = "SELECT FALSE"

        val actual: String = QueryBuilder
            .select(FALSE).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Simple String Value False`() {
        val expected = "SELECT \"FALSE\""

        val actual: String = QueryBuilder
            .select("FALSE".toDopeType()).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Separate Between Boolean And String`() {
        val expected = "SELECT TRUE = TRUE"

        val actual: String = QueryBuilder
            .select(
                TRUE.isEqualTo(
                    TRUE,
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support And with two types`() {
        val expected = "SELECT (TRUE AND FALSE)"

        val actual: String = QueryBuilder
            .select(
                TRUE.and(
                    FALSE,
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support And with a boolean type and boolean`() {
        val expected = "SELECT (TRUE AND FALSE)"

        val actual: String = QueryBuilder
            .select(
                TRUE.and(
                    false,
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support And with a boolean and boolean type`() {
        val expected = "SELECT (TRUE AND FALSE)"

        val actual: String = QueryBuilder
            .select(
                true.and(
                    FALSE,
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Or with two types`() {
        val expected = "SELECT (TRUE OR FALSE)"

        val actual: String = QueryBuilder
            .select(
                TRUE.or(
                    FALSE,
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Or with boolean type and boolean`() {
        val expected = "SELECT (TRUE OR FALSE)"

        val actual: String = QueryBuilder
            .select(
                TRUE.or(
                    false,
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Or with boolean and boolean type`() {
        val expected = "SELECT (TRUE OR FALSE)"

        val actual: String = QueryBuilder
            .select(
                true.or(
                    FALSE,
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Not 1`() {
        val expected = "SELECT NOT TRUE"

        val actual: String = QueryBuilder
            .select(not(TRUE)).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Not 2`() {
        val expected = "SELECT NOT (TRUE AND FALSE)"

        val actual: String = QueryBuilder
            .select(
                not(
                    TRUE.and(
                        FALSE,
                    ),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Not 3`() {
        val expected = "SELECT NOT (TRUE AND (FALSE AND TRUE))"

        val actual: String = QueryBuilder
            .select(
                not(
                    TRUE.and(
                        FALSE.and(
                            TRUE,
                        ),
                    ),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Equals with two types`() {
        val expected = "TRUE = FALSE"

        val actual: String = TRUE.isEqualTo(
            FALSE,
        ).toDopeQuery(resolver).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `Should support the NOW_STR function`() {
        val expected = "SELECT NOW_STR() AS `full_date`, NOW_STR(\"invalid date\") AS `invalid_date`, " +
            "NOW_STR(\"1111-11-11\") " +
            "AS `short_date`"

        val actual = QueryBuilder
            .select(
                nowString().alias("full_date"),
                nowString("invalid date").alias("invalid_date"),
                nowString("1111-11-11").alias("short_date"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select raw`() {
        val expected = "SELECT RAW NOT (TRUE OR FALSE)"

        val actual = QueryBuilder.selectRaw(
            not(TRUE.or(FALSE)),
        ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select raw with field`() {
        val expected = "SELECT RAW `name` FROM `someBucket`"

        val actual = QueryBuilder.selectRaw(
            someStringField("name"),
        ).from(
            someKeySpace(),
        ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to boolean type`() {
        val getSomething = { "something" }
        val expected = "TRUE"

        val actual = (getSomething() == "something").toDopeType().toDopeQuery(resolver).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting all Fields from keyspace`() {
        val someBucket = someKeySpace()
        val expected = "SELECT `someBucket`.* FROM `someBucket`"

        val actual = QueryBuilder
            .select(someBucket.asterisk())
            .from(someBucket)
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting all Fields from aliased keyspace`() {
        val someBucket = someKeySpace().alias("alias")
        val expected = "SELECT `alias`.* FROM `someBucket` AS `alias`"

        val actual = QueryBuilder
            .select(someBucket.asterisk())
            .from(someBucket)
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting case`() {
        val someBucket = someKeySpace()
        val expected = "SELECT CASE `numberField` WHEN `other` THEN 2 END, " +
            "CASE WHEN `booleanField` THEN `numberField` ELSE `stringField` END AS `alias` FROM `someBucket`"

        val actual = QueryBuilder
            .select(
                case(someNumberField()).condition(someNumberField("other").resultsIn(2)),
                case().condition(someBooleanField().resultsIn(someNumberField())).otherwise(someStringField()).alias("alias"),
            )
            .from(someBucket)
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select with keyspaces`() {
        val expected = "SELECT `keyspace1`, `alias` FROM `keyspace2` AS `alias`"
        val keyspace1 = someKeySpace("keyspace1")
        val keyspace2 = someKeySpace("keyspace2").alias("alias")

        val actual = QueryBuilder
            .select(keyspace1, keyspace2)
            .from(keyspace2)
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set operator union with two selects`() {
        val expected = "(SELECT * FROM `keyspace1`) UNION (SELECT * FROM `keyspace2`)"

        val actual = QueryBuilder
            .selectFrom(someKeySpace("keyspace1"))
            .union(
                QueryBuilder.selectFrom(someKeySpace("keyspace2")),
            )
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set operator except and intersect with three selects`() {
        val expected = "(SELECT * FROM `keyspace1`) EXCEPT ((SELECT * FROM `keyspace2`) INTERSECT (SELECT * FROM `keyspace3`))"

        val actual = QueryBuilder
            .selectFrom(someKeySpace("keyspace1"))
            .except(
                QueryBuilder
                    .selectFrom(someKeySpace("keyspace2"))
                    .intersect(
                        QueryBuilder
                            .selectFrom(someKeySpace("keyspace3")),
                    ),
            )
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set operator intersect all and union all and except all with three selects in order`() {
        val expected = "(((SELECT * FROM `keyspace1`) INTERSECT ALL (SELECT * FROM `keyspace2`)) " +
            "UNION ALL (SELECT * FROM `keyspace3`)) EXCEPT ALL (SELECT * FROM `keyspace4`)"

        val actual = QueryBuilder
            .selectFrom(someKeySpace("keyspace1"))
            .intersectAll(QueryBuilder.selectFrom(someKeySpace("keyspace2")))
            .unionAll(QueryBuilder.selectFrom(someKeySpace("keyspace3")))
            .exceptAll(someFromClause(someKeySpace("keyspace4"))).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting let clause`() {
        val t1 = someKeySpace("route").alias("t1")
        val equip = "equip".assignTo(someStringArrayField("equipment", t1).any { it.isEqualTo("radio") })
        val sourceAirport = someStringField("sourceAirport", someKeySpace("route").alias("t2"))
        val sourceAirports = "source_airports".assignTo(QueryBuilder.selectRaw(sourceAirport).where(sourceAirport.isNotNull()))
        val destinationAirport = someStringField("destinationAirport", t1)

        val expected = "SELECT `t1`.`destinationAirport`, `equip` AS `has_radio` " +
            "FROM `route` AS `t1` " +
            "LET `equip` = ANY `iterator1` IN `t1`.`equipment` SATISFIES `iterator1` = \"radio\" END, " +
            "`source_airports` = (SELECT RAW `t2`.`sourceAirport` WHERE `t2`.`sourceAirport` IS NOT NULL) " +
            "WHERE (`t1`.`airline` = \"AI\" AND `t1`.`destinationAirport` IN `source_airports`)"

        val actual = QueryBuilder
            .select(destinationAirport, equip.alias("has_radio"))
            .from(t1)
            .withVariables(equip, sourceAirports)
            .where(
                someStringField("airline", t1).isEqualTo("AI")
                    .and(destinationAirport.inArray(sourceAirports)),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support with clause before query`() {
        val hotel = someKeySpace("hotel")
        val publicLikes = someNumberField("publicLikes", hotel)
        val cte = hotel.alias("cte")
        val ctePublicLikes = someNumberField("publicLikes", cte)
        val avgLikeCount = "avgLikeCount".assignTo(
            QueryBuilder.selectRaw(avg(ctePublicLikes)).from(cte).asExpression(),
        )

        val expected = "WITH `avgLikeCount` AS ((SELECT RAW AVG(`cte`.`publicLikes`) FROM `hotel` AS `cte`)) " +
            "SELECT `hotel`.`publicLikes` AS `likeCount` FROM `hotel` " +
            "WHERE `hotel`.`publicLikes` >= `avgLikeCount`[0] LIMIT 5"

        val actual = QueryBuilder
            .with(avgLikeCount)
            .select(
                publicLikes.alias("likeCount"),
            )
            .from(
                hotel,
            )
            .where(
                publicLikes.isGreaterOrEqualThan(avgLikeCount.get(0)),
            )
            .limit(
                5,
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }
}
