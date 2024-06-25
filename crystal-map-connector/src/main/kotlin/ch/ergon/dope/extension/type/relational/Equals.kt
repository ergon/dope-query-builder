package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeField
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isEqualToNumber")
fun CMField<out Number>.isEqualTo(right: Number): EqualsExpression<NumberType> =
    toDopeField().isEqualTo(right.toDopeType())

@JvmName("isEqualToNumber")
fun Number.isEqualTo(right: CMField<out Number>): EqualsExpression<NumberType> =
    toDopeType().isEqualTo(right.toDopeField())

@JvmName("isEqualToNumber")
fun CMField<out Number>.isEqualTo(right: CMField<out Number>): EqualsExpression<NumberType> =
    toDopeField().isEqualTo(right.toDopeField())

@JvmName("isEqualToNumber")
fun TypeExpression<NumberType>.isEqualTo(right: CMField<out Number>): EqualsExpression<NumberType> =
    isEqualTo(right.toDopeField())

@JvmName("isEqualToNumber")
fun CMField<out Number>.isEqualTo(right: TypeExpression<NumberType>): EqualsExpression<NumberType> =
    toDopeField().isEqualTo(right)

@JvmName("isEqualToString")
fun CMField<String>.isEqualTo(right: String): EqualsExpression<StringType> =
    toDopeField().isEqualTo(right.toDopeType())

@JvmName("isEqualToString")
fun String.isEqualTo(right: CMField<String>): EqualsExpression<StringType> =
    toDopeType().isEqualTo(right.toDopeField())

@JvmName("isEqualToString")
fun CMField<String>.isEqualTo(right: CMField<String>): EqualsExpression<StringType> =
    toDopeField().isEqualTo(right.toDopeField())

@JvmName("isEqualToString")
fun TypeExpression<StringType>.isEqualTo(right: CMField<String>): EqualsExpression<StringType> =
    isEqualTo(right.toDopeField())

@JvmName("isEqualToString")
fun CMField<String>.isEqualTo(right: TypeExpression<StringType>): EqualsExpression<StringType> =
    toDopeField().isEqualTo(right)

@JvmName("isEqualToBoolean")
fun CMField<Boolean>.isEqualTo(right: Boolean): EqualsExpression<BooleanType> =
    toDopeField().isEqualTo(right.toDopeType())

@JvmName("isEqualToBoolean")
fun Boolean.isEqualTo(right: CMField<Boolean>): EqualsExpression<BooleanType> =
    toDopeType().isEqualTo(right.toDopeField())

@JvmName("isEqualToBoolean")
fun CMField<Boolean>.isEqualTo(right: CMField<Boolean>): EqualsExpression<BooleanType> =
    toDopeField().isEqualTo(right.toDopeField())

@JvmName("isEqualToBoolean")
fun TypeExpression<BooleanType>.isEqualTo(right: CMField<Boolean>): EqualsExpression<BooleanType> =
    isEqualTo(right.toDopeField())

@JvmName("isEqualToBoolean")
fun CMField<Boolean>.isEqualTo(right: TypeExpression<BooleanType>): EqualsExpression<BooleanType> =
    toDopeField().isEqualTo(right)
