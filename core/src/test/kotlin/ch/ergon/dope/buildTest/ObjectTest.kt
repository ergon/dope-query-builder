package ch.ergon.dope.buildTest

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.access.get
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.any
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.upper
import ch.ergon.dope.resolvable.expression.unaliased.type.getArray
import ch.ergon.dope.resolvable.expression.unaliased.type.getBoolean
import ch.ergon.dope.resolvable.expression.unaliased.type.getNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.getObject
import ch.ergon.dope.resolvable.expression.unaliased.type.getString
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.expression.unaliased.type.toObjectEntry
import ch.ergon.dope.validtype.StringType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support selecting object`() {
        val expected = "SELECT `objectField` FROM `someBucket`"

        val actual: String = create
            .select(
                someObjectField(),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting object field in where`() {
        val objectField = someObjectField()
        val expected = "SELECT `objectField` FROM `someBucket` WHERE `objectField`.`field` = \"someString\""

        val actual: String = create
            .select(
                objectField,
            ).from(
                someBucket(),
            ).where(
                objectField.getString("field").isEqualTo(someString()),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting primitive object`() {
        val objectPrimitive = mapOf(
            "age" to 18,
            "name" to "Peter",
            "hobbies" to listOf("football", "music"),
            "siblings" to mapOf(
                "name" to "John",
            ),
        ).alias("object")
        val expected = "SELECT {\"age\" : 18, " +
            "\"name\" : \"Peter\", " +
            "\"hobbies\" : [\"football\", \"music\"], " +
            "\"siblings\" : {\"name\" : \"John\"}} " +
            "AS `object` " +
            "FROM `someBucket`"

        val actual: String = create
            .select(
                objectPrimitive,
            ).from(
                someBucket(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting object conditions`() {
        val objectPrimitive = someObjectField("person")
        val expected = "SELECT `person` AS `a` FROM `someBucket` WHERE ((`person`.`siblings`.`name` = \"John\" " +
            "AND ANY `iterator1` IN `person`.`hobbies` SATISFIES `iterator1` = \"football\" END) " +
            "AND `person`.`age` >= 18)"

        val actual: String = create
            .select(
                objectPrimitive.alias("a"),
            ).from(
                someBucket(),
            )
            .where(
                objectPrimitive.getObject("siblings").getString("name").isEqualTo("John")
                    .and(objectPrimitive.getArray<StringType>("hobbies").any { it.isEqualTo("football") })
                    .and(objectPrimitive.getNumber("age").isGreaterOrEqualThan(18)),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object primitive with types`() {
        val expected = "SELECT {UPPER(`stringField`) : \$param} FROM `someBucket`"

        val actual: String = create
            .select(
                listOf(
                    upper(someStringField()).toObjectEntry(someString().asParameter("param")),
                ).toDopeType(),
            ).from(
                someBucket(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object with bucket path`() {
        val bucket = someBucket().alias("b")
        val expected = "SELECT `b`.`person`.`isMale` FROM `someBucket` AS `b`"

        val actual: String = create
            .select(
                someObjectField("person", bucket).getBoolean("isMale"),
            ).from(
                bucket,
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple nested object with bucket path`() {
        val bucket = someBucket().alias("b")
        val expected = "SELECT `b`.`person`.`first`.`second` AS `nested_object` FROM `someBucket` AS `b`"

        val actual: String = create
            .select(
                someObjectField("person", bucket).getObject("first").getObject("second").alias("nested_object"),
            ).from(
                bucket,
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support range operations on aliased sub select`() {
        val subQuery = create.selectRaw(someStringArrayField()).from(someBucket("other")).alias("subQuery")
        val expected = "SELECT ANY `iterator1` IN `subQuery` SATISFIES `iterator1` = \"something\" END, " +
            "`subQuery`[0] AS `cool` FROM (SELECT RAW `stringArrayField` FROM `other`) AS `subQuery`"

        val actual: String = create
            .select(
                subQuery.any { it.isEqualTo("something") },
                subQuery.get(0).alias("cool"),
            ).from(
                subQuery,
            )
            .build().queryString

        assertEquals(expected, actual)
    }
}
