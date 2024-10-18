package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.NotWithinExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.WithinExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.notWithinArray
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.withinArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("withinNumberArray")
fun CMJsonField<out Number>.withinArray(array: TypeExpression<ArrayType<NumberType>>): WithinExpression<NumberType> =
    toDopeType().withinArray(array)

@JvmName("withinStringArray")
fun CMJsonField<String>.withinArray(array: TypeExpression<ArrayType<StringType>>): WithinExpression<StringType> =
    toDopeType().withinArray(array)

@JvmName("withinBooleanArray")
fun CMJsonField<Boolean>.withinArray(array: TypeExpression<ArrayType<BooleanType>>): WithinExpression<BooleanType> =
    toDopeType().withinArray(array)

@JvmName("withinNumberArray")
fun CMJsonField<out Number>.withinArray(selectClause: ISelectOffsetClause<NumberType>): WithinExpression<NumberType> =
    toDopeType().withinArray(selectClause.asExpression())

@JvmName("withinStringArray")
fun CMJsonField<String>.withinArray(selectClause: ISelectOffsetClause<StringType>): WithinExpression<StringType> =
    toDopeType().withinArray(selectClause.asExpression())

@JvmName("withinBooleanArray")
fun CMJsonField<Boolean>.withinArray(selectClause: ISelectOffsetClause<BooleanType>): WithinExpression<BooleanType> =
    toDopeType().withinArray(selectClause.asExpression())

@JvmName("withinNumberArray")
fun TypeExpression<NumberType>.withinArray(array: CMJsonList<out Number>): WithinExpression<NumberType> =
    withinArray(array.toDopeType())

@JvmName("withinStringArray")
fun TypeExpression<StringType>.withinArray(array: CMJsonList<String>): WithinExpression<StringType> =
    withinArray(array.toDopeType())

@JvmName("withinBooleanArray")
fun TypeExpression<BooleanType>.withinArray(array: CMJsonList<Boolean>): WithinExpression<BooleanType> =
    withinArray(array.toDopeType())

@JvmName("withinNumberArray")
fun CMJsonField<out Number>.withinArray(array: CMJsonList<out Number>): WithinExpression<NumberType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinStringArray")
fun CMJsonField<String>.withinArray(array: CMJsonList<String>): WithinExpression<StringType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinBooleanArray")
fun CMJsonField<Boolean>.withinArray(array: CMJsonList<Boolean>): WithinExpression<BooleanType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinNumberArray")
fun CMJsonField<out Number>.withinArray(array: Collection<TypeExpression<NumberType>>): WithinExpression<NumberType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinStringArray")
fun CMJsonField<String>.withinArray(array: Collection<TypeExpression<StringType>>): WithinExpression<StringType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinBooleanArray")
fun CMJsonField<Boolean>.withinArray(array: Collection<TypeExpression<BooleanType>>): WithinExpression<BooleanType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinNumberArray")
fun Number.withinArray(array: CMJsonList<out Number>): WithinExpression<NumberType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinStringArray")
fun String.withinArray(array: CMJsonList<String>): WithinExpression<StringType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinBooleanArray")
fun Boolean.withinArray(array: CMJsonList<Boolean>): WithinExpression<BooleanType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinArrayNumberConverter")
fun <Convertable : Any, JsonType : Number> Convertable.withinArray(array: CMConverterList<Convertable, JsonType>): WithinExpression<NumberType> =
    toDopeType(array).withinArray(array.toDopeType())

@JvmName("withinArrayStringConverter")
fun <Convertable : Any> Convertable.withinArray(array: CMConverterList<Convertable, String>): WithinExpression<StringType> =
    toDopeType(array).withinArray(array.toDopeType())

@JvmName("withinArrayBooleanConverter")
fun <Convertable : Any> Convertable.withinArray(array: CMConverterList<Convertable, Boolean>): WithinExpression<BooleanType> =
    toDopeType(array).withinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMJsonField<out Number>.notWithinArray(array: TypeExpression<ArrayType<NumberType>>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array)

@JvmName("notWithinStringArray")
fun CMJsonField<String>.notWithinArray(array: TypeExpression<ArrayType<StringType>>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array)

@JvmName("notWithinBooleanArray")
fun CMJsonField<Boolean>.notWithinArray(array: TypeExpression<ArrayType<BooleanType>>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array)

@JvmName("notWithinNumberArray")
fun CMJsonField<out Number>.notWithinArray(selectClause: ISelectOffsetClause<NumberType>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(selectClause.asExpression())

@JvmName("notWithinStringArray")
fun CMJsonField<String>.notWithinArray(selectClause: ISelectOffsetClause<StringType>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(selectClause.asExpression())

@JvmName("notWithinBooleanArray")
fun CMJsonField<Boolean>.notWithinArray(selectClause: ISelectOffsetClause<BooleanType>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(selectClause.asExpression())

@JvmName("notWithinNumberArray")
fun TypeExpression<NumberType>.notWithinArray(array: CMJsonList<out Number>): NotWithinExpression<NumberType> =
    this.notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun TypeExpression<StringType>.notWithinArray(array: CMJsonList<String>): NotWithinExpression<StringType> =
    this.notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun TypeExpression<BooleanType>.notWithinArray(array: CMJsonList<Boolean>): NotWithinExpression<BooleanType> =
    this.notWithinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMJsonField<out Number>.notWithinArray(array: CMJsonList<out Number>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun CMJsonField<String>.notWithinArray(array: CMJsonList<String>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun CMJsonField<Boolean>.notWithinArray(array: CMJsonList<Boolean>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMJsonField<out Number>.notWithinArray(array: Collection<TypeExpression<NumberType>>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun CMJsonField<String>.notWithinArray(array: Collection<TypeExpression<StringType>>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun CMJsonField<Boolean>.notWithinArray(array: Collection<TypeExpression<BooleanType>>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun Number.notWithinArray(array: CMJsonList<out Number>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun String.notWithinArray(array: CMJsonList<String>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun Boolean.notWithinArray(array: CMJsonList<Boolean>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array.toDopeType())
