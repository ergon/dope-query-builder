package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.GreaterOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeField
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<out Number>.isGreaterOrEqualThan(right: CMField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    toDopeField().isGreaterOrEqualThan(right.toDopeField())

@JvmName("isGreaterOrEqualThanNumber")
fun TypeExpression<NumberType>.isGreaterOrEqualThan(right: CMField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    isGreaterOrEqualThan(right.toDopeField())

@JvmName("isGreaterOrEqualThanNumber")
fun Number.isGreaterOrEqualThan(right: CMField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeField())

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<out Number>.isGreaterOrEqualThan(right: TypeExpression<NumberType>): GreaterOrEqualThanExpression<NumberType> =
    toDopeField().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<out Number>.isGreaterOrEqualThan(right: Number): GreaterOrEqualThanExpression<NumberType> =
    toDopeField().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<StringType> =
    toDopeField().isGreaterOrEqualThan(right.toDopeField())

@JvmName("isGreaterOrEqualThanString")
fun TypeExpression<StringType>.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<StringType> =
    isGreaterOrEqualThan(right.toDopeField())

@JvmName("isGreaterOrEqualThanString")
fun String.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeField())

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: TypeExpression<StringType>): GreaterOrEqualThanExpression<StringType> =
    toDopeField().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: String): GreaterOrEqualThanExpression<StringType> =
    toDopeField().isGreaterOrEqualThan(right.toDopeType())
