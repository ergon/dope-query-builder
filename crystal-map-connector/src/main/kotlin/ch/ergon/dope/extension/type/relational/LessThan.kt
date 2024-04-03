package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LessThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.validtype.ComparableType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isLessThanNumber")
fun CMField<out Number>.isLessThan(right: CMField<out Number>): LessThanExpression<NumberType> =
    asField().isLessThan(right.asField())

@JvmName("isLessThanNumber")
fun TypeExpression<NumberType>.isLessThan(right: CMField<out Number>): LessThanExpression<NumberType> =
    isLessThan(right.asField())

@JvmName("isLessThanNumber")
fun Number.isLessThan(right: CMField<out Number>): LessThanExpression<NumberType> =
    toNumberType().isLessThan(right.asField())

@JvmName("isLessThanNumber")
fun CMField<out Number>.isLessThan(right: TypeExpression<NumberType>): LessThanExpression<NumberType> =
    asField().isLessThan(right)

@JvmName("isLessThanNumber")
fun CMField<out Number>.isLessThan(right: Number): LessThanExpression<NumberType> =
    asField().isLessThan(right.toNumberType())

@JvmName("isLessThanString")
fun CMField<String>.isLessThan(right: CMField<String>): LessThanExpression<StringType> =
    asField().isLessThan(right.asField())

@JvmName("isLessThanString")
fun TypeExpression<StringType>.isLessThan(right: CMField<String>): LessThanExpression<StringType> =
    isLessThan(right.asField())

@JvmName("isLessThanString")
fun String.isLessThan(right: CMField<String>): LessThanExpression<StringType> =
    toStringType().isLessThan(right.asField())

@JvmName("isLessThanString")
fun CMField<String>.isLessThan(right: TypeExpression<StringType>): LessThanExpression<StringType> =
    asField().isLessThan(right)

@JvmName("isLessThanString")
fun CMField<String>.isLessThan(right: String): LessThanExpression<StringType> =
    asField().isLessThan(right.toStringType())
