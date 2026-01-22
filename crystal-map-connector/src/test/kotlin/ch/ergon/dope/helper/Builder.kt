package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.expression.type.CaseClass
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.keyspace.Keyspace
import ch.ergon.dope.resolvable.keyspace.UnaliasedKeyspace
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.ITypeConverter
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema
import java.time.Instant
import java.util.*

fun someKeyspace(bucket: String = "someBucket", scope: String? = null, collection: String? = null) = UnaliasedKeyspace(bucket, scope, collection)

fun someCMNumberField(name: String = "cmNumberField", path: String = "") = CMJsonField<Number>(name, path)
fun someCMStringField(name: String = "cmStringField", path: String = "") = CMJsonField<String>(name, path)
fun someCMBooleanField(name: String = "cmBooleanField", path: String = "") = CMJsonField<Boolean>(name, path)

fun someCMConverterNumberField(name: String = "cmConverterNumberField", path: String = "") =
    CMConverterField(name, path, DateNumberConverterInstance)

fun someCMConverterStringField(name: String = "cmConverterStringField", path: String = "") =
    CMConverterField(name, path, DateStringConverterInstance)

fun someCMConverterBooleanField(name: String = "cmConverterBooleanField", path: String = "") =
    CMConverterField(name, path, DateBooleanConverterInstance)

fun someCorruptField(name: String = "corruptField", path: String = "") =
    CMConverterField(name, path, CorruptStringNumberConverterInstance)

fun someNumberFieldList(name: String = "numberFieldList", keyspace: Keyspace? = null) = Field<ArrayType<NumberType>>(name, keyspace)
fun someStringFieldList(name: String = "stringFieldList", keyspace: Keyspace? = null) = Field<ArrayType<StringType>>(name, keyspace)
fun someBooleanFieldList(name: String = "booleanFieldList", keyspace: Keyspace? = null) = Field<ArrayType<BooleanType>>(name, keyspace)

fun someCMNumberList(name: String = "cmNumberList", path: String = "") = CMJsonList<Number>(name, path)
fun someCMStringList(name: String = "cmStringList", path: String = "") = CMJsonList<String>(name, path)
fun someCMBooleanList(name: String = "cmBooleanList", path: String = "") = CMJsonList<Boolean>(name, path)

class SchemaDummy : Schema

fun someCMObjectList(name: String = "cmObjectList", path: String = "") = CMObjectList(SchemaDummy(), name, path)
fun someCMObjectField(name: String = "cmObjectField", path: String = "") = CMObjectField(SchemaDummy(), name, path)

fun someCMConverterNumberList(name: String = "cmConverterNumberList", path: String = "") =
    CMConverterList(name, path, DateNumberConverterInstance)

fun someCMConverterStringList(name: String = "cmConverterStringList", path: String = "") =
    CMConverterList(name, path, DateStringConverterInstance)

fun someCMConverterBooleanList(name: String = "cmConverterBooleanList", path: String = "") =
    CMConverterList(name, path, DateBooleanConverterInstance)

fun someSelect(selectable: Selectable = Asterisk()) = SelectClause(selectable)
fun someOrderBy(selectClause: SelectClause) = selectClause.orderBy(someNumberField(), OrderType.ASC)
fun someFrom(fromable: Fromable = someKeyspace(), selectClause: SelectClause = someSelect()) = FromClause(fromable, selectClause)

fun someDelete(keyspace: Keyspace = someKeyspace()) = DeleteClause(keyspace)

fun someUpdate(keyspace: Keyspace = someKeyspace()) = UpdateClause(keyspace)

fun someNumber(value: Number = 5) = value

fun someString(value: String = "someString") = value

fun someBoolean(value: Boolean = true) = value

fun someDate(value: Date = Date(12345)) = value

fun someObject(value: Map<String, Any> = mapOf("someKey" to "someValue")) = value

fun someNumberField(name: String = "numberField", keyspace: Keyspace? = null) = Field<NumberType>(name, keyspace)
fun someStringField(name: String = "stringField", keyspace: Keyspace? = null) = Field<StringType>(name, keyspace)
fun someBooleanField(name: String = "booleanField", keyspace: Keyspace? = null) = Field<BooleanType>(name, keyspace)
fun someObjectField(name: String = "objectField", keyspace: Keyspace? = null) = Field<ObjectType>(name, keyspace)

fun <T : ValidType> someCaseClass(expression: TypeExpression<T>) = CaseClass(
    expression,
)

object DateNumberConverterInstance : DateNumberConverter()

abstract class DateNumberConverter : ITypeConverter<Date, Number> {
    override fun write(value: Date?): Number? =
        value?.toInstant()?.toEpochMilli()

    override fun read(value: Number?): Date? = value?.toLong()?.let { Date.from(Instant.ofEpochSecond(it)) }
}

object DateStringConverterInstance : DateStringConverter()

abstract class DateStringConverter : ITypeConverter<Date, String> {
    override fun write(value: Date?): String? =
        value?.toInstant()?.toEpochMilli().toString()

    override fun read(value: String?): Date? = value?.toLong()?.let { Date.from(Instant.ofEpochSecond(it)) }
}

object DateBooleanConverterInstance : DateBooleanConverter()

abstract class DateBooleanConverter : ITypeConverter<Date, Boolean> {
    override fun write(value: Date?): Boolean? = value != null

    override fun read(value: Boolean?): Date? = Date(1)
}

object CorruptStringNumberConverterInstance : CorruptStringNumberConverter()

abstract class CorruptStringNumberConverter : ITypeConverter<String, Number> {
    override fun write(value: String?) = null

    override fun read(value: Number?) = null
}
