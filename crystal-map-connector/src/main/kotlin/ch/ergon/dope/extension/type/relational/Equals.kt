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
import com.schwarz.crystalapi.schema.CMField

@JvmName("isEqualToNumber")
fun CMField<out Number>.isEqualTo(right: Number): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToNumber")
fun Number.isEqualTo(right: CMField<out Number>): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToNumber")
fun CMField<out Number>.isEqualTo(right: CMField<out Number>): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToNumber")
fun TypeExpression<NumberType>.isEqualTo(right: CMField<out Number>): EqualsExpression<NumberType> =
    isEqualTo(right.toDopeType())

@JvmName("isEqualToNumber")
fun CMField<out Number>.isEqualTo(right: TypeExpression<NumberType>): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right)

@JvmName("isEqualToNumberConverter")
fun <KotlinType : Any, MapType : Number> CMConverterField<KotlinType, MapType>.isEqualTo(other: KotlinType): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(toDopeType(other))

@JvmName("isEqualToNumberConverter")
fun <KotlinType : Any, MapType : Number> KotlinType.isEqualTo(other: CMConverterField<KotlinType, MapType>): EqualsExpression<NumberType> =
    toDopeType(other).isEqualTo(other.toDopeType())

@JvmName("isEqualToString")
fun CMField<String>.isEqualTo(right: String): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToString")
fun String.isEqualTo(right: CMField<String>): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToString")
fun CMField<String>.isEqualTo(right: CMField<String>): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToString")
fun TypeExpression<StringType>.isEqualTo(right: CMField<String>): EqualsExpression<StringType> =
    isEqualTo(right.toDopeType())

@JvmName("isEqualToString")
fun CMField<String>.isEqualTo(right: TypeExpression<StringType>): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right)

@JvmName("isEqualToStringConverter")
fun <MapType : Any> CMConverterField<MapType, String>.isEqualTo(other: MapType): EqualsExpression<StringType> =
    toDopeType().isEqualTo(toDopeType(other))

@JvmName("isEqualToStringConverter")
fun <KotlinType : Any> KotlinType.isEqualTo(other: CMConverterField<KotlinType, String>): EqualsExpression<StringType> =
    toDopeType(other).isEqualTo(other.toDopeType())

@JvmName("isEqualToBoolean")
fun CMField<Boolean>.isEqualTo(right: Boolean): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToBoolean")
fun Boolean.isEqualTo(right: CMField<Boolean>): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToBoolean")
fun CMField<Boolean>.isEqualTo(right: CMField<Boolean>): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right.toDopeType())

@JvmName("isEqualToBoolean")
fun TypeExpression<BooleanType>.isEqualTo(right: CMField<Boolean>): EqualsExpression<BooleanType> =
    isEqualTo(right.toDopeType())

@JvmName("isEqualToBoolean")
fun CMField<Boolean>.isEqualTo(right: TypeExpression<BooleanType>): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right)

@JvmName("isEqualToBooleanConverter")
fun <MapType : Any> CMConverterField<MapType, Boolean>.isEqualTo(other: MapType): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(toDopeType(other))

@JvmName("isEqualToBooleanConverter")
fun <KotlinType : Any> KotlinType.isEqualTo(other: CMConverterField<KotlinType, Boolean>): EqualsExpression<BooleanType> =
    toDopeType(other).isEqualTo(other.toDopeType())

@JvmName("isNotEqualToNumber")
fun CMField<out Number>.isNotEqualTo(right: Number): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun Number.isNotEqualTo(right: CMField<out Number>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun CMField<out Number>.isNotEqualTo(right: CMField<out Number>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun TypeExpression<NumberType>.isNotEqualTo(right: CMField<out Number>): NotEqualsExpression<NumberType> =
    isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun CMField<out Number>.isNotEqualTo(right: TypeExpression<NumberType>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right)

@JvmName("isNotEqualToString")
fun CMField<String>.isNotEqualTo(right: String): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun String.isNotEqualTo(right: CMField<String>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun CMField<String>.isNotEqualTo(right: CMField<String>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun TypeExpression<StringType>.isNotEqualTo(right: CMField<String>): NotEqualsExpression<StringType> =
    isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun CMField<String>.isNotEqualTo(right: TypeExpression<StringType>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right)

@JvmName("isNotEqualToBoolean")
fun CMField<Boolean>.isNotEqualTo(right: Boolean): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun Boolean.isNotEqualTo(right: CMField<Boolean>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun CMField<Boolean>.isNotEqualTo(right: CMField<Boolean>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun TypeExpression<BooleanType>.isNotEqualTo(right: CMField<Boolean>): NotEqualsExpression<BooleanType> =
    isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun CMField<Boolean>.isNotEqualTo(right: TypeExpression<BooleanType>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right)
