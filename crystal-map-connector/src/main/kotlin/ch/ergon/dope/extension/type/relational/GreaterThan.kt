package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.GreaterThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeField
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: CMField<out Number>): GreaterThanExpression<NumberType> =
    toDopeField().isGreaterThan(right.toDopeField())

@JvmName("isGreaterThanNumber")
fun TypeExpression<NumberType>.isGreaterThan(right: CMField<out Number>): GreaterThanExpression<NumberType> =
    isGreaterThan(right.toDopeField())

@JvmName("isGreaterThanNumber")
fun Number.isGreaterThan(right: CMField<out Number>): GreaterThanExpression<NumberType> =
    toDopeType().isGreaterThan(right.toDopeField())

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: TypeExpression<NumberType>): GreaterThanExpression<NumberType> =
    toDopeField().isGreaterThan(right)

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: Number): GreaterThanExpression<NumberType> =
    toDopeField().isGreaterThan(right.toDopeType())

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: CMField<String>): GreaterThanExpression<StringType> =
    toDopeField().isGreaterThan(right.toDopeField())

@JvmName("isGreaterThanString")
fun TypeExpression<StringType>.isGreaterThan(right: CMField<String>): GreaterThanExpression<StringType> =
    isGreaterThan(right.toDopeField())

@JvmName("isGreaterThanString")
fun String.isGreaterThan(right: CMField<String>): GreaterThanExpression<StringType> =
    toDopeType().isGreaterThan(right.toDopeField())

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: TypeExpression<StringType>): GreaterThanExpression<StringType> =
    toDopeField().isGreaterThan(right)

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: String): GreaterThanExpression<StringType> =
    toDopeField().isGreaterThan(right.toDopeType())
