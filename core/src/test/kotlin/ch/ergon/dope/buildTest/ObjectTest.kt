package ch.ergon.dope.buildTest

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.collection.any
import ch.ergon.dope.resolvable.expression.type.function.string.upper
import ch.ergon.dope.resolvable.expression.type.get
import ch.ergon.dope.resolvable.expression.type.getArray
import ch.ergon.dope.resolvable.expression.type.getBoolean
import ch.ergon.dope.resolvable.expression.type.getNumber
import ch.ergon.dope.resolvable.expression.type.getObject
import ch.ergon.dope.resolvable.expression.type.getString
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.resolvable.expression.type.toObjectEntry
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support selecting object`() {
        val expected = "SELECT `objectField` FROM `someBucket`"

        val actual: String = QueryBuilder
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

        val actual: String = QueryBuilder
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

        val actual: String = QueryBuilder
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

        val actual: String = QueryBuilder
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

        val actual: String = QueryBuilder
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

        val actual: String = QueryBuilder
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

        val actual: String = QueryBuilder
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
        val subQuery = QueryBuilder.selectRaw(someStringArrayField()).from(someBucket("other")).alias("subQuery")
        val expected = "SELECT ANY `iterator1` IN `subQuery` SATISFIES `iterator1` = \"something\" END, " +
            "`subQuery`[0] AS `cool` FROM (SELECT RAW `stringArrayField` FROM `other`) AS `subQuery`"

        val actual: String = QueryBuilder
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
