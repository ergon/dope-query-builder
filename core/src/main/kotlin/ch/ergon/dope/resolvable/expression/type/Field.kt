package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.validtype.ValidType

interface IField<T : ValidType> : TypeExpression<T> {
    val name: String
    val path: String
}

data class Field<T : ValidType>(override val name: String, override val path: String) : IField<T>
