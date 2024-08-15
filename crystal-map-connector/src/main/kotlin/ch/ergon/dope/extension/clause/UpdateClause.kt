package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.clause.IUpdateUseKeysClause
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
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMType

fun IUpdateLimitClause.returning(field: CMType, vararg fields: CMType) =
    returning(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun IUpdateWhereClause.limit(numberField: CMJsonField<Number>) = limit(numberField.toDopeType())

fun IUpdateUnsetClause.where(whereExpression: CMJsonField<Boolean>) = where(whereExpression.toDopeType())

fun IUpdateUnsetClause.unset(field: CMType) =
    UnsetClause(field.toDopeType(), parentClause = this)

fun UnsetClause.unset(field: CMType) =
    this.unset(field.toDopeType())

@JvmName("setCMNumberFieldToCMNumberField")
fun IUpdateSetClause.set(
    field: CMJsonField<out Number>,
    value: CMJsonField<out Number>,
) = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = this)

@JvmName("setCMNumberFieldToCMNumberField")
fun SetClause.set(
    field: CMJsonField<out Number>,
    value: CMJsonField<out Number>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMStringFieldToCMStringField")
fun IUpdateUseKeysClause.set(
    field: CMJsonField<String>,
    value: CMJsonField<String>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMStringFieldToCMStringField")
fun SetClause.set(
    field: CMJsonField<String>,
    value: CMJsonField<String>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMBooleanFieldToCMBooleanField")
fun IUpdateUseKeysClause.set(
    field: CMJsonField<Boolean>,
    value: CMJsonField<Boolean>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMBooleanFieldToCMBooleanField")
fun SetClause.set(
    field: CMJsonField<Boolean>,
    value: CMJsonField<Boolean>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMNumberListToCMNumberList")
fun IUpdateUseKeysClause.set(
    field: CMJsonList<out Number>,
    value: CMJsonList<out Number>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMNumberListToCMNumberList")
fun SetClause.set(
    field: CMJsonList<out Number>,
    value: CMJsonList<out Number>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMStringListToCMStringList")
fun IUpdateUseKeysClause.set(
    field: CMJsonList<String>,
    value: CMJsonList<String>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMStringListToCMStringList")
fun SetClause.set(
    field: CMJsonList<String>,
    value: CMJsonList<String>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMBooleanListToCMBooleanList")
fun IUpdateUseKeysClause.set(
    field: CMJsonList<Boolean>,
    value: CMJsonList<Boolean>,
) = set(field.toDopeType(), value.toDopeType())

@JvmName("setCMBooleanListToCMBooleanList")
fun SetClause.set(
    field: CMJsonList<Boolean>,
    value: CMJsonList<Boolean>,
) = this.set(field.toDopeType(), value.toDopeType())

@JvmName("setCMNumberFieldToNumberTypeExpression")
fun IUpdateUseKeysClause.set(
    field: CMJsonField<out Number>,
    value: TypeExpression<NumberType>,
) = set(field.toDopeType(), value)

@JvmName("setCMNumberFieldToNumberTypeExpression")
fun SetClause.set(
    field: CMJsonField<out Number>,
    value: TypeExpression<NumberType>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMStringFieldToStringTypeExpression")
fun IUpdateUseKeysClause.set(
    field: CMJsonField<String>,
    value: TypeExpression<StringType>,
) = set(field.toDopeType(), value)

@JvmName("setCMStringFieldToStringTypeExpression")
fun SetClause.set(
    field: CMJsonField<String>,
    value: TypeExpression<StringType>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMBooleanFieldToBooleanTypeExpression")
fun IUpdateUseKeysClause.set(
    field: CMJsonField<Boolean>,
    value: TypeExpression<BooleanType>,
) = set(field.toDopeType(), value)

@JvmName("setCMBooleanFieldToBooleanTypeExpression")
fun SetClause.set(
    field: CMJsonField<Boolean>,
    value: TypeExpression<BooleanType>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMNumberListToNumberArrayTypeExpression")
fun IUpdateUseKeysClause.set(
    field: CMJsonList<out Number>,
    value: TypeExpression<ArrayType<NumberType>>,
) = set(field.toDopeType(), value)

@JvmName("setCMNumberListToNumberArrayTypeExpression")
fun SetClause.set(
    field: CMJsonList<out Number>,
    value: TypeExpression<ArrayType<NumberType>>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMStringListToStringArrayTypeExpression")
fun IUpdateUseKeysClause.set(
    field: CMJsonList<String>,
    value: TypeExpression<ArrayType<StringType>>,
) = set(field.toDopeType(), value)

@JvmName("setCMStringListToStringArrayTypeExpression")
fun SetClause.set(
    field: CMJsonList<String>,
    value: TypeExpression<ArrayType<StringType>>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMBooleanListToBooleanArrayTypeExpression")
fun IUpdateUseKeysClause.set(
    field: CMJsonList<Boolean>,
    value: TypeExpression<ArrayType<BooleanType>>,
) = set(field.toDopeType(), value)

@JvmName("setCMBooleanListToBooleanArrayTypeExpression")
fun SetClause.set(
    field: CMJsonList<Boolean>,
    value: TypeExpression<ArrayType<BooleanType>>,
) = this.set(field.toDopeType(), value)

@JvmName("setCMNumberFieldToNumber")
fun IUpdateUseKeysClause.set(
    field: CMJsonField<out Number>,
    value: Number,
) = set(field.toDopeType(), value)

@JvmName("setCMNumberFieldToNumber")
fun SetClause.set(
    field: CMJsonField<out Number>,
    value: Number,
) = this.set(field.toDopeType(), value)

@JvmName("setCMStringFieldToString")
fun IUpdateUseKeysClause.set(
    field: CMJsonField<String>,
    value: String,
) = set(field.toDopeType(), value)

@JvmName("setCMStringFieldToString")
fun SetClause.set(
    field: CMJsonField<String>,
    value: String,
) = this.set(field.toDopeType(), value)

@JvmName("setCMBooleanFieldToBoolean")
fun IUpdateUseKeysClause.set(
    field: CMJsonField<Boolean>,
    value: Boolean,
) = set(field.toDopeType(), value)

@JvmName("setCMBooleanFieldToBoolean")
fun SetClause.set(
    field: CMJsonField<Boolean>,
    value: Boolean,
) = this.set(field.toDopeType(), value)

fun IUpdateClause.useKeys(useKeys: CMJsonField<String>) = useKeys(useKeys.toDopeType())
fun IUpdateClause.useKeys(useKeys: CMJsonList<String>) = useKeys(useKeys.toDopeType())
