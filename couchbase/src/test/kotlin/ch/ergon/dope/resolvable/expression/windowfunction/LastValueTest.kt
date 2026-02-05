package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someWindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.LastValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.LastValueWithReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.IGNORE
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.RESPECT
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.lastValue
import kotlin.test.Test
import kotlin.test.assertEquals

class LastValueTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support last value with reference`() {
        val expected = CouchbaseDopeQuery(
            "LAST_VALUE(`numberField`) OVER `ref`",
        )
        val underTest = LastValueWithReference(someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support last value with reference and nulls modifier`() {
        val expected = CouchbaseDopeQuery(
            "LAST_VALUE(`numberField`) RESPECT NULLS OVER `ref`",
        )
        val underTest = LastValueWithReference(someNumberField(), nullsModifier = RESPECT, windowReference = "ref")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support last value with order clause`() {
        val expected = CouchbaseDopeQuery(
            "LAST_VALUE(`numberField`) OVER (ORDER BY `stringField`)",
        )
        val underTest = LastValue(someNumberField(), windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support last value with order clause and nulls modifier`() {
        val expected = CouchbaseDopeQuery(
            "LAST_VALUE(`numberField`) IGNORE NULLS OVER (ORDER BY `stringField`)",
        )
        val underTest = LastValue(
            someNumberField(),
            nullsModifier = IGNORE,
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support last value with all window partition`() {
        val expected = CouchbaseDopeQuery(
            "LAST_VALUE(`numberField`) OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
        )
        val underTest = LastValue(
            someNumberField(),
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support last value with frame clause`() {
        val expected = CouchbaseDopeQuery(
            "LAST_VALUE(`numberField`) OVER (ORDER BY `stringField` RANGE UNBOUNDED PRECEDING)",
        )
        val underTest = LastValue(
            someNumberField(),
            windowOrderClause = listOf(someOrderingTerm()),
            windowFrameClause = someWindowFrameClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support last value function with reference`() {
        val expression = someNumberField()
        val windowReference = "ref"
        val nullsModifier = RESPECT
        val expected = LastValueWithReference(expression, nullsModifier, windowReference)

        val actual = lastValue(expression, nullsModifier, windowReference)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support last value function with all parameters`() {
        val expression = someNumberField()
        val nullsModifier = IGNORE
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val windowFrameClause = someWindowFrameClause()
        val expected = LastValue(expression, nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

        val actual = lastValue(expression, nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
