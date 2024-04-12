package ch.ergon.dope

import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.count
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.countAsterisk
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.min
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class NumberFunctionsTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: DSLContext
    private val person = someBucket("person")

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = DSLContext()
    }

    @Test
    fun `should support numberType alias`() {
        val expected = "SELECT 12 AS someNumber"

        val actual: String = create
            .select(
                12.toNumberType().alias("someNumber"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min with a ValidType`() {
        val expected = "SELECT MIN(numberField), MIN(person.fname) FROM person"

        val actual: String = create
            .select(
                min(someNumberField()),
                min(someStringField("fname", person)),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count all`() {
        val expected = "SELECT COUNT(*) FROM person"

        val actual: String = create
            .select(
                countAsterisk(),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with a Field`() {
        val expected = "SELECT COUNT(numberField) FROM person"

        val actual: String = create
            .select(
                count(someNumberField()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }
}
