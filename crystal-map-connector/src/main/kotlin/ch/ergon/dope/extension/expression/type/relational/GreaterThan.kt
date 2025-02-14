package ch.ergon.dope.extension.expression.type.relational

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.relational.GreaterThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("isGreaterThanNumber")
fun CMJsonField<out Number>.isGreaterThan(right: CMJsonField<out Number>): GreaterThanExpression<NumberType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanNumber")
fun TypeExpression<NumberType>.isGreaterThan(right: CMJsonField<out Number>): GreaterThanExpression<NumberType> =
    isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanNumber")
fun Number.isGreaterThan(right: CMJsonField<out Number>): GreaterThanExpression<NumberType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanNumber")
fun CMJsonField<out Number>.isGreaterThan(right: TypeExpression<NumberType>): GreaterThanExpression<NumberType> =
    toDopeType().isGreaterThan(right)

@JvmName("isGreaterThanNumber")
fun CMJsonField<out Number>.isGreaterThan(right: Number): GreaterThanExpression<NumberType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanNumberConverter")
fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.isGreaterThan(other: Convertable):
    GreaterThanExpression<NumberType> = toDopeType().isGreaterThan(toDopeType(other))

@JvmName("isGreaterThanNumberConverter")
fun <Convertable : Any, JsonType : Number> Convertable.isGreaterThan(other: CMConverterField<Convertable, JsonType>):
    GreaterThanExpression<NumberType> = toDopeType(other).isGreaterThan(other.toDopeType())

@JvmName("isGreaterThanString")
fun CMJsonField<String>.isGreaterThan(right: CMJsonField<String>): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanString")
fun TypeExpression<StringType>.isGreaterThan(right: CMJsonField<String>): GreaterThanExpression<StringType> =
    isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanString")
fun String.isGreaterThan(right: CMJsonField<String>): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanString")
fun CMJsonField<String>.isGreaterThan(right: TypeExpression<StringType>): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(right)

@JvmName("isGreaterThanString")
fun CMJsonField<String>.isGreaterThan(right: String): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanStringConverter")
fun <JsonType : Any> CMConverterField<JsonType, String>.isGreaterThan(other: JsonType): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(toDopeType(other))

@JvmName("isGreaterThanStringConverter")
fun <Convertable : Any> Convertable.isGreaterThan(other: CMConverterField<Convertable, String>): GreaterThanExpression<StringType> =
    toDopeType(other).isGreaterThan(other.toDopeType())
