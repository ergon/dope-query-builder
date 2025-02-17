package ch.ergon.dope.extension.expression.type

import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ObjectType
import com.schwarz.crystalapi.ITypeConverter
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.CMType
import com.schwarz.crystalapi.schema.Schema
import kotlin.reflect.KProperty1

class ObjectField<S : Schema>(val schema: S, val name: String, val path: String) : Field<ObjectType>(name, path)

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
 * val someField = schema.someObject.getField(OtherSchema::someField)
 * ```
 *
 * @param field a property of the schema
 * @throws IllegalStateException if the attribute type is not supported
 * @return the retrieved CMType
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : CMType, S : Schema> ObjectField<S>.getField(field: KProperty1<S, T>): T {
    val schemaField = field.get(schema)
    val nestedFieldPath = if (path.isBlank()) name else "$path.$name"
    return when (schemaField) {
        is CMConverterField<*, *> ->
            CMConverterField(schemaField.name, nestedFieldPath, schemaField.typeConverter as ITypeConverter<Any, Any>) as T

        is CMConverterList<*, *> ->
            CMConverterList(schemaField.name, nestedFieldPath, schemaField.typeConverter as ITypeConverter<Any, Any>) as T

        is CMJsonField<*> -> CMJsonField<Any>(schemaField.name, nestedFieldPath) as T
        is CMJsonList<*> -> CMJsonList<Any>(schemaField.name, nestedFieldPath) as T
        is CMObjectField<*> -> CMObjectField(schemaField.element, schemaField.name, nestedFieldPath) as T
        is CMObjectList<*> -> CMObjectList(schemaField.element, schemaField.name, nestedFieldPath) as T
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
 * val someField = schema.someObject.getField(OtherSchema::someField)
 * ```
 *
 * @param field a property of the schema
 * @throws IllegalStateException if the attribute type is not supported
 * @return the retrieved CMType
 */
inline fun <reified T : CMType, S : Schema> CMObjectField<S>.getField(field: KProperty1<S, T>): T = toDopeType().getField(field)
