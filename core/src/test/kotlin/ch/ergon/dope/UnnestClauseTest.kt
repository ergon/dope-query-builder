package ch.ergon.dope

import ch.ergon.dope.helper.someArrayField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isValued
import ch.ergon.dope.resolvable.fromable.asterisk
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class UnnestClauseTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support nested unnest`() {
        val airline = someBucket("airline").alias("ai")
        val aField = someArrayField("a")
        val expected = "SELECT c FROM airline AS ai UNNEST a AS ab UNNEST ab.c WHERE a IS VALUED"

        val actual: String = create
            .select(someArrayField("c"))
            .from(airline)
            .unnest(aField.alias("ab"))
            .unnest(someArrayField("c", someBucket("ab")))
            .where(aField.isValued())
            .build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unnest`() {
        val expected = "SELECT c FROM airline UNNEST a"

        val actual: String = create
            .select(someArrayField("c"))
            .from(someBucket("airline"))
            .unnest(someArrayField("a"))
            .build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unnest s`() {
        val expected = "SELECT b.* FROM someBucket AS b UNNEST arrayField AS a WHERE b.stringField = \"something\" AND a.stringField = \$param"

        val b = someBucket().alias("b")
        val actual: String = create
            .select(b.asterisk())
            .from(b)
            .unnest(someArrayField().alias("a"))
            .where(
                someStringField(bucket = b).isEqualTo("something")
                    .and(someStringField(bucket = someBucket("a")).isEqualTo("".asParameter("param"))),
            )
            .build()

        assertEquals(expected, actual)
    }
}
