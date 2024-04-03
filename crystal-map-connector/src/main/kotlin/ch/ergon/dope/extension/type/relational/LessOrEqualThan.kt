package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LessOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isLessOrEqualThanNumber")
fun CMField<out Number>.isLessOrEqualThan(right: CMField<out Number>): LessOrEqualThanExpression<NumberType> =
    asField().isLessOrEqualThan(right.asField())

@JvmName("isLessOrEqualThanNumber")
fun TypeExpression<NumberType>.isLessOrEqualThan(right: CMField<out Number>): LessOrEqualThanExpression<NumberType> =
    isLessOrEqualThan(right.asField())

@JvmName("isLessOrEqualThanNumber")
fun Number.isLessOrEqualThan(right: CMField<out Number>): LessOrEqualThanExpression<NumberType> =
    toNumberType().isLessOrEqualThan(right.asField())

@JvmName("isLessOrEqualThanNumber")
fun CMField<out Number>.isLessOrEqualThan(right: TypeExpression<NumberType>): LessOrEqualThanExpression<NumberType> =
    asField().isLessOrEqualThan(right)

@JvmName("isLessOrEqualThanNumber")
fun CMField<out Number>.isLessOrEqualThan(right: Number): LessOrEqualThanExpression<NumberType> =
    asField().isLessOrEqualThan(right.toNumberType())

@JvmName("isLessOrEqualThanString")
fun CMField<String>.isLessOrEqualThan(right: CMField<String>): LessOrEqualThanExpression<StringType> =
    asField().isLessOrEqualThan(right.asField())

@JvmName("isLessOrEqualThanString")
fun TypeExpression<StringType>.isLessOrEqualThan(right: CMField<String>): LessOrEqualThanExpression<StringType> =
    isLessOrEqualThan(right.asField())

@JvmName("isLessOrEqualThanString")
fun String.isLessOrEqualThan(right: CMField<String>): LessOrEqualThanExpression<StringType> =
    toStringType().isLessOrEqualThan(right.asField())

@JvmName("isLessOrEqualThanString")
fun CMField<String>.isLessOrEqualThan(right: TypeExpression<StringType>): LessOrEqualThanExpression<StringType> =
    asField().isLessOrEqualThan(right)

@JvmName("isLessOrEqualThanString")
fun CMField<String>.isLessOrEqualThan(right: String): LessOrEqualThanExpression<StringType> =
    asField().isLessOrEqualThan(right.toStringType())
