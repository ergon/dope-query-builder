package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ObjectType

const val ASTERISK_STRING = "*"

class Asterisk(private val path: SingleExpression<ObjectType>? = null) : Selectable, Returnable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val queryString = path?.toDopeQuery(manager)?.queryString?.let { "$it.$ASTERISK_STRING" } ?: ASTERISK_STRING
        return DopeQuery(queryString = queryString)
    }
}

fun asterisk(path: SingleExpression<ObjectType>? = null) = Asterisk(path)

@JvmName("asteriskSingleExpressionReceiver")
fun SingleExpression<ObjectType>.asterisk() = asterisk(this)
