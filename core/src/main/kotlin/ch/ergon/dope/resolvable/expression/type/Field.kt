package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.keyspace.KeySpace
import ch.ergon.dope.validtype.ValidType

interface IField<T : ValidType> : TypeExpression<T> {
    val name: String
    val keySpace: KeySpace?
}

data class Field<T : ValidType>(override val name: String, override val keySpace: KeySpace? = null) : IField<T>
