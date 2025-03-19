package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.clause.IUpdateWhereClause
import ch.ergon.dope.resolvable.clause.model.SetAssignment
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.to
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
infix fun CMJsonField<out Number>.to(value: CMJsonField<out Number>):
    SetAssignment<NumberType> = toDopeType() to value.toDopeType()

@JvmName("setCMStringFieldToCMStringField")
infix fun CMJsonField<String>.to(value: CMJsonField<String>):
    SetAssignment<StringType> = toDopeType() to value.toDopeType()

@JvmName("setCMBooleanFieldToCMBooleanField")
infix fun CMJsonField<Boolean>.to(value: CMJsonField<Boolean>):
    SetAssignment<BooleanType> = toDopeType() to value.toDopeType()

@JvmName("setCMNumberListToCMNumberList")
infix fun CMJsonList<out Number>.to(value: CMJsonList<out Number>):
    SetAssignment<ArrayType<NumberType>> = toDopeType() to value.toDopeType()

@JvmName("setCMStringListToCMStringList")
infix fun CMJsonList<String>.to(value: CMJsonList<String>):
    SetAssignment<ArrayType<StringType>> = toDopeType() to value.toDopeType()

@JvmName("setCMBooleanListToCMBooleanList")
infix fun CMJsonList<Boolean>.to(value: CMJsonList<Boolean>):
    SetAssignment<ArrayType<BooleanType>> = toDopeType() to value.toDopeType()

@JvmName("setCMNumberFieldToTypeExpression")
infix fun CMJsonField<out Number>.to(value: TypeExpression<out NumberType>):
    SetAssignment<NumberType> = toDopeType() to value

@JvmName("setCMStringFieldToTypeExpression")
infix fun CMJsonField<String>.to(value: TypeExpression<out StringType>):
    SetAssignment<StringType> = toDopeType() to value

@JvmName("setCMBooleanFieldToTypeExpression")
infix fun CMJsonField<Boolean>.to(value: TypeExpression<out BooleanType>):
    SetAssignment<BooleanType> = toDopeType() to value

@JvmName("setCMNumberListToTypeExpression")
infix fun CMJsonList<out Number>.to(value: TypeExpression<out ArrayType<NumberType>>):
    SetAssignment<ArrayType<NumberType>> = toDopeType() to value

@JvmName("setCMStringListToTypeExpression")
infix fun CMJsonList<String>.to(value: TypeExpression<out ArrayType<StringType>>):
    SetAssignment<ArrayType<StringType>> = toDopeType() to value

@JvmName("setCMBooleanListToTypeExpression")
infix fun CMJsonList<Boolean>.to(value: TypeExpression<out ArrayType<BooleanType>>):
    SetAssignment<ArrayType<BooleanType>> = toDopeType() to value

@JvmName("setCMNumberFieldToNumber")
infix fun CMJsonField<out Number>.to(value: Number): SetAssignment<NumberType> = toDopeType() to value

@JvmName("setCMStringFieldToString")
infix fun CMJsonField<String>.to(value: String): SetAssignment<StringType> = toDopeType() to value

@JvmName("setCMBooleanFieldToBoolean")
infix fun CMJsonField<Boolean>.to(value: Boolean): SetAssignment<BooleanType> = toDopeType() to value

@JvmName("setCMConverterNumberFieldToNumber")
infix fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.to(value: Convertable) =
    toDopeType() to toDopeType(value)

@JvmName("setCMConverterStringFieldToString")
infix fun <Convertable : Any> CMConverterField<Convertable, String>.to(value: Convertable) =
    toDopeType() to toDopeType(value)

@JvmName("setCMConverterBooleanFieldToBoolean")
infix fun <Convertable : Any> CMConverterField<Convertable, Boolean>.to(value: Convertable) =
    toDopeType() to toDopeType(value)
