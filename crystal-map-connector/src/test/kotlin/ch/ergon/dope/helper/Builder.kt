package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Fromable
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.ITypeConverter
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import java.time.Instant
import java.util.*

fun someBucket(name: String = "someBucket") = UnaliasedBucket(name)

fun someCMNumberField(name: String = "CMNumberField", path: String = "") = CMJsonField<Number>(name, path)
fun someCMStringField(name: String = "CMStringField", path: String = "") = CMJsonField<String>(name, path)
fun someCMBooleanField(name: String = "CMBooleanField", path: String = "") = CMJsonField<Boolean>(name, path)

fun someCMConverterNumberField(name: String = "CMConverterNumberField", path: String = "") =
    CMConverterField(name, path, DateNumberConverterInstance)
fun someCMConverterStringField(name: String = "CMConverterStringField", path: String = "") =
    CMConverterField(name, path, DateStringConverterInstance)
fun someCMConverterBooleanField(name: String = "CMConverterBooleanField", path: String = "") =
    CMConverterField(name, path, DateBooleanConverterInstance)

fun someCorruptField(name: String = "corruptField", path: String = "") =
    CMConverterField(name, path, CorruptStringNumberConverterInstance)

fun someNumberFieldList(name: String = "numberFieldList", path: String = "") = Field<ArrayType<NumberType>>(name, path)
fun someStringFieldList(name: String = "stringFieldList", path: String = "") = Field<ArrayType<StringType>>(name, path)
fun someBooleanFieldList(name: String = "booleanFieldList", path: String = "") = Field<ArrayType<BooleanType>>(name, path)

fun someCMNumberList(name: String = "CMNumberList", path: String = "") = CMJsonList<Number>(name, path)
fun someCMStringList(name: String = "CMStringList", path: String = "") = CMJsonList<String>(name, path)
fun someCMBooleanList(name: String = "CMBooleanList", path: String = "") = CMJsonList<Boolean>(name, path)

fun someCMConverterNumberList(name: String = "CMConverterNumberList", path: String = "") =
    CMConverterList(name, path, DateNumberConverterInstance)
fun someCMConverterStringList(name: String = "CMConverterStringList", path: String = "") =
    CMConverterList(name, path, DateStringConverterInstance)
fun someCMConverterBooleanList(name: String = "CMConverterBooleanList", path: String = "") =
    CMConverterList(name, path, DateBooleanConverterInstance)

fun someSelect(exception: Expression = AsteriskExpression()) = SelectClause(exception)
fun someFrom(fromable: Fromable = someBucket(), selectClause: SelectClause = someSelect()) = FromClause(fromable, selectClause)

fun someDelete(bucket: Bucket = someBucket()) = DeleteClause(bucket)

fun someNumber(value: Number = 5) = value

fun someString(value: String = "someString") = value

fun someBoolean(value: Boolean = true) = value

fun someDate(value: Date = Date(1)) = value

fun someNumberField(name: String = "numberField", bucket: Bucket = someBucket("")) = Field<NumberType>(name, getBucketName(bucket))
fun someStringField(name: String = "stringField", bucket: Bucket = someBucket("")) = Field<StringType>(name, getBucketName(bucket))
fun someBooleanField(name: String = "booleanField", bucket: Bucket = someBucket("")) = Field<BooleanType>(name, getBucketName(bucket))

private fun getBucketName(bucket: Bucket) = when (bucket) {
    is AliasedBucket -> bucket.alias
    is UnaliasedBucket -> bucket.name
}

object DateNumberConverterInstance : DateNumberConverter()

abstract class DateNumberConverter : ITypeConverter<Date, Number> {
    override fun write(value: Date?): Number? =
        value?.toInstant()?.epochSecond

    override fun read(value: Number?): Date? = value?.toLong()?.let { Date.from(Instant.ofEpochSecond(it)) }
}

object DateStringConverterInstance : DateStringConverter()

abstract class DateStringConverter : ITypeConverter<Date, String> {
    override fun write(value: Date?): String? =
        value?.toInstant()?.epochSecond.toString()

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
