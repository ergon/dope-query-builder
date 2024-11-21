package ch.ergon.dope.extension.type

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType
import com.schwarz.crystalapi.schema.Schema

class ObjectList<T : Schema>(val schema: T, name: String, path: String) : Field<ArrayType<ObjectType>>(name, path)
