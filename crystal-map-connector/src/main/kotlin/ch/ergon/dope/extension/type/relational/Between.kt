package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotBetweenExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.between
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.notBetween
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
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
    between(start.toDopeType(), end.toDopeType())

@JvmName("betweenNumber")
fun TypeExpression<NumberType>.between(start: CMField<out Number>, end: TypeExpression<NumberType>): BetweenExpression<NumberType> =
    between(start.toDopeType(), end)

@JvmName("betweenNumber")
fun TypeExpression<NumberType>.between(start: TypeExpression<NumberType>, end: CMField<out Number>): BetweenExpression<NumberType> =
    between(start, end.toDopeType())

@JvmName("betweenNumber")
fun <KotlinType : Any, MapType : Number> CMConverterField<KotlinType, MapType>.between(
    start: KotlinType,
    end: KotlinType,
): BetweenExpression<NumberType> =
    toDopeType().between(toDopeType(start), toDopeType(end))

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
    between(start.toDopeType(), end.toDopeType())

@JvmName("betweenString")
fun TypeExpression<StringType>.between(start: CMField<String>, end: TypeExpression<StringType>): BetweenExpression<StringType> =
    between(start.toDopeType(), end)

@JvmName("betweenString")
fun TypeExpression<StringType>.between(start: TypeExpression<StringType>, end: CMField<String>): BetweenExpression<StringType> =
    between(start, end.toDopeType())

@JvmName("betweenString")
fun <KotlinType : Any> CMConverterField<KotlinType, String>.between(start: KotlinType, end: KotlinType): BetweenExpression<StringType> =
    toDopeType().between(toDopeType(start), toDopeType(end))

@JvmName("notBetweenNumber")
fun CMField<out Number>.notBetween(start: CMField<out Number>, end: CMField<out Number>): NotBetweenExpression<NumberType> =
    toDopeType().notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenNumber")
fun CMField<out Number>.notBetween(start: CMField<out Number>, end: TypeExpression<NumberType>): NotBetweenExpression<NumberType> =
    toDopeType().notBetween(start.toDopeType(), end)

@JvmName("notBetweenNumber")
fun CMField<out Number>.notBetween(start: TypeExpression<NumberType>, end: CMField<out Number>): NotBetweenExpression<NumberType> =
    toDopeType().notBetween(start, end.toDopeType())

@JvmName("notBetweenNumber")
fun CMField<out Number>.notBetween(start: TypeExpression<NumberType>, end: TypeExpression<NumberType>): NotBetweenExpression<NumberType> =
    toDopeType().notBetween(start, end)

@JvmName("notBetweenNumber")
fun TypeExpression<NumberType>.notBetween(start: CMField<out Number>, end: CMField<out Number>): NotBetweenExpression<NumberType> =
    this.notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenNumber")
fun TypeExpression<NumberType>.notBetween(start: CMField<out Number>, end: TypeExpression<NumberType>): NotBetweenExpression<NumberType> =
    this.notBetween(start.toDopeType(), end)

@JvmName("notBetweenNumber")
fun TypeExpression<NumberType>.notBetween(start: TypeExpression<NumberType>, end: CMField<out Number>): NotBetweenExpression<NumberType> =
    this.notBetween(start, end.toDopeType())

@JvmName("notBetweenString")
fun CMField<String>.notBetween(start: CMField<String>, end: CMField<String>): NotBetweenExpression<StringType> =
    toDopeType().notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenString")
fun CMField<String>.notBetween(start: CMField<String>, end: TypeExpression<StringType>): NotBetweenExpression<StringType> =
    toDopeType().notBetween(start.toDopeType(), end)

@JvmName("notBetweenString")
fun CMField<String>.notBetween(start: TypeExpression<StringType>, end: CMField<String>): NotBetweenExpression<StringType> =
    toDopeType().notBetween(start, end.toDopeType())

@JvmName("notBetweenString")
fun CMField<String>.notBetween(start: TypeExpression<StringType>, end: TypeExpression<StringType>): NotBetweenExpression<StringType> =
    toDopeType().notBetween(start, end)

@JvmName("notBetweenString")
fun TypeExpression<StringType>.notBetween(start: CMField<String>, end: CMField<String>): NotBetweenExpression<StringType> =
    this.notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenString")
fun TypeExpression<StringType>.notBetween(start: CMField<String>, end: TypeExpression<StringType>): NotBetweenExpression<StringType> =
    this.notBetween(start.toDopeType(), end)

@JvmName("notBetweenString")
fun TypeExpression<StringType>.notBetween(start: TypeExpression<StringType>, end: CMField<String>): NotBetweenExpression<StringType> =
    this.notBetween(start, end.toDopeType())
