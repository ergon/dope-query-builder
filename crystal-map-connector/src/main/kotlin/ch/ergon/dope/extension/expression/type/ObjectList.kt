package ch.ergon.dope.extension.expression.type

import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.keyspace.Keyspace
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType
import com.schwarz.crystalapi.schema.Schema

data class ObjectList<T : Schema>(
    val schema: T,
    override val name: String,
    val path: String,
    override val keyspace: Keyspace? = null,
) : IField<ArrayType<ObjectType>>
