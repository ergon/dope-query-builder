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

inline fun <reified T : CMType, S : Schema> ObjectField<S>.get(getField: S.() -> T): T {
    val schemaField = schema.getField()
    val newPath = if (path.isBlank()) name else "$path`.`$name"
    return when (schemaField) {
        is CMJsonField<*> -> CMJsonField<Any>(schemaField.name, newPath) as T
        is CMJsonList<*> -> CMJsonList<Any>(schemaField.name, newPath) as T
        is CMObjectField<*> -> CMObjectField(schemaField.element, schemaField.name, newPath) as T
        is CMObjectList<*> -> CMObjectList(schemaField.element, schemaField.name, newPath) as T
        else -> error("Unsupported CMType: $schemaField")
    }
}

/**
 * Retrieves a schema attribute of a specific type from the object field's schema.
 *
 * This function uses pattern matching to handle different types of schema attributes.
 * If the attribute type is not supported, it throws an error.
 *
 * Example usage:
 * ```kotlin
 * class SomeSchema : Schema {
 *    val someObject: CMObjectField<OtherSchema> = CMObjectField(OtherSchema(), "someObject")
 * }
 *
 * class OtherSchema : Schema {
 *    val someField: CMJsonField<String> = CMJsonField("someField")
 * }
 *
 * val schema = SomeSchema()
 * val someField = schema.someObject.get { someField }
 * ```
 *
 * @param getField a function that retrieves a CMType from the schema
 * @throws IllegalArgumentException if the attribute type is not supported
 * @return the retrieved CMType
 */
inline fun <reified T : CMType, S : Schema> CMObjectField<S>.get(getField: S.() -> T): T = toDopeType().get { getField() }
