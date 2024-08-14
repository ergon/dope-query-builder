package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.clause.IUpdateWhereClause
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.to
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMType

fun IUpdateLimitClause.returning(field: CMType, vararg fields: CMType) =
    returning(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun IUpdateWhereClause.limit(numberField: CMField<Number>) = limit(numberField.toDopeType())

fun IUpdateUnsetClause.where(whereExpression: CMField<Boolean>) = where(whereExpression.toDopeType())

fun IUpdateUnsetClause.unset(field: CMType) =
    UnsetClause(field.toDopeType(), parentClause = this)

fun UnsetClause.unset(field: CMType) =
    this.unset(field.toDopeType())

@JvmName("setCMNumberFieldToCMNumberField")
fun IUpdateSetClause.set(
    field: CMField<out Number>,
    value: CMField<out Number>,
) = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = this)

@JvmName("setCMNumberFieldToCMNumberField")
fun SetClause.set(
    field: CMField<out Number>,
    value: CMField<out Number>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMStringFieldToCMStringField")
fun IUpdateClause.set(
    field: CMField<String>,
    value: CMField<String>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMStringFieldToCMStringField")
fun SetClause.set(
    field: CMField<String>,
    value: CMField<String>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMBooleanFieldToCMBooleanField")
fun IUpdateClause.set(
    field: CMField<Boolean>,
    value: CMField<Boolean>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMBooleanFieldToCMBooleanField")
fun SetClause.set(
    field: CMField<Boolean>,
    value: CMField<Boolean>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMNumberListToCMNumberList")
fun IUpdateClause.set(
    field: CMList<out Number>,
    value: CMList<out Number>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMNumberListToCMNumberList")
fun SetClause.set(
    field: CMList<out Number>,
    value: CMList<out Number>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMStringListToCMStringList")
fun IUpdateClause.set(
    field: CMList<String>,
    value: CMList<String>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMStringListToCMStringList")
fun SetClause.set(
    field: CMList<String>,
    value: CMList<String>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMBooleanListToCMBooleanList")
fun IUpdateClause.set(
    field: CMList<Boolean>,
    value: CMList<Boolean>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMBooleanListToCMBooleanList")
fun SetClause.set(
    field: CMList<Boolean>,
    value: CMList<Boolean>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMNumberFieldToNumberTypeExpression")
fun IUpdateClause.set(
    field: CMField<out Number>,
    value: TypeExpression<NumberType>,
) = set(field.toDopeType(), value)

@JvmName("setCMNumberFieldToNumberTypeExpression")
fun SetClause.set(
    field: CMField<out Number>,
    value: TypeExpression<NumberType>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMStringFieldToStringTypeExpression")
fun IUpdateClause.set(
    field: CMField<String>,
    value: TypeExpression<StringType>,
) = set(field.toDopeType(), value)

@JvmName("setCMStringFieldToStringTypeExpression")
fun SetClause.set(
    field: CMField<String>,
    value: TypeExpression<StringType>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMBooleanFieldToBooleanTypeExpression")
fun IUpdateClause.set(
    field: CMField<Boolean>,
    value: TypeExpression<BooleanType>,
) = set(field.toDopeType(), value)

@JvmName("setCMBooleanFieldToBooleanTypeExpression")
fun SetClause.set(
    field: CMField<Boolean>,
    value: TypeExpression<BooleanType>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMNumberListToNumberArrayTypeExpression")
fun IUpdateClause.set(
    field: CMList<out Number>,
    value: TypeExpression<ArrayType<NumberType>>,
) = set(field.toDopeType(), value)

@JvmName("setCMNumberListToNumberArrayTypeExpression")
fun SetClause.set(
    field: CMList<out Number>,
    value: TypeExpression<ArrayType<NumberType>>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMStringListToStringArrayTypeExpression")
fun IUpdateClause.set(
    field: CMList<String>,
    value: TypeExpression<ArrayType<StringType>>,
) = set(field.toDopeType(), value)

@JvmName("setCMStringListToStringArrayTypeExpression")
fun SetClause.set(
    field: CMList<String>,
    value: TypeExpression<ArrayType<StringType>>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMBooleanListToBooleanArrayTypeExpression")
fun IUpdateClause.set(
    field: CMList<Boolean>,
    value: TypeExpression<ArrayType<BooleanType>>,
) = set(field.toDopeType(), value)

@JvmName("setCMBooleanListToBooleanArrayTypeExpression")
fun SetClause.set(
    field: CMList<Boolean>,
    value: TypeExpression<ArrayType<BooleanType>>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMNumberFieldToNumber")
fun IUpdateClause.set(
    field: CMField<out Number>,
    value: Number,
) = set(field.toDopeType(), value)

@JvmName("setCMNumberFieldToNumber")
fun SetClause.set(
    field: CMField<out Number>,
    value: Number,
) = this.set(field.toDopeType(), value)

@JvmName("setCMStringFieldToString")
fun IUpdateClause.set(
    field: CMField<String>,
    value: String,
) = set(field.toDopeType(), value)

@JvmName("setCMStringFieldToString")
fun SetClause.set(
    field: CMField<String>,
    value: String,
) = this.set(field.toDopeType(), value)

@JvmName("setCMBooleanFieldToBoolean")
fun IUpdateClause.set(
    field: CMField<Boolean>,
    value: Boolean,
) = set(field.toDopeType(), value)

@JvmName("setCMBooleanFieldToBoolean")
fun SetClause.set(
    field: CMField<Boolean>,
    value: Boolean,
) = this.set(field.toDopeType(), value)
