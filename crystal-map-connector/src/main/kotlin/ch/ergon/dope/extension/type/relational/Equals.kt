package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isEqualToNumber")
fun CMField<out Number>.isEqualTo(right: Number): EqualsExpression<NumberType> =
    asField().isEqualTo(right.toNumberType())

@JvmName("isEqualToNumber")
fun Number.isEqualTo(right: CMField<out Number>): EqualsExpression<NumberType> =
    toNumberType().isEqualTo(right.asField())

@JvmName("isEqualToNumber")
fun CMField<out Number>.isEqualTo(right: CMField<out Number>): EqualsExpression<NumberType> =
    asField().isEqualTo(right.asField())

@JvmName("isEqualToNumber")
fun TypeExpression<NumberType>.isEqualTo(right: CMField<out Number>): EqualsExpression<NumberType> =
    isEqualTo(right.asField())

@JvmName("isEqualToNumber")
fun CMField<out Number>.isEqualTo(right: TypeExpression<NumberType>): EqualsExpression<NumberType> =
    asField().isEqualTo(right)

@JvmName("isEqualToString")
fun CMField<String>.isEqualTo(right: String): EqualsExpression<StringType> =
    asField().isEqualTo(right.toStringType())

@JvmName("isEqualToString")
fun String.isEqualTo(right: CMField<String>): EqualsExpression<StringType> =
    toStringType().isEqualTo(right.asField())

@JvmName("isEqualToString")
fun CMField<String>.isEqualTo(right: CMField<String>): EqualsExpression<StringType> =
    asField().isEqualTo(right.asField())

@JvmName("isEqualToString")
fun TypeExpression<StringType>.isEqualTo(right: CMField<String>): EqualsExpression<StringType> =
    isEqualTo(right.asField())

@JvmName("isEqualToString")
fun CMField<String>.isEqualTo(right: TypeExpression<StringType>): EqualsExpression<StringType> =
    asField().isEqualTo(right)

@JvmName("isEqualToBoolean")
fun CMField<Boolean>.isEqualTo(right: Boolean): EqualsExpression<BooleanType> =
    asField().isEqualTo(right.toBooleanType())

@JvmName("isEqualToBoolean")
fun Boolean.isEqualTo(right: CMField<Boolean>): EqualsExpression<BooleanType> =
    toBooleanType().isEqualTo(right.asField())

@JvmName("isEqualToBoolean")
fun CMField<Boolean>.isEqualTo(right: CMField<Boolean>): EqualsExpression<BooleanType> =
    asField().isEqualTo(right.asField())

@JvmName("isEqualToBoolean")
fun TypeExpression<BooleanType>.isEqualTo(right: CMField<Boolean>): EqualsExpression<BooleanType> =
    isEqualTo(right.asField())

@JvmName("isEqualToBoolean")
fun CMField<Boolean>.isEqualTo(right: TypeExpression<BooleanType>): EqualsExpression<BooleanType> =
    asField().isEqualTo(right)
