package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.between
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField

@JvmName("betweenNumber")
fun CMField<out Number>.between(start: CMField<out Number>, end: CMField<out Number>): BetweenExpression<NumberType> =
    toDopeType().between(start.toDopeType(), end.toDopeType())

@JvmName("betweenNumber")
fun CMField<out Number>.between(start: CMField<out Number>, end: TypeExpression<NumberType>): BetweenExpression<NumberType> =
    toDopeType().between(start.toDopeType(), end)

@JvmName("betweenNumber")
fun CMField<out Number>.between(start: TypeExpression<NumberType>, end: CMField<out Number>): BetweenExpression<NumberType> =
    toDopeType().between(start, end.toDopeType())

@JvmName("betweenNumber")
fun CMField<out Number>.between(start: TypeExpression<NumberType>, end: TypeExpression<NumberType>): BetweenExpression<NumberType> =
    toDopeType().between(start, end)

@JvmName("betweenNumber")
fun TypeExpression<NumberType>.between(start: CMField<out Number>, end: CMField<out Number>): BetweenExpression<NumberType> =
    this.between(start.toDopeType(), end.toDopeType())

@JvmName("betweenNumber")
fun TypeExpression<NumberType>.between(start: CMField<out Number>, end: TypeExpression<NumberType>): BetweenExpression<NumberType> =
    this.between(start.toDopeType(), end)

@JvmName("betweenNumber")
fun TypeExpression<NumberType>.between(start: TypeExpression<NumberType>, end: CMField<out Number>): BetweenExpression<NumberType> =
    this.between(start, end.toDopeType())

@JvmName("betweenString")
fun CMField<String>.between(start: CMField<String>, end: CMField<String>): BetweenExpression<StringType> =
    toDopeType().between(start.toDopeType(), end.toDopeType())

@JvmName("betweenString")
fun CMField<String>.between(start: CMField<String>, end: TypeExpression<StringType>): BetweenExpression<StringType> =
    toDopeType().between(start.toDopeType(), end)

@JvmName("betweenString")
fun CMField<String>.between(start: TypeExpression<StringType>, end: CMField<String>): BetweenExpression<StringType> =
    toDopeType().between(start, end.toDopeType())

@JvmName("betweenString")
fun CMField<String>.between(start: TypeExpression<StringType>, end: TypeExpression<StringType>): BetweenExpression<StringType> =
    toDopeType().between(start, end)

@JvmName("betweenString")
fun TypeExpression<StringType>.between(start: CMField<String>, end: CMField<String>): BetweenExpression<StringType> =
    this.between(start.toDopeType(), end.toDopeType())

@JvmName("betweenString")
fun TypeExpression<StringType>.between(start: CMField<String>, end: TypeExpression<StringType>): BetweenExpression<StringType> =
    this.between(start.toDopeType(), end)

@JvmName("betweenString")
fun TypeExpression<StringType>.between(start: TypeExpression<StringType>, end: CMField<String>): BetweenExpression<StringType> =
    this.between(start, end.toDopeType())
