package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.GreaterOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMField

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<out Number>.isGreaterOrEqualThan(right: CMField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumber")
fun TypeExpression<NumberType>.isGreaterOrEqualThan(right: CMField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumber")
fun Number.isGreaterOrEqualThan(right: CMField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<out Number>.isGreaterOrEqualThan(right: TypeExpression<NumberType>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<out Number>.isGreaterOrEqualThan(right: Number): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumberConverter")
fun <KotlinType : Any, MapType : Number> CMConverterField<KotlinType, MapType>.isGreaterOrEqualThan(other: KotlinType):
    GreaterOrEqualThanExpression<NumberType> = toDopeType().isGreaterOrEqualThan(toDopeType(other))

@JvmName("isGreaterOrEqualThanNumberConverter")
fun <KotlinType : Any, MapType : Number> KotlinType.isGreaterOrEqualThan(other: CMConverterField<KotlinType, MapType>):
    GreaterOrEqualThanExpression<NumberType> = toDopeType(other).isGreaterOrEqualThan(other.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun TypeExpression<StringType>.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<StringType> =
    isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun String.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: TypeExpression<StringType>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: String): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanStringConverter")
fun <KotlinType : Any> CMConverterField<KotlinType, String>.isGreaterOrEqualThan(other: KotlinType): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(toDopeType(other))

@JvmName("isGreaterOrEqualThanStringConverter")
fun <KotlinType : Any> KotlinType.isGreaterOrEqualThan(other: CMConverterField<KotlinType, String>): GreaterOrEqualThanExpression<StringType> =
    toDopeType(other).isGreaterOrEqualThan(other.toDopeType())
