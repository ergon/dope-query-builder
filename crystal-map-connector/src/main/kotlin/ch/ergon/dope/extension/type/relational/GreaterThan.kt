package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.GreaterThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMField

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: CMField<out Number>): GreaterThanExpression<NumberType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanNumber")
fun TypeExpression<NumberType>.isGreaterThan(right: CMField<out Number>): GreaterThanExpression<NumberType> =
    isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanNumber")
fun Number.isGreaterThan(right: CMField<out Number>): GreaterThanExpression<NumberType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: TypeExpression<NumberType>): GreaterThanExpression<NumberType> =
    toDopeType().isGreaterThan(right)

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: Number): GreaterThanExpression<NumberType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanNumberConverter")
fun <KotlinType : Any, MapType : Number> CMConverterField<KotlinType, MapType>.isGreaterThan(other: KotlinType):
    GreaterThanExpression<NumberType> = toDopeType().isGreaterThan(typeConverter.write(other)!!.toDopeType())

@JvmName("isGreaterThanNumberConverter")
fun <KotlinType : Any, MapType : Number> KotlinType.isGreaterThan(other: CMConverterField<KotlinType, MapType>):
    GreaterThanExpression<NumberType> = other.typeConverter.write(this)!!.isGreaterThan(other.toDopeType())

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: CMField<String>): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanString")
fun TypeExpression<StringType>.isGreaterThan(right: CMField<String>): GreaterThanExpression<StringType> =
    isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanString")
fun String.isGreaterThan(right: CMField<String>): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: TypeExpression<StringType>): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(right)

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: String): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanStringConverter")
fun <MapType : Any> CMConverterField<MapType, String>.isGreaterThan(other: MapType): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(typeConverter.write(other)!!.toDopeType())

@JvmName("isGreaterThanStringConverter")
fun <KotlinType : Any> KotlinType.isGreaterThan(other: CMConverterField<KotlinType, String>): GreaterThanExpression<StringType> =
    other.typeConverter.write(this)!!.isGreaterThan(other.toDopeType())
