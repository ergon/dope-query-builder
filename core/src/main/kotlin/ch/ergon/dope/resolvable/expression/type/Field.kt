package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.keyspace.Keyspace
import ch.ergon.dope.validtype.ValidType

interface IField<T : ValidType> : TypeExpression<T> {
    val name: String
    val keyspace: Keyspace?
}

data class Field<T : ValidType>(override val name: String, override val keyspace: Keyspace? = null) : IField<T>
