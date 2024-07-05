package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotWithinExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.notWithin
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("notWithinNumberArray")
fun CMField<out Number>.notWithin(array: TypeExpression<ArrayType<NumberType>>): NotWithinExpression<NumberType> =
    toDopeType().notWithin(array)

@JvmName("notWithinStringArray")
fun CMField<String>.notWithin(array: TypeExpression<ArrayType<StringType>>): NotWithinExpression<StringType> =
    toDopeType().notWithin(array)

@JvmName("notWithinBooleanArray")
fun CMField<Boolean>.notWithin(array: TypeExpression<ArrayType<BooleanType>>): NotWithinExpression<BooleanType> =
    toDopeType().notWithin(array)

@JvmName("notWithinNumberArray")
fun TypeExpression<NumberType>.notWithin(array: CMList<out Number>): NotWithinExpression<NumberType> =
    this.notWithin(array.toDopeType())

@JvmName("notWithinStringArray")
fun TypeExpression<StringType>.notWithin(array: CMList<String>): NotWithinExpression<StringType> =
    this.notWithin(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun TypeExpression<BooleanType>.notWithin(array: CMList<Boolean>): NotWithinExpression<BooleanType> =
    this.notWithin(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMField<out Number>.notWithin(array: CMList<out Number>): NotWithinExpression<NumberType> =
    toDopeType().notWithin(array.toDopeType())

@JvmName("notWithinStringArray")
fun CMField<String>.notWithin(array: CMList<String>): NotWithinExpression<StringType> =
    toDopeType().notWithin(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun CMField<Boolean>.notWithin(array: CMList<Boolean>): NotWithinExpression<BooleanType> =
    toDopeType().notWithin(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMField<out Number>.notWithin(array: Collection<TypeExpression<NumberType>>): NotWithinExpression<NumberType> =
    toDopeType().notWithin(array.toDopeType())

@JvmName("notWithinStringArray")
fun CMField<String>.notWithin(array: Collection<TypeExpression<StringType>>): NotWithinExpression<StringType> =
    toDopeType().notWithin(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun CMField<Boolean>.notWithin(array: Collection<TypeExpression<BooleanType>>): NotWithinExpression<BooleanType> =
    toDopeType().notWithin(array.toDopeType())

@JvmName("notWithinNumberArray")
fun Number.notWithin(array: CMList<out Number>): NotWithinExpression<NumberType> =
    toDopeType().notWithin(array.toDopeType())

@JvmName("notWithinStringArray")
fun String.notWithin(array: CMList<String>): NotWithinExpression<StringType> =
    toDopeType().notWithin(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun Boolean.notWithin(array: CMList<Boolean>): NotWithinExpression<BooleanType> =
    toDopeType().notWithin(array.toDopeType())
