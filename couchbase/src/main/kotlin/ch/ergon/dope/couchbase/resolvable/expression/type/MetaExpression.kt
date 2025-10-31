package ch.ergon.dope.couchbase.resolvable.expression.type

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.keyspace.KeySpace
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class MetaExpression(val keyspace: KeySpace?) : FunctionOperator<ObjectType> {
    val cas: IField<NumberType> = MetaField(this, "cas")

    val expiration: IField<NumberType> = MetaField(this, "expiration")

    val flags: IField<NumberType> = MetaField(this, "flags")

    val id: IField<StringType> = MetaField(this, "id")

    val type: IField<StringType> = MetaField(this, "type")

    data class MetaField<T : ValidType>(
        val metaExpression: MetaExpression,
        override val name: String,
    ) : IField<T> {
        override val keySpace: KeySpace? = null
    }
}

fun meta(keyspace: KeySpace) = MetaExpression(keyspace)

fun meta() = MetaExpression(null)
