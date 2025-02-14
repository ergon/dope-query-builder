package ch.ergon.dope.extension.expression.single.type.relational

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.relational.GreaterOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.single.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("isGreaterOrEqualThanNumber")
fun CMJsonField<out Number>.isGreaterOrEqualThan(right: CMJsonField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumber")
fun TypeExpression<NumberType>.isGreaterOrEqualThan(right: CMJsonField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumber")
fun Number.isGreaterOrEqualThan(right: CMJsonField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumber")
fun CMJsonField<out Number>.isGreaterOrEqualThan(right: TypeExpression<NumberType>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanNumber")
fun CMJsonField<out Number>.isGreaterOrEqualThan(right: Number): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumberConverter")
fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.isGreaterOrEqualThan(other: Convertable):
    GreaterOrEqualThanExpression<NumberType> = toDopeType().isGreaterOrEqualThan(toDopeType(other))

@JvmName("isGreaterOrEqualThanNumberConverter")
fun <Convertable : Any, JsonType : Number> Convertable.isGreaterOrEqualThan(other: CMConverterField<Convertable, JsonType>):
    GreaterOrEqualThanExpression<NumberType> = toDopeType(other).isGreaterOrEqualThan(other.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun CMJsonField<String>.isGreaterOrEqualThan(right: CMJsonField<String>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun TypeExpression<StringType>.isGreaterOrEqualThan(right: CMJsonField<String>): GreaterOrEqualThanExpression<StringType> =
    isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun String.isGreaterOrEqualThan(right: CMJsonField<String>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun CMJsonField<String>.isGreaterOrEqualThan(right: TypeExpression<StringType>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanString")
fun CMJsonField<String>.isGreaterOrEqualThan(right: String): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanStringConverter")
fun <Convertable : Any> CMConverterField<Convertable, String>.isGreaterOrEqualThan(other: Convertable):
    GreaterOrEqualThanExpression<StringType> = toDopeType().isGreaterOrEqualThan(toDopeType(other))

@JvmName("isGreaterOrEqualThanStringConverter")
fun <Convertable : Any> Convertable.isGreaterOrEqualThan(other: CMConverterField<Convertable, String>):
    GreaterOrEqualThanExpression<StringType> = toDopeType(other).isGreaterOrEqualThan(other.toDopeType())
