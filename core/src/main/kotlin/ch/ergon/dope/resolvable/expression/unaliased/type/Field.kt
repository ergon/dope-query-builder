package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.fromable.Collection
import ch.ergon.dope.validtype.ValidType

class Field<T : ValidType>(private val name: String, private val path: String) : TypeExpression<T> {
    override fun toQueryString(): String =
        if (path.isNotBlank()) {
            "$path.$name"
        } else {
            name
        }
}

fun <T : ValidType> Collection.addField(name: String) = Field<T>(name, this.name)
