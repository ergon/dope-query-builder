package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.NotWithinExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.notWithinArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("notWithinNumberArray")
fun CMField<out Number>.notWithinArray(array: TypeExpression<ArrayType<NumberType>>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array)

@JvmName("notWithinStringArray")
fun CMField<String>.notWithinArray(array: TypeExpression<ArrayType<StringType>>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array)

@JvmName("notWithinBooleanArray")
fun CMField<Boolean>.notWithinArray(array: TypeExpression<ArrayType<BooleanType>>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array)

@JvmName("notWithinNumberArray")
fun TypeExpression<NumberType>.notWithinArray(array: CMList<out Number>): NotWithinExpression<NumberType> =
    notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun TypeExpression<StringType>.notWithinArray(array: CMList<String>): NotWithinExpression<StringType> =
    notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun TypeExpression<BooleanType>.notWithinArray(array: CMList<Boolean>): NotWithinExpression<BooleanType> =
    notWithinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMField<out Number>.notWithinArray(array: CMList<out Number>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun CMField<String>.notWithinArray(array: CMList<String>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun CMField<Boolean>.notWithinArray(array: CMList<Boolean>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMField<out Number>.notWithinArray(array: Collection<TypeExpression<NumberType>>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun CMField<String>.notWithinArray(array: Collection<TypeExpression<StringType>>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun CMField<Boolean>.notWithinArray(array: Collection<TypeExpression<BooleanType>>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun Number.notWithinArray(array: CMList<out Number>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun String.notWithinArray(array: CMList<String>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun Boolean.notWithinArray(array: CMList<Boolean>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array.toDopeType())
