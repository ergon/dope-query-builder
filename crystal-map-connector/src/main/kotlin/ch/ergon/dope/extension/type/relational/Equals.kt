package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotEqualsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("isEqualToNumber")
fun CMJsonField<out Number>.isEqualTo(right: Number): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToNumber")
fun Number.isEqualTo(right: CMJsonField<out Number>): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToNumber")
fun CMJsonField<out Number>.isEqualTo(right: CMJsonField<out Number>): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToNumber")
fun TypeExpression<NumberType>.isEqualTo(right: CMJsonField<out Number>): EqualsExpression<NumberType> =
    isEqualTo(right.toDopeType())

@JvmName("isEqualToNumber")
fun CMJsonField<out Number>.isEqualTo(right: TypeExpression<NumberType>): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right)

@JvmName("isEqualToNumberConverter")
fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.isEqualTo(other: Convertable): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(toDopeType(other))

@JvmName("isEqualToNumberConverter")
fun <Convertable : Any, JsonType : Number> Convertable.isEqualTo(other: CMConverterField<Convertable, JsonType>): EqualsExpression<NumberType> =
    toDopeType(other).isEqualTo(other.toDopeType())

@JvmName("isEqualToString")
fun CMJsonField<String>.isEqualTo(right: String): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToString")
fun String.isEqualTo(right: CMJsonField<String>): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToString")
fun CMJsonField<String>.isEqualTo(right: CMJsonField<String>): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToString")
fun TypeExpression<StringType>.isEqualTo(right: CMJsonField<String>): EqualsExpression<StringType> =
    isEqualTo(right.toDopeType())

@JvmName("isEqualToString")
fun CMJsonField<String>.isEqualTo(right: TypeExpression<StringType>): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right)

@JvmName("isEqualToStringConverter")
fun <JsonType : Any> CMConverterField<JsonType, String>.isEqualTo(other: JsonType): EqualsExpression<StringType> =
    toDopeType().isEqualTo(toDopeType(other))

@JvmName("isEqualToStringConverter")
fun <Convertable : Any> Convertable.isEqualTo(other: CMConverterField<Convertable, String>): EqualsExpression<StringType> =
    toDopeType(other).isEqualTo(other.toDopeType())

@JvmName("isEqualToBoolean")
fun CMJsonField<Boolean>.isEqualTo(right: Boolean): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToBoolean")
fun Boolean.isEqualTo(right: CMJsonField<Boolean>): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToBoolean")
fun CMJsonField<Boolean>.isEqualTo(right: CMJsonField<Boolean>): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToBoolean")
fun TypeExpression<BooleanType>.isEqualTo(right: CMJsonField<Boolean>): EqualsExpression<BooleanType> =
    isEqualTo(right.toDopeType())

@JvmName("isEqualToBoolean")
fun CMJsonField<Boolean>.isEqualTo(right: TypeExpression<BooleanType>): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right)

@JvmName("isEqualToBooleanConverter")
fun <JsonType : Any> CMConverterField<JsonType, Boolean>.isEqualTo(other: JsonType): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(toDopeType(other))

@JvmName("isEqualToBooleanConverter")
fun <Convertable : Any> Convertable.isEqualTo(other: CMConverterField<Convertable, Boolean>): EqualsExpression<BooleanType> =
    toDopeType(other).isEqualTo(other.toDopeType())

@JvmName("isNotEqualToNumber")
fun CMJsonField<out Number>.isNotEqualTo(right: Number): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun Number.isNotEqualTo(right: CMJsonField<out Number>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun CMJsonField<out Number>.isNotEqualTo(right: CMJsonField<out Number>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun TypeExpression<NumberType>.isNotEqualTo(right: CMJsonField<out Number>): NotEqualsExpression<NumberType> =
    isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun CMJsonField<out Number>.isNotEqualTo(right: TypeExpression<NumberType>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right)

@JvmName("isNotEqualToString")
fun CMJsonField<String>.isNotEqualTo(right: String): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun String.isNotEqualTo(right: CMJsonField<String>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun CMJsonField<String>.isNotEqualTo(right: CMJsonField<String>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun TypeExpression<StringType>.isNotEqualTo(right: CMJsonField<String>): NotEqualsExpression<StringType> =
    isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun CMJsonField<String>.isNotEqualTo(right: TypeExpression<StringType>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right)

@JvmName("isNotEqualToBoolean")
fun CMJsonField<Boolean>.isNotEqualTo(right: Boolean): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun Boolean.isNotEqualTo(right: CMJsonField<Boolean>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun CMJsonField<Boolean>.isNotEqualTo(right: CMJsonField<Boolean>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun TypeExpression<BooleanType>.isNotEqualTo(right: CMJsonField<Boolean>): NotEqualsExpression<BooleanType> =
    isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun CMJsonField<Boolean>.isNotEqualTo(right: TypeExpression<BooleanType>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right)
