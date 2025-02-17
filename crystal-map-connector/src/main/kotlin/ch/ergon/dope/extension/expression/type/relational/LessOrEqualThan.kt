package ch.ergon.dope.extension.expression.type.relational

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.relational.LessOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("isLessOrEqualThanNumber")
fun CMJsonField<out Number>.isLessOrEqualThan(right: CMJsonField<out Number>): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanNumber")
fun TypeExpression<NumberType>.isLessOrEqualThan(right: CMJsonField<out Number>): LessOrEqualThanExpression<NumberType> =
    isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanNumber")
fun Number.isLessOrEqualThan(right: CMJsonField<out Number>): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanNumber")
fun CMJsonField<out Number>.isLessOrEqualThan(right: TypeExpression<NumberType>): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right)

@JvmName("isLessOrEqualThanNumber")
fun CMJsonField<out Number>.isLessOrEqualThan(right: Number): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanNumberConverter")
fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.isLessOrEqualThan(other: Convertable):
    LessOrEqualThanExpression<NumberType> = toDopeType().isLessOrEqualThan(toDopeType(other))

@JvmName("isLessOrEqualThanNumberConverter")
fun <Convertable : Any, JsonType : Number> Convertable.isLessOrEqualThan(other: CMConverterField<Convertable, JsonType>):
    LessOrEqualThanExpression<NumberType> = toDopeType(other).isLessOrEqualThan(other.toDopeType())

@JvmName("isLessOrEqualThanString")
fun CMJsonField<String>.isLessOrEqualThan(right: CMJsonField<String>): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanString")
fun TypeExpression<StringType>.isLessOrEqualThan(right: CMJsonField<String>): LessOrEqualThanExpression<StringType> =
    isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanString")
fun String.isLessOrEqualThan(right: CMJsonField<String>): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanString")
fun CMJsonField<String>.isLessOrEqualThan(right: TypeExpression<StringType>): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right)

@JvmName("isLessOrEqualThanString")
fun CMJsonField<String>.isLessOrEqualThan(right: String): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanStringConverter")
fun <JsonType : Any> CMConverterField<JsonType, String>.isLessOrEqualThan(other: JsonType): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(toDopeType(other))

@JvmName("isLessOrEqualThanStringConverter")
fun <Convertable : Any> Convertable.isLessOrEqualThan(other: CMConverterField<Convertable, String>): LessOrEqualThanExpression<StringType> =
    toDopeType(other).isLessOrEqualThan(other.toDopeType())
