package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LessThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeField
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isLessThanNumber")
fun CMField<out Number>.isLessThan(right: CMField<out Number>): LessThanExpression<NumberType> =
    toDopeField().isLessThan(right.toDopeField())

@JvmName("isLessThanNumber")
fun TypeExpression<NumberType>.isLessThan(right: CMField<out Number>): LessThanExpression<NumberType> =
    isLessThan(right.toDopeField())

@JvmName("isLessThanNumber")
fun Number.isLessThan(right: CMField<out Number>): LessThanExpression<NumberType> =
    toDopeType().isLessThan(right.toDopeField())

@JvmName("isLessThanNumber")
fun CMField<out Number>.isLessThan(right: TypeExpression<NumberType>): LessThanExpression<NumberType> =
    toDopeField().isLessThan(right)

@JvmName("isLessThanNumber")
fun CMField<out Number>.isLessThan(right: Number): LessThanExpression<NumberType> =
    toDopeField().isLessThan(right.toDopeType())

@JvmName("isLessThanString")
fun CMField<String>.isLessThan(right: CMField<String>): LessThanExpression<StringType> =
    toDopeField().isLessThan(right.toDopeField())

@JvmName("isLessThanString")
fun TypeExpression<StringType>.isLessThan(right: CMField<String>): LessThanExpression<StringType> =
    isLessThan(right.toDopeField())

@JvmName("isLessThanString")
fun String.isLessThan(right: CMField<String>): LessThanExpression<StringType> =
    toDopeType().isLessThan(right.toDopeField())

@JvmName("isLessThanString")
fun CMField<String>.isLessThan(right: TypeExpression<StringType>): LessThanExpression<StringType> =
    toDopeField().isLessThan(right)

@JvmName("isLessThanString")
fun CMField<String>.isLessThan(right: String): LessThanExpression<StringType> =
    toDopeField().isLessThan(right.toDopeType())
