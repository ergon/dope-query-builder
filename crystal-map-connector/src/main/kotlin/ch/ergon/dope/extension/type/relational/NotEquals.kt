package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotEqualsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotEqualToNumber")
fun CMField<out Number>.isNotEqualTo(right: Number): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun Number.isNotEqualTo(right: CMField<out Number>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun CMField<out Number>.isNotEqualTo(right: CMField<out Number>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun TypeExpression<NumberType>.isNotEqualTo(right: CMField<out Number>): NotEqualsExpression<NumberType> =
    isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToNumber")
fun CMField<out Number>.isNotEqualTo(right: TypeExpression<NumberType>): NotEqualsExpression<NumberType> =
    toDopeType().isNotEqualTo(right)

@JvmName("isNotEqualToString")
fun CMField<String>.isNotEqualTo(right: String): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun String.isNotEqualTo(right: CMField<String>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun CMField<String>.isNotEqualTo(right: CMField<String>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun TypeExpression<StringType>.isNotEqualTo(right: CMField<String>): NotEqualsExpression<StringType> =
    isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToString")
fun CMField<String>.isNotEqualTo(right: TypeExpression<StringType>): NotEqualsExpression<StringType> =
    toDopeType().isNotEqualTo(right)

@JvmName("isNotEqualToBoolean")
fun CMField<Boolean>.isNotEqualTo(right: Boolean): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun Boolean.isNotEqualTo(right: CMField<Boolean>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun CMField<Boolean>.isNotEqualTo(right: CMField<Boolean>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun TypeExpression<BooleanType>.isNotEqualTo(right: CMField<Boolean>): NotEqualsExpression<BooleanType> =
    isNotEqualTo(right.toDopeType())

@JvmName("isNotEqualToBoolean")
fun CMField<Boolean>.isNotEqualTo(right: TypeExpression<BooleanType>): NotEqualsExpression<BooleanType> =
    toDopeType().isNotEqualTo(right)
