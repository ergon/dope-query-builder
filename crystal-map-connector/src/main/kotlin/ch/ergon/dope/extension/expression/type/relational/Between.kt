package ch.ergon.dope.extension.expression.type.relational

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotBetweenExpression
import ch.ergon.dope.resolvable.expression.type.relational.between
import ch.ergon.dope.resolvable.expression.type.relational.notBetween
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("betweenNumber")
fun CMJsonField<out Number>.between(start: CMJsonField<out Number>, end: CMJsonField<out Number>): BetweenExpression<NumberType> =
    toDopeType().between(start.toDopeType(), end.toDopeType())

@JvmName("betweenNumber")
fun CMJsonField<out Number>.between(start: CMJsonField<out Number>, end: TypeExpression<NumberType>): BetweenExpression<NumberType> =
    toDopeType().between(start.toDopeType(), end)

@JvmName("betweenNumber")
fun CMJsonField<out Number>.between(start: TypeExpression<NumberType>, end: CMJsonField<out Number>): BetweenExpression<NumberType> =
    toDopeType().between(start, end.toDopeType())

@JvmName("betweenNumber")
fun CMJsonField<out Number>.between(start: TypeExpression<NumberType>, end: TypeExpression<NumberType>): BetweenExpression<NumberType> =
    toDopeType().between(start, end)

@JvmName("betweenNumber")
fun TypeExpression<NumberType>.between(start: CMJsonField<out Number>, end: CMJsonField<out Number>): BetweenExpression<NumberType> =
    between(start.toDopeType(), end.toDopeType())

@JvmName("betweenNumber")
fun TypeExpression<NumberType>.between(start: CMJsonField<out Number>, end: TypeExpression<NumberType>): BetweenExpression<NumberType> =
    between(start.toDopeType(), end)

@JvmName("betweenNumber")
fun TypeExpression<NumberType>.between(start: TypeExpression<NumberType>, end: CMJsonField<out Number>): BetweenExpression<NumberType> =
    between(start, end.toDopeType())

@JvmName("betweenNumber")
fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.between(start: Convertable, end: Convertable):
    BetweenExpression<NumberType> = toDopeType().between(toDopeType(start), toDopeType(end))

@JvmName("betweenString")
fun CMJsonField<String>.between(start: CMJsonField<String>, end: CMJsonField<String>): BetweenExpression<StringType> =
    toDopeType().between(start.toDopeType(), end.toDopeType())

@JvmName("betweenString")
fun CMJsonField<String>.between(start: CMJsonField<String>, end: TypeExpression<StringType>): BetweenExpression<StringType> =
    toDopeType().between(start.toDopeType(), end)

@JvmName("betweenString")
fun CMJsonField<String>.between(start: TypeExpression<StringType>, end: CMJsonField<String>): BetweenExpression<StringType> =
    toDopeType().between(start, end.toDopeType())

@JvmName("betweenString")
fun CMJsonField<String>.between(start: TypeExpression<StringType>, end: TypeExpression<StringType>): BetweenExpression<StringType> =
    toDopeType().between(start, end)

@JvmName("betweenString")
fun TypeExpression<StringType>.between(start: CMJsonField<String>, end: CMJsonField<String>): BetweenExpression<StringType> =
    between(start.toDopeType(), end.toDopeType())

@JvmName("betweenString")
fun TypeExpression<StringType>.between(start: CMJsonField<String>, end: TypeExpression<StringType>): BetweenExpression<StringType> =
    between(start.toDopeType(), end)

@JvmName("betweenString")
fun TypeExpression<StringType>.between(start: TypeExpression<StringType>, end: CMJsonField<String>): BetweenExpression<StringType> =
    between(start, end.toDopeType())

@JvmName("betweenString")
fun <Convertable : Any> CMConverterField<Convertable, String>.between(start: Convertable, end: Convertable): BetweenExpression<StringType> =
    toDopeType().between(toDopeType(start), toDopeType(end))

@JvmName("notBetweenNumber")
fun CMJsonField<out Number>.notBetween(start: CMJsonField<out Number>, end: CMJsonField<out Number>): NotBetweenExpression<NumberType> =
    toDopeType().notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenNumber")
fun CMJsonField<out Number>.notBetween(start: CMJsonField<out Number>, end: TypeExpression<NumberType>): NotBetweenExpression<NumberType> =
    toDopeType().notBetween(start.toDopeType(), end)

@JvmName("notBetweenNumber")
fun CMJsonField<out Number>.notBetween(start: TypeExpression<NumberType>, end: CMJsonField<out Number>): NotBetweenExpression<NumberType> =
    toDopeType().notBetween(start, end.toDopeType())

@JvmName("notBetweenNumber")
fun CMJsonField<out Number>.notBetween(start: TypeExpression<NumberType>, end: TypeExpression<NumberType>): NotBetweenExpression<NumberType> =
    toDopeType().notBetween(start, end)

@JvmName("notBetweenNumber")
fun TypeExpression<NumberType>.notBetween(start: CMJsonField<out Number>, end: CMJsonField<out Number>): NotBetweenExpression<NumberType> =
    this.notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenNumber")
fun TypeExpression<NumberType>.notBetween(start: CMJsonField<out Number>, end: TypeExpression<NumberType>): NotBetweenExpression<NumberType> =
    this.notBetween(start.toDopeType(), end)

@JvmName("notBetweenNumber")
fun TypeExpression<NumberType>.notBetween(start: TypeExpression<NumberType>, end: CMJsonField<out Number>): NotBetweenExpression<NumberType> =
    this.notBetween(start, end.toDopeType())

@JvmName("notBetweenNumber")
fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.notBetween(start: Convertable, end: Convertable):
    NotBetweenExpression<NumberType> = toDopeType().notBetween(toDopeType(start), toDopeType(end))

@JvmName("notBetweenString")
fun CMJsonField<String>.notBetween(start: CMJsonField<String>, end: CMJsonField<String>): NotBetweenExpression<StringType> =
    toDopeType().notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenString")
fun CMJsonField<String>.notBetween(start: CMJsonField<String>, end: TypeExpression<StringType>): NotBetweenExpression<StringType> =
    toDopeType().notBetween(start.toDopeType(), end)

@JvmName("notBetweenString")
fun CMJsonField<String>.notBetween(start: TypeExpression<StringType>, end: CMJsonField<String>): NotBetweenExpression<StringType> =
    toDopeType().notBetween(start, end.toDopeType())

@JvmName("notBetweenString")
fun CMJsonField<String>.notBetween(start: TypeExpression<StringType>, end: TypeExpression<StringType>): NotBetweenExpression<StringType> =
    toDopeType().notBetween(start, end)

@JvmName("notBetweenString")
fun TypeExpression<StringType>.notBetween(start: CMJsonField<String>, end: CMJsonField<String>): NotBetweenExpression<StringType> =
    this.notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenString")
fun TypeExpression<StringType>.notBetween(start: CMJsonField<String>, end: TypeExpression<StringType>): NotBetweenExpression<StringType> =
    this.notBetween(start.toDopeType(), end)

@JvmName("notBetweenString")
fun TypeExpression<StringType>.notBetween(start: TypeExpression<StringType>, end: CMJsonField<String>): NotBetweenExpression<StringType> =
    this.notBetween(start, end.toDopeType())

@JvmName("notBetweenString")
fun <Convertable : Any> CMConverterField<Convertable, String>.notBetween(start: Convertable, end: Convertable):
    NotBetweenExpression<StringType> = toDopeType().notBetween(toDopeType(start), toDopeType(end))
