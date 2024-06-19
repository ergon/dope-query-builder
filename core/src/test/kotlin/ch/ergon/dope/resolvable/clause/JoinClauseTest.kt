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

        val actual = StandardJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with parameter`() {
        val paramValue = true
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON $1",
            mapOf("$1" to paramValue),
        )

        val actual = StandardJoinClause(someBucket(), onCondition = paramValue.asParameter(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join with parameter and parameter in parent`() {
        val paramValue = 1
        val paramValue2 = true
        val expected = DopeQuery(
            "SELECT $1 JOIN `someBucket` ON $2",
            mapOf("$1" to paramValue, "$2" to paramValue2),
        )

        val actual = StandardJoinClause(someBucket(), onCondition = paramValue2.asParameter(), someSelectClause(paramValue.asParameter())).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON TRUE",
            emptyMap(),
        )

        val actual = LeftJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with parameter`() {
        val paramValue = true
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON $1",
            mapOf("$1" to paramValue),
        )

        val actual = LeftJoinClause(someBucket(), onCondition = paramValue.asParameter(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join with parameter and parameter in parent`() {
        val paramValue = 1
        val paramValue2 = true
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `someBucket` ON $2",
            mapOf("$1" to paramValue, "$2" to paramValue2),
        )

        val actual = LeftJoinClause(someBucket(), onCondition = paramValue2.asParameter(), someSelectClause(paramValue.asParameter())).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON TRUE",
            emptyMap(),
        )

        val actual = InnerJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with parameter`() {
        val paramValue = true
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON $1",
            mapOf("$1" to paramValue),
        )

        val actual = InnerJoinClause(someBucket(), onCondition = paramValue.asParameter(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join with parameter and parameter in parent`() {
        val paramValue = 1
        val paramValue2 = true
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `someBucket` ON $2",
            mapOf("$1" to paramValue, "$2" to paramValue2),
        )

        val actual = InnerJoinClause(someBucket(), onCondition = paramValue2.asParameter(), someSelectClause(paramValue.asParameter())).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join`() {
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON TRUE",
            emptyMap(),
        )

        val actual = RightJoinClause(someBucket(), onCondition = someBooleanExpression(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with parameter`() {
        val paramValue = true
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON $1",
            mapOf("$1" to paramValue),
        )

        val actual = RightJoinClause(someBucket(), onCondition = paramValue.asParameter(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join with parameter and parameter in parent`() {
        val paramValue = 1
        val paramValue2 = true
        val expected = DopeQuery(
            "SELECT $1 RIGHT JOIN `someBucket` ON $2",
            mapOf("$1" to paramValue, "$2" to paramValue2),
        )

        val actual = RightJoinClause(someBucket(), onCondition = paramValue2.asParameter(), someSelectClause(paramValue.asParameter())).toDopeQuery()

        assertEquals(expected, actual)
    }

    // ON KEYS

    @Test
    fun `should support standard join on keys`() {
        val expected = DopeQuery(
            "SELECT * JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
        )

        val actual = StandardJoinClause(someBucket(), onKeys = someStringField(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard join on keys and parameter in parent`() {
        val paramValue = 1
        val expected = DopeQuery(
            "SELECT $1 JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to paramValue),
        )

        val actual = StandardJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(paramValue.asParameter())).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys`() {
        val expected = DopeQuery(
            "SELECT * LEFT JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
        )

        val actual = LeftJoinClause(someBucket(), onKeys = someStringField(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left join on keys with parameter and parameter in parent`() {
        val paramValue = 1
        val expected = DopeQuery(
            "SELECT $1 LEFT JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to paramValue),
        )

        val actual = LeftJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(paramValue.asParameter())).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys`() {
        val expected = DopeQuery(
            "SELECT * INNER JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
        )

        val actual = InnerJoinClause(someBucket(), onKeys = someStringField(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner join on keys with parameter and parameter in parent`() {
        val paramValue = 1
        val expected = DopeQuery(
            "SELECT $1 INNER JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to paramValue),
        )

        val actual = InnerJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(paramValue.asParameter())).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join on keys`() {
        val expected = DopeQuery(
            "SELECT * RIGHT JOIN `someBucket` ON KEYS `stringField`",
            emptyMap(),
        )

        val actual = RightJoinClause(someBucket(), onKeys = someStringField(), someSelectClause()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support right join on keys with parameter and parameter in parent`() {
        val paramValue = 1
        val expected = DopeQuery(
            "SELECT $1 RIGHT JOIN `someBucket` ON KEYS `stringField`",
            mapOf("$1" to paramValue),
        )

        val actual = RightJoinClause(someBucket(), onKeys = someStringField(), someSelectClause(paramValue.asParameter())).toDopeQuery()

        assertEquals(expected, actual)
    }
}
