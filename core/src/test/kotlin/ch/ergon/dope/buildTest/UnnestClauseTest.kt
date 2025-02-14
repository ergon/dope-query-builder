package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.bucket.asterisk
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isValued
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UnnestClauseTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support unnest`() {
        val airline = someBucket()
        val aField = listOf("a".toDopeType()).toDopeType()
        val expected = "SELECT * FROM `someBucket` UNNEST [\"a\"] AS `a`"

        val alias: AliasedTypeExpression<ArrayType<StringType>> = aField.alias("a")
        val actual: String = create
            .selectFrom(airline)
            .unnest(alias)
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unnest with arrayField`() {
        val expected = "SELECT `c` FROM `airline` UNNEST `a`"

        val actual: String = create
            .select(someNumberArrayField("c"))
            .from(someBucket("airline"))
            .unnest(someNumberArrayField("a"))
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested unnest`() {
        val airline = someBucket("airline").alias("ai")
        val aField = someNumberArrayField("a")
        val expected = "SELECT `c` FROM `airline` AS `ai` UNNEST `a` AS `ab` UNNEST `ab`.`c` WHERE `a` IS VALUED"

        val actual: String = create
            .select(someNumberArrayField("c"))
            .from(airline)
            .unnest(aField.alias("ab"))
            .unnest(someBooleanArrayField("c", someBucket("ab")))
            .where(aField.isValued())
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unnest s`() {
        val expected =
            "SELECT `b`.* FROM `someBucket` AS `b` UNNEST `stringArrayField` AS `a` " +
                "WHERE (`b`.`stringField` = \"something\" AND `a`.`stringField` = \$param)"

        val b = someBucket().alias("b")
        val actual: String = create
            .select(b.asterisk())
            .from(b)
            .unnest(someStringArrayField().alias("a"))
            .where(
                someStringField(bucket = b).isEqualTo("something")
                    .and(someStringField(bucket = someBucket("a")).isEqualTo("".asParameter("param"))),
            )
            .build().queryString

        assertEquals(expected, actual)
    }
}
