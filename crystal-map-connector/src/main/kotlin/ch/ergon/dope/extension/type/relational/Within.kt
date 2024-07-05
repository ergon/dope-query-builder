package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.WithinExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.within
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("withinNumberArray")
fun CMField<out Number>.within(array: TypeExpression<ArrayType<NumberType>>): WithinExpression<NumberType> =
    toDopeType().within(array)

@JvmName("withinStringArray")
fun CMField<String>.within(array: TypeExpression<ArrayType<StringType>>): WithinExpression<StringType> =
    toDopeType().within(array)

@JvmName("withinBooleanArray")
fun CMField<Boolean>.within(array: TypeExpression<ArrayType<BooleanType>>): WithinExpression<BooleanType> =
    toDopeType().within(array)

@JvmName("withinNumberArray")
fun TypeExpression<NumberType>.within(array: CMList<out Number>): WithinExpression<NumberType> =
    this.within(array.toDopeType())

@JvmName("withinStringArray")
fun TypeExpression<StringType>.within(array: CMList<String>): WithinExpression<StringType> =
    this.within(array.toDopeType())

@JvmName("withinBooleanArray")
fun TypeExpression<BooleanType>.within(array: CMList<Boolean>): WithinExpression<BooleanType> =
    this.within(array.toDopeType())

@JvmName("withinNumberArray")
fun CMField<out Number>.within(array: CMList<out Number>): WithinExpression<NumberType> =
    toDopeType().within(array.toDopeType())

@JvmName("withinStringArray")
fun CMField<String>.within(array: CMList<String>): WithinExpression<StringType> =
    toDopeType().within(array.toDopeType())

@JvmName("withinBooleanArray")
fun CMField<Boolean>.within(array: CMList<Boolean>): WithinExpression<BooleanType> =
    toDopeType().within(array.toDopeType())

@JvmName("withinNumberArray")
fun CMField<out Number>.within(array: Collection<TypeExpression<NumberType>>): WithinExpression<NumberType> =
    toDopeType().within(array.toDopeType())

@JvmName("withinStringArray")
fun CMField<String>.within(array: Collection<TypeExpression<StringType>>): WithinExpression<StringType> =
    toDopeType().within(array.toDopeType())

@JvmName("withinBooleanArray")
fun CMField<Boolean>.within(array: Collection<TypeExpression<BooleanType>>): WithinExpression<BooleanType> =
    toDopeType().within(array.toDopeType())

@JvmName("withinNumberArray")
fun Number.within(array: CMList<out Number>): WithinExpression<NumberType> =
    toDopeType().within(array.toDopeType())

@JvmName("withinStringArray")
fun String.within(array: CMList<String>): WithinExpression<StringType> =
    toDopeType().within(array.toDopeType())

@JvmName("withinBooleanArray")
fun Boolean.within(array: CMList<Boolean>): WithinExpression<BooleanType> =
    toDopeType().within(array.toDopeType())
