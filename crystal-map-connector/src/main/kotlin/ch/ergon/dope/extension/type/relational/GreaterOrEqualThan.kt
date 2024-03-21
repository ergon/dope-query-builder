package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.GreaterOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<Number>.isGreaterOrEqualThan(right: CMField<Number>): GreaterOrEqualThanExpression<ComparableType> =
    asField().isGreaterOrEqualThan(right.asField())

@JvmName("isGreaterOrEqualThanNumber")
fun TypeExpression<NumberType>.isGreaterOrEqualThan(right: CMField<Number>): GreaterOrEqualThanExpression<ComparableType> =
    isGreaterOrEqualThan(right.asField())

@JvmName("isGreaterOrEqualThanNumber")
fun Number.isGreaterOrEqualThan(right: CMField<Number>): GreaterOrEqualThanExpression<ComparableType> =
    toNumberType().isGreaterOrEqualThan(right.asField())

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<Number>.isGreaterOrEqualThan(right: TypeExpression<NumberType>): GreaterOrEqualThanExpression<ComparableType> =
    asField().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<Number>.isGreaterOrEqualThan(right: Number): GreaterOrEqualThanExpression<ComparableType> =
    asField().isGreaterOrEqualThan(right.toNumberType())

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<ComparableType> =
    asField().isGreaterOrEqualThan(right.asField())

@JvmName("isGreaterOrEqualThanString")
fun TypeExpression<StringType>.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<ComparableType> =
    isGreaterOrEqualThan(right.asField())

@JvmName("isGreaterOrEqualThanString")
fun String.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<ComparableType> =
    toStringType().isGreaterOrEqualThan(right.asField())

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: TypeExpression<StringType>): GreaterOrEqualThanExpression<ComparableType> =
    asField().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: String): GreaterOrEqualThanExpression<ComparableType> =
    asField().isGreaterOrEqualThan(right.toStringType())
