package ch.ergon.dope.extensions.clause

import ch.ergon.dope.extension.clause.groupBy
import ch.ergon.dope.extension.clause.innerJoin
import ch.ergon.dope.extension.clause.join
import ch.ergon.dope.extension.clause.leftJoin
import ch.ergon.dope.extension.clause.limit
import ch.ergon.dope.extension.clause.offset
import ch.ergon.dope.extension.clause.orderBy
import ch.ergon.dope.extension.clause.unnest
import ch.ergon.dope.extension.clause.where
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someFrom
import ch.ergon.dope.helper.someSelect
import ch.ergon.dope.resolvable.clause.model.OrderByType
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectClauseTest {
    @Test
    fun `should support select where with CM`() {
        val actual: String = someSelect().where(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("SELECT * WHERE `CMBooleanField`", actual)
    }

    @Test
    fun `should support select unnest with CM Number`() {
        val actual: String = someFrom().unnest(someCMNumberList()).toDopeQuery().queryString

        assertEquals("SELECT * FROM `someBucket` UNNEST `CMNumberList`", actual)
    }

    @Test
    fun `should support select unnest with CM String`() {
        val actual: String = someFrom().unnest(someCMStringList()).toDopeQuery().queryString

        assertEquals("SELECT * FROM `someBucket` UNNEST `CMStringList`", actual)
    }

    @Test
    fun `should support select unnest with CM Boolean`() {
        val actual: String = someFrom().unnest(someCMBooleanList()).toDopeQuery().queryString

        assertEquals("SELECT * FROM `someBucket` UNNEST `CMBooleanList`", actual)
    }

    @Test
    fun `should support select join with CM`() {
        val actual: String = someFrom().join(someBucket("other"), onKeys = someCMNumberField()).toDopeQuery().queryString

        assertEquals("SELECT * FROM `someBucket` JOIN `other` ON KEYS `CMNumberField`", actual)
    }

    @Test
    fun `should support select join on key for with CM`() {
        val actual: String = someFrom().join(someBucket("other"), onKey = someCMNumberField(), someBucket()).toDopeQuery().queryString

        assertEquals("SELECT * FROM `someBucket` JOIN `other` ON KEY `CMNumberField` FOR `someBucket`", actual)
    }

    @Test
    fun `should support select inner join with CM`() {
        val actual: String = someFrom().innerJoin(someBucket("other"), onKeys = someCMNumberField()).toDopeQuery().queryString

        assertEquals("SELECT * FROM `someBucket` INNER JOIN `other` ON KEYS `CMNumberField`", actual)
    }

    @Test
    fun `should support select inner join on key for with CM`() {
        val actual: String = someFrom().innerJoin(someBucket("other"), onKey = someCMNumberField(), someBucket()).toDopeQuery().queryString

        assertEquals("SELECT * FROM `someBucket` INNER JOIN `other` ON KEY `CMNumberField` FOR `someBucket`", actual)
    }

    @Test
    fun `should support select left join with CM`() {
        val actual: String = someFrom().leftJoin(someBucket("other"), onKeys = someCMNumberField()).toDopeQuery().queryString

        assertEquals("SELECT * FROM `someBucket` LEFT JOIN `other` ON KEYS `CMNumberField`", actual)
    }

    @Test
    fun `should support select left join on key for with CM`() {
        val actual: String = someFrom().leftJoin(someBucket("other"), onKey = someCMNumberField(), someBucket()).toDopeQuery().queryString

        assertEquals("SELECT * FROM `someBucket` LEFT JOIN `other` ON KEY `CMNumberField` FOR `someBucket`", actual)
    }

    @Test
    fun `should support select group by with multiple CM`() {
        val actual: String = someSelect().groupBy(someCMStringField(), someCMNumberList()).toDopeQuery().queryString

        assertEquals("SELECT * GROUP BY `CMStringField`, `CMNumberList`", actual)
    }

    @Test
    fun `should support select group by with CM`() {
        val actual: String = someSelect().groupBy(someCMStringField()).toDopeQuery().queryString

        assertEquals("SELECT * GROUP BY `CMStringField`", actual)
    }

    @Test
    fun `should support select order by with CM and type`() {
        val actual: String = someSelect().orderBy(someCMStringField(), OrderByType.ASC).toDopeQuery().queryString

        assertEquals("SELECT * ORDER BY `CMStringField` ASC", actual)
    }

    @Test
    fun `should support select order by with CM`() {
        val actual: String = someSelect().orderBy(someCMStringField()).toDopeQuery().queryString

        assertEquals("SELECT * ORDER BY `CMStringField`", actual)
    }

    @Test
    fun `should support select limit with CM`() {
        val actual: String = someSelect().limit(someCMNumberField()).toDopeQuery().queryString

        assertEquals("SELECT * LIMIT `CMNumberField`", actual)
    }

    @Test
    fun `should support select offset with CM`() {
        val actual: String = someSelect().offset(someCMNumberField()).toDopeQuery().queryString

        assertEquals("SELECT * OFFSET `CMNumberField`", actual)
    }
}
