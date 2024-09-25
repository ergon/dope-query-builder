package ch.ergon.dope.extension.type

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ObjectType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.CMType
import com.schwarz.crystalapi.schema.Schema

class ObjectField<S : Schema>(val schema: S, val name: String, val path: String) : Field<ObjectType>(name, path)

inline fun <reified T : CMType, S : Schema> ObjectField<S>.get(getCMType: S.() -> T): T {
    val schemaAttribute = schema.getCMType()
    val newPath = if (path.isBlank()) name else "$path`.`$name"
    return when (schemaAttribute) {
        is CMJsonField<*> -> CMJsonField<Any>(schemaAttribute.name, newPath) as T
        is CMJsonList<*> -> CMJsonList<Any>(schemaAttribute.name, newPath) as T
        is CMObjectField<*> -> CMObjectField(schemaAttribute.element, schemaAttribute.name, newPath) as T
        is CMObjectList<*> -> CMObjectList(schemaAttribute.element, schemaAttribute.name, newPath) as T
        else -> error("Unsupported CMType: $schemaAttribute")
    }
}

inline fun <reified T : CMType, S : Schema> CMObjectField<S>.get(getCMType: S.() -> T): T = toDopeType().get { getCMType() }
