package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.InExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.inArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("inArrayNumber")
fun CMField<out Number>.inArray(array: TypeExpression<ArrayType<NumberType>>): InExpression<NumberType> =
    toDopeType().inArray(array)

@JvmName("inArrayString")
fun CMField<String>.inArray(array: TypeExpression<ArrayType<StringType>>): InExpression<StringType> =
    toDopeType().inArray(array)

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: TypeExpression<ArrayType<BooleanType>>): InExpression<BooleanType> =
    toDopeType().inArray(array)

@JvmName("inArrayNumber")
fun CMField<out Number>.inArray(array: CMList<out Number>): InExpression<NumberType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayString")
fun CMField<String>.inArray(array: CMList<String>): InExpression<StringType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: CMList<Boolean>): InExpression<BooleanType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayNumber")
fun CMField<out Number>.inArray(array: Collection<TypeExpression<NumberType>>): InExpression<NumberType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayString")
fun CMField<String>.inArray(array: Collection<TypeExpression<StringType>>): InExpression<StringType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: Collection<TypeExpression<BooleanType>>): InExpression<BooleanType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayNumber")
fun Number.inArray(array: CMList<out Number>): InExpression<NumberType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayString")
fun String.inArray(array: CMList<String>): InExpression<StringType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun Boolean.inArray(array: CMList<Boolean>): InExpression<BooleanType> =
    toDopeType().inArray(array.toDopeType())
