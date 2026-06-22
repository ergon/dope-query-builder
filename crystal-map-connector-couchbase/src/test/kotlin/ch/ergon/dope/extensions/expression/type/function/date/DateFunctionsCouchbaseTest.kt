package ch.ergon.dope.extensions.expression.type.function.date

import ch.ergon.dope.couchbase.resolvable.expression.type.function.date.DurationToStringExpression
import ch.ergon.dope.couchbase.resolvable.expression.type.function.date.StringToDurationExpression
import ch.ergon.dope.extension.expression.type.function.date.toDurationMillis
import ch.ergon.dope.extension.expression.type.function.date.toDurationString
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DateFunctionsCouchbaseTest {
    @Test
    fun `should support DurationToString with CM number`() {
        val date = someCMNumberField()
        val expected = DurationToStringExpression(date.toDopeType())

        val actual = date.toDurationString()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support StrToDuration with CM string`() {
        val duration = someCMStringField()
        val expected = StringToDurationExpression(duration.toDopeType())

        val actual = duration.toDurationMillis()

        assertEquals(expected, actual)
    }
}
