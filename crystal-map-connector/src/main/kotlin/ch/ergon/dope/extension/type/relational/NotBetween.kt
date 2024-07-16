package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotBetweenExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.notBetween
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMField

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
    notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenNumber")
fun TypeExpression<NumberType>.notBetween(start: CMField<out Number>, end: TypeExpression<NumberType>): NotBetweenExpression<NumberType> =
    notBetween(start.toDopeType(), end)

@JvmName("notBetweenNumber")
fun TypeExpression<NumberType>.notBetween(start: TypeExpression<NumberType>, end: CMField<out Number>): NotBetweenExpression<NumberType> =
    notBetween(start, end.toDopeType())

@JvmName("notBetweenNumber")
fun <KotlinType, MapType : Number> CMConverterField<KotlinType, MapType>.notBetween(start: KotlinType, end: KotlinType):
    NotBetweenExpression<NumberType> =
    toDopeType().notBetween(typeConverter.write(start)!!.toDopeType(), typeConverter.write(end)!!.toDopeType())

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
    notBetween(start.toDopeType(), end.toDopeType())

@JvmName("notBetweenString")
fun TypeExpression<StringType>.notBetween(start: CMField<String>, end: TypeExpression<StringType>): NotBetweenExpression<StringType> =
    notBetween(start.toDopeType(), end)

@JvmName("notBetweenString")
fun TypeExpression<StringType>.notBetween(start: TypeExpression<StringType>, end: CMField<String>): NotBetweenExpression<StringType> =
    notBetween(start, end.toDopeType())

@JvmName("notBetweenString")
fun <KotlinType> CMConverterField<KotlinType, String>.notBetween(start: KotlinType, end: KotlinType): NotBetweenExpression<StringType> =
    toDopeType().notBetween(typeConverter.write(start)!!.toDopeType(), typeConverter.write(end)!!.toDopeType())
