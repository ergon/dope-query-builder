package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.GreaterThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: CMField<out Number>): GreaterThanExpression<NumberType> =
    asField().isGreaterThan(right.asField())

@JvmName("isGreaterThanNumber")
fun TypeExpression<NumberType>.isGreaterThan(right: CMField<out Number>): GreaterThanExpression<NumberType> =
    isGreaterThan(right.asField())

@JvmName("isGreaterThanNumber")
fun Number.isGreaterThan(right: CMField<out Number>): GreaterThanExpression<NumberType> =
    toNumberType().isGreaterThan(right.asField())

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: TypeExpression<NumberType>): GreaterThanExpression<NumberType> =
    asField().isGreaterThan(right)

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: Number): GreaterThanExpression<NumberType> =
    asField().isGreaterThan(right.toNumberType())

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: CMField<String>): GreaterThanExpression<StringType> =
    asField().isGreaterThan(right.asField())

@JvmName("isGreaterThanString")
fun TypeExpression<StringType>.isGreaterThan(right: CMField<String>): GreaterThanExpression<StringType> =
    isGreaterThan(right.asField())

@JvmName("isGreaterThanString")
fun String.isGreaterThan(right: CMField<String>): GreaterThanExpression<StringType> =
    toStringType().isGreaterThan(right.asField())

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: TypeExpression<StringType>): GreaterThanExpression<StringType> =
    asField().isGreaterThan(right)

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: String): GreaterThanExpression<StringType> =
    asField().isGreaterThan(right.toStringType())
