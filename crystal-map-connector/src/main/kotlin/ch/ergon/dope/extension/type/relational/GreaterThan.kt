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
fun CMField<Number>.isGreaterThan(right: CMField<Number>): GreaterThanExpression<ComparableType> =
    asField().isGreaterThan(right.asField())

@JvmName("isGreaterThanNumber")
fun TypeExpression<NumberType>.isGreaterThan(right: CMField<Number>): GreaterThanExpression<ComparableType> =
    isGreaterThan(right.asField())

@JvmName("isGreaterThanNumber")
fun Number.isGreaterThan(right: CMField<Number>): GreaterThanExpression<ComparableType> =
    toNumberType().isGreaterThan(right.asField())

@JvmName("isGreaterThanNumber")
fun CMField<Number>.isGreaterThan(right: TypeExpression<NumberType>): GreaterThanExpression<ComparableType> =
    asField().isGreaterThan(right)

@JvmName("isGreaterThanNumber")
fun CMField<out Number>.isGreaterThan(right: Number): GreaterThanExpression<ComparableType> =
    asField().isGreaterThan(right.toNumberType())

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: CMField<String>): GreaterThanExpression<ComparableType> =
    asField().isGreaterThan(right.asField())

@JvmName("isGreaterThanString")
fun TypeExpression<StringType>.isGreaterThan(right: CMField<String>): GreaterThanExpression<ComparableType> =
    isGreaterThan(right.asField())

@JvmName("isGreaterThanString")
fun String.isGreaterThan(right: CMField<String>): GreaterThanExpression<ComparableType> =
    toStringType().isGreaterThan(right.asField())

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: TypeExpression<StringType>): GreaterThanExpression<ComparableType> =
    asField().isGreaterThan(right)

@JvmName("isGreaterThanString")
fun CMField<String>.isGreaterThan(right: String): GreaterThanExpression<ComparableType> =
    asField().isGreaterThan(right.toStringType())
