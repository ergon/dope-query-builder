package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.validtype.ValidType

interface IField<T : ValidType> : TypeExpression<T> {
    val name: String
    val bucket: Bucket?
}

data class Field<T : ValidType>(override val name: String, override val bucket: Bucket? = null) : IField<T>
