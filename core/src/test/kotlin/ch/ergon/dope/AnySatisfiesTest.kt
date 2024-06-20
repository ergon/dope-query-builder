package ch.ergon.dope

import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.AnySatisfiesExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.any
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.upper
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class AnySatisfiesTest {

    @BeforeEach
    fun setup() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support any satisfies string`() {
        val expected = DopeQuery(
            "ANY x IN `stringArrayField` SATISFIES UPPER(x) = \"A\" END",
            emptyMap(),
        )

        val actual = AnySatisfiesExpression("x", someStringArrayField()) { x -> upper(x).isEqualTo("A") }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies number`() {
        val expected = DopeQuery(
            "ANY x IN `numberArrayField` SATISFIES (x % 2) = 1 END",
            emptyMap(),
        )

        val actual = AnySatisfiesExpression("x", someNumberArrayField()) { x -> x.mod(2).isEqualTo(1) }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support any satisfies boolean`() {
        val expected = DopeQuery(
            "ANY x IN `booleanArrayField` SATISFIES x END",
            emptyMap(),
        )

        val actual = AnySatisfiesExpression("x", someBooleanArrayField()) { it }.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support query with any satisfies`() {
        val expected = "SELECT * FROM `someBucket` WHERE (`firstName` = \"Hans\" AND ANY hobby IN `hobbies` SATISFIES hobby = \"Football\" END)"

        val actual: String = QueryBuilder()
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                someStringField("firstName").isEqualTo("Hans").and(any("hobby", someStringArrayField("hobbies")) { it.isEqualTo("Football") }),
            ).build().queryString

        kotlin.test.assertEquals(expected, actual)
    }
}
