package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.InnerJoinClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinClause
import ch.ergon.dope.resolvable.clause.model.RightJoinClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinClause
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class JoinClauseTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support standard join`() {
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON TRUE",
            emptyMap(),
        )
        val underTest = StandardJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = StandardJoinClause(someBucket(), onCondition = parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with parameter and parameter in parent`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 JOIN `someBucket` ON $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = StandardJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON TRUE",
            emptyMap(),
        )
        val underTest = LeftJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = LeftJoinClause(someBucket(), onCondition = parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with parameter and parameter in parent`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `someBucket` ON $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = LeftJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON TRUE",
            emptyMap(),
        )
        val underTest = InnerJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = InnerJoinClause(someBucket(), onCondition = parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with parameter and parameter in parent`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `someBucket` ON $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = InnerJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join`() {
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON TRUE",
            emptyMap(),
        )
        val underTest = RightJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = RightJoinClause(someBucket(), onCondition = parameterValue.asParameter(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with parameter and parameter in parent`() {
        val parameterValue = 1
        val parameterValue2 = true
        val expected = DopeQuery(
            "SELECT $1 RIGHT JOIN `someBucket` ON $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = RightJoinClause(
            someBucket(),
            onCondition = parameterValue2.asParameter(),
            someSelectClause(parameterValue.asParameter()),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    // ON KEYS

    @Test
    fun `should support standard join on keys`() {
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
        )
        val underTest = StandardJoinClause(someBucket(), onKeys = someStringField(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on keys and parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = StandardJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
        )
        val underTest = LeftJoinClause(someBucket(), onKeys = someStringField(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys with parameter and parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = LeftJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
        )
        val underTest = InnerJoinClause(someBucket(), onKeys = someStringField(), someSelectClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with parameter and parameter in parent`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = InnerJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(parameterValue.asParameter()))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
