package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.clause.IUpdateWhereClause
import ch.ergon.dope.resolvable.clause.model.SetAssignment
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.toNewValue
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMType

fun IUpdateLimitClause.returning(field: CMType, vararg fields: CMType) =
    returning(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun IUpdateLimitClause.returningRaw(field: CMType) =
    returningRaw(field.toDopeType())

fun IUpdateLimitClause.returningValue(field: CMType) =
    returningValue(field.toDopeType())

fun IUpdateLimitClause.returningElement(field: CMType) =
    returningElement(field.toDopeType())

fun IUpdateWhereClause.limit(numberField: CMJsonField<Number>) = limit(numberField.toDopeType())

fun IUpdateUnsetClause.where(whereExpression: CMJsonField<Boolean>) = where(whereExpression.toDopeType())

fun IUpdateSetClause.unset(field: CMType, vararg fields: CMType) =
    UnsetClause(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray(), parentClause = this)

@JvmName("setCMNumberFieldToCMNumberField")
fun CMJsonField<out Number>.toNewValue(value: CMJsonField<out Number>):
    SetAssignment<NumberType> = toDopeType().toNewValue(value.toDopeType())

@JvmName("setCMStringFieldToCMStringField")
fun CMJsonField<String>.toNewValue(value: CMJsonField<String>):
    SetAssignment<StringType> = toDopeType().toNewValue(value.toDopeType())

@JvmName("setCMBooleanFieldToCMBooleanField")
fun CMJsonField<Boolean>.toNewValue(value: CMJsonField<Boolean>):
    SetAssignment<BooleanType> = toDopeType().toNewValue(value.toDopeType())

@JvmName("setCMNumberListToCMNumberList")
fun CMJsonList<out Number>.toNewValue(value: CMJsonList<out Number>):
    SetAssignment<ArrayType<NumberType>> = toDopeType().toNewValue(value.toDopeType())

@JvmName("setCMStringListToCMStringList")
fun CMJsonList<String>.toNewValue(value: CMJsonList<String>):
    SetAssignment<ArrayType<StringType>> = toDopeType().toNewValue(value.toDopeType())

@JvmName("setCMBooleanListToCMBooleanList")
fun CMJsonList<Boolean>.toNewValue(value: CMJsonList<Boolean>):
    SetAssignment<ArrayType<BooleanType>> = toDopeType().toNewValue(value.toDopeType())

@JvmName("setCMNumberFieldToTypeExpression")
fun CMJsonField<out Number>.toNewValue(value: TypeExpression<out NumberType>):
    SetAssignment<NumberType> = toDopeType().toNewValue(value)

@JvmName("setCMStringFieldToTypeExpression")
fun CMJsonField<String>.toNewValue(value: TypeExpression<out StringType>):
    SetAssignment<StringType> = toDopeType().toNewValue(value)

@JvmName("setCMBooleanFieldToTypeExpression")
fun CMJsonField<Boolean>.toNewValue(value: TypeExpression<out BooleanType>):
    SetAssignment<BooleanType> = toDopeType().toNewValue(value)

@JvmName("setCMNumberListToTypeExpression")
fun CMJsonList<out Number>.toNewValue(value: TypeExpression<out ArrayType<NumberType>>):
    SetAssignment<ArrayType<NumberType>> = toDopeType().toNewValue(value)

@JvmName("setCMStringListToTypeExpression")
fun CMJsonList<String>.toNewValue(value: TypeExpression<out ArrayType<StringType>>):
    SetAssignment<ArrayType<StringType>> = toDopeType().toNewValue(value)

@JvmName("setCMBooleanListToTypeExpression")
fun CMJsonList<Boolean>.toNewValue(value: TypeExpression<out ArrayType<BooleanType>>):
    SetAssignment<ArrayType<BooleanType>> = toDopeType().toNewValue(value)

@JvmName("setCMNumberFieldToNumber")
fun CMJsonField<out Number>.toNewValue(value: Number): SetAssignment<NumberType> = toDopeType().toNewValue(value)

@JvmName("setCMStringFieldToString")
fun CMJsonField<String>.toNewValue(value: String): SetAssignment<StringType> = toDopeType().toNewValue(value)

@JvmName("setCMBooleanFieldToBoolean")
fun CMJsonField<Boolean>.toNewValue(value: Boolean): SetAssignment<BooleanType> = toDopeType().toNewValue(value)

@JvmName("setCMConverterNumberFieldToNumber")
fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.toNewValue(value: Convertable) =
    toDopeType().toNewValue(toDopeType(value))

@JvmName("setCMConverterStringFieldToString")
fun <Convertable : Any> CMConverterField<Convertable, String>.toNewValue(value: Convertable) =
    toDopeType().toNewValue(toDopeType(value))

@JvmName("setCMConverterBooleanFieldToBoolean")
fun <Convertable : Any> CMConverterField<Convertable, Boolean>.toNewValue(value: Convertable) =
    toDopeType().toNewValue(toDopeType(value))
