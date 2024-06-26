package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LessOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isLessOrEqualThanNumber")
fun CMField<out Number>.isLessOrEqualThan(right: CMField<out Number>): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanNumber")
fun TypeExpression<NumberType>.isLessOrEqualThan(right: CMField<out Number>): LessOrEqualThanExpression<NumberType> =
    isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanNumber")
fun Number.isLessOrEqualThan(right: CMField<out Number>): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanNumber")
fun CMField<out Number>.isLessOrEqualThan(right: TypeExpression<NumberType>): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right)

@JvmName("isLessOrEqualThanNumber")
fun CMField<out Number>.isLessOrEqualThan(right: Number): LessOrEqualThanExpression<NumberType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanString")
fun CMField<String>.isLessOrEqualThan(right: CMField<String>): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanString")
fun TypeExpression<StringType>.isLessOrEqualThan(right: CMField<String>): LessOrEqualThanExpression<StringType> =
    isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanString")
fun String.isLessOrEqualThan(right: CMField<String>): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())

@JvmName("isLessOrEqualThanString")
fun CMField<String>.isLessOrEqualThan(right: TypeExpression<StringType>): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right)

@JvmName("isLessOrEqualThanString")
fun CMField<String>.isLessOrEqualThan(right: String): LessOrEqualThanExpression<StringType> =
    toDopeType().isLessOrEqualThan(right.toDopeType())
