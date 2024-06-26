package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LessThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isLessThanNumber")
fun CMField<out Number>.isLessThan(right: CMField<out Number>): LessThanExpression<NumberType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanNumber")
fun TypeExpression<NumberType>.isLessThan(right: CMField<out Number>): LessThanExpression<NumberType> =
    isLessThan(right.toDopeType())

@JvmName("isLessThanNumber")
fun Number.isLessThan(right: CMField<out Number>): LessThanExpression<NumberType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanNumber")
fun CMField<out Number>.isLessThan(right: TypeExpression<NumberType>): LessThanExpression<NumberType> =
    toDopeType().isLessThan(right)

@JvmName("isLessThanNumber")
fun CMField<out Number>.isLessThan(right: Number): LessThanExpression<NumberType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanString")
fun CMField<String>.isLessThan(right: CMField<String>): LessThanExpression<StringType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanString")
fun TypeExpression<StringType>.isLessThan(right: CMField<String>): LessThanExpression<StringType> =
    isLessThan(right.toDopeType())

@JvmName("isLessThanString")
fun String.isLessThan(right: CMField<String>): LessThanExpression<StringType> =
    toDopeType().isLessThan(right.toDopeType())

@JvmName("isLessThanString")
fun CMField<String>.isLessThan(right: TypeExpression<StringType>): LessThanExpression<StringType> =
    toDopeType().isLessThan(right)

@JvmName("isLessThanString")
fun CMField<String>.isLessThan(right: String): LessThanExpression<StringType> =
    toDopeType().isLessThan(right.toDopeType())
