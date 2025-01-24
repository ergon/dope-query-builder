package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.fromable.Bucket

const val ASTERISK_STRING = "*"

class AsteriskExpression(private val bucket: Bucket? = null) : Expression {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val queryString = bucket?.toDopeQuery(manager)?.queryString?.let { "$it.$ASTERISK_STRING" } ?: ASTERISK_STRING
        return DopeQuery(queryString = queryString)
    }
}
