package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.GreaterOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<out Number>.isGreaterOrEqualThan(right: CMField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumber")
fun TypeExpression<NumberType>.isGreaterOrEqualThan(right: CMField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumber")
fun Number.isGreaterOrEqualThan(right: CMField<out Number>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<out Number>.isGreaterOrEqualThan(right: TypeExpression<NumberType>): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanNumber")
fun CMField<out Number>.isGreaterOrEqualThan(right: Number): GreaterOrEqualThanExpression<NumberType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun TypeExpression<StringType>.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<StringType> =
    isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun String.isGreaterOrEqualThan(right: CMField<String>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: TypeExpression<StringType>): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right)

@JvmName("isGreaterOrEqualThanString")
fun CMField<String>.isGreaterOrEqualThan(right: String): GreaterOrEqualThanExpression<StringType> =
    toDopeType().isGreaterOrEqualThan(right.toDopeType())
