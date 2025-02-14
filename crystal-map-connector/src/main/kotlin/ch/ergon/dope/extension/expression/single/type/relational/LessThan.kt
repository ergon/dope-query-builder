package ch.ergon.dope.extension.expression.single.type.relational

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.relational.LessThanExpression
import ch.ergon.dope.resolvable.expression.single.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("isLessThanNumber")
fun CMJsonField<out Number>.isLessThan(right: CMJsonField<out Number>): LessThanExpression<NumberType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanNumber")
fun TypeExpression<NumberType>.isLessThan(right: CMJsonField<out Number>): LessThanExpression<NumberType> =
    isLessThan(right.toDopeType())

@JvmName("isLessThanNumber")
fun Number.isLessThan(right: CMJsonField<out Number>): LessThanExpression<NumberType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanNumber")
fun CMJsonField<out Number>.isLessThan(right: TypeExpression<NumberType>): LessThanExpression<NumberType> =
    toDopeType().isLessThan(right)

@JvmName("isLessThanNumber")
fun CMJsonField<out Number>.isLessThan(right: Number): LessThanExpression<NumberType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanNumberConverter")
fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.isLessThan(other: Convertable):
    LessThanExpression<NumberType> = toDopeType().isLessThan(toDopeType(other))

@JvmName("isLessThanNumberConverter")
fun <Convertable : Any, JsonType : Number> Convertable.isLessThan(other: CMConverterField<Convertable, JsonType>):
    LessThanExpression<NumberType> = toDopeType(other).isLessThan(other.toDopeType())

@JvmName("isLessThanString")
fun CMJsonField<String>.isLessThan(right: CMJsonField<String>): LessThanExpression<StringType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanString")
fun TypeExpression<StringType>.isLessThan(right: CMJsonField<String>): LessThanExpression<StringType> =
    isLessThan(right.toDopeType())

@JvmName("isLessThanString")
fun String.isLessThan(right: CMJsonField<String>): LessThanExpression<StringType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanString")
fun CMJsonField<String>.isLessThan(right: TypeExpression<StringType>): LessThanExpression<StringType> =
    toDopeType().isLessThan(right)

@JvmName("isLessThanString")
fun CMJsonField<String>.isLessThan(right: String): LessThanExpression<StringType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanStringConverter")
fun <JsonType : Any> CMConverterField<JsonType, String>.isLessThan(other: JsonType): LessThanExpression<StringType> =
    toDopeType().isLessThan(toDopeType(other))

@JvmName("isLessThanStringConverter")
fun <Convertable : Any> Convertable.isLessThan(other: CMConverterField<Convertable, String>): LessThanExpression<StringType> =
    toDopeType(other).isLessThan(other.toDopeType())
