package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.nvl2
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMType

@JvmName("nvl2CMNumberFieldAndCMNumberField")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonField<out Number>,
    valueIfNotExists: CMJsonField<out Number>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMStringFieldAndCMStringField")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonField<String>,
    valueIfNotExists: CMJsonField<String>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMBooleanFieldAndCMBooleanField")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonField<Boolean>,
    valueIfNotExists: CMJsonField<Boolean>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMNumberListAndCMNumberList")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonList<out Number>,
    valueIfNotExists: CMJsonList<out Number>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMStringListAndCMStringList")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonList<String>,
    valueIfNotExists: CMJsonList<String>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMBooleanListAndCMBooleanList")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonList<Boolean>,
    valueIfNotExists: CMJsonList<Boolean>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMNumberFieldAndNumberType")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonField<out Number>,
    valueIfNotExists: UnaliasedExpression<NumberType>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMStringFieldAndStringType")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonField<String>,
    valueIfNotExists: UnaliasedExpression<StringType>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMBooleanFieldAndBooleanType")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonField<Boolean>,
    valueIfNotExists: UnaliasedExpression<BooleanType>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMNumberListAndArrayNumberType")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonList<out Number>,
    valueIfNotExists: UnaliasedExpression<ArrayType<NumberType>>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMStringListAndArrayStringType")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonList<String>,
    valueIfNotExists: UnaliasedExpression<ArrayType<StringType>>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMBooleanListAndArrayBooleanType")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonList<Boolean>,
    valueIfNotExists: UnaliasedExpression<ArrayType<BooleanType>>,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMNumberFieldAndNumber")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonField<out Number>,
    valueIfNotExists: Number,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMStringFieldAndString")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonField<String>,
    valueIfNotExists: String,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMBooleanFieldAndBoolean")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: CMJsonField<Boolean>,
    valueIfNotExists: Boolean,
) = nvl2(initialExpression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2NumberTypeAndCMNumberField")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: UnaliasedExpression<NumberType>,
    valueIfNotExists: CMJsonField<out Number>,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2StringTypeAndCMStringField")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: UnaliasedExpression<StringType>,
    valueIfNotExists: CMJsonField<String>,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2BooleanTypeAndCMBooleanField")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: UnaliasedExpression<BooleanType>,
    valueIfNotExists: CMJsonField<Boolean>,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2ArrayNumberTypeAndCMNumberList")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: UnaliasedExpression<ArrayType<NumberType>>,
    valueIfNotExists: CMJsonList<out Number>,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2ArrayStringTypeAndCMStringList")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: UnaliasedExpression<ArrayType<StringType>>,
    valueIfNotExists: CMJsonList<String>,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2ArrayBooleanTypeAndCMBooleanList")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: UnaliasedExpression<ArrayType<BooleanType>>,
    valueIfNotExists: CMJsonList<Boolean>,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2NumberAndCMNumberField")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: Number,
    valueIfNotExists: CMJsonField<out Number>,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2StringAndCMStringField")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: String,
    valueIfNotExists: CMJsonField<String>,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2BooleanAndCMBooleanField")
fun nvl2(
    initialExpression: CMType,
    valueIfExists: Boolean,
    valueIfNotExists: CMJsonField<Boolean>,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

fun nvl2(
    initialExpression: CMType,
    valueIfExists: Number,
    valueIfNotExists: Number,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists)

fun nvl2(
    initialExpression: CMType,
    valueIfExists: String,
    valueIfNotExists: String,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists)

fun nvl2(
    initialExpression: CMType,
    valueIfExists: Boolean,
    valueIfNotExists: Boolean,
) = nvl2(initialExpression.toDopeType(), valueIfExists, valueIfNotExists)

@JvmName("nvl2CMNumberFieldAndCMNumberField")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonField<out Number>,
    valueIfNotExists: CMJsonField<out Number>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMStringFieldAndCMStringField")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonField<String>,
    valueIfNotExists: CMJsonField<String>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMBooleanFieldAndCMBooleanField")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonField<Boolean>,
    valueIfNotExists: CMJsonField<Boolean>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMNumberListAndCMNumberList")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonList<out Number>,
    valueIfNotExists: CMJsonList<out Number>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMStringListAndCMStringList")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonList<String>,
    valueIfNotExists: CMJsonList<String>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMBooleanListAndCMBooleanList")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonList<Boolean>,
    valueIfNotExists: CMJsonList<Boolean>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

@JvmName("nvl2CMNumberFieldAndNumberType")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonField<out Number>,
    valueIfNotExists: UnaliasedExpression<NumberType>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMStringFieldAndStringType")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonField<String>,
    valueIfNotExists: UnaliasedExpression<StringType>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMBooleanFieldAndBooleanType")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonField<Boolean>,
    valueIfNotExists: UnaliasedExpression<BooleanType>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMNumberListAndArrayNumberType")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonList<out Number>,
    valueIfNotExists: UnaliasedExpression<ArrayType<NumberType>>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMStringListAndArrayStringType")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonList<String>,
    valueIfNotExists: UnaliasedExpression<ArrayType<StringType>>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMBooleanListAndArrayBooleanType")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonList<Boolean>,
    valueIfNotExists: UnaliasedExpression<ArrayType<BooleanType>>,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMNumberFieldAndNumber")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonField<out Number>,
    valueIfNotExists: Number,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMStringFieldAndString")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonField<String>,
    valueIfNotExists: String,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2CMBooleanFieldAndBoolean")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: CMJsonField<Boolean>,
    valueIfNotExists: Boolean,
) = nvl2(initialExpression, valueIfExists.toDopeType(), valueIfNotExists)

@JvmName("nvl2NumberTypeAndCMNumberField")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<NumberType>,
    valueIfNotExists: CMJsonField<out Number>,
) = nvl2(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2StringTypeAndCMStringField")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<StringType>,
    valueIfNotExists: CMJsonField<String>,
) = nvl2(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2BooleanTypeAndCMBooleanField")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<BooleanType>,
    valueIfNotExists: CMJsonField<Boolean>,
) = nvl2(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2ArrayNumberTypeAndCMNumberList")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<ArrayType<NumberType>>,
    valueIfNotExists: CMJsonList<out Number>,
) = nvl2(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2ArrayStringTypeAndCMStringList")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<ArrayType<StringType>>,
    valueIfNotExists: CMJsonList<String>,
) = nvl2(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2ArrayBooleanTypeAndCMBooleanList")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: UnaliasedExpression<ArrayType<BooleanType>>,
    valueIfNotExists: CMJsonList<Boolean>,
) = nvl2(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2NumberAndCMNumberField")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: Number,
    valueIfNotExists: CMJsonField<out Number>,
) = nvl2(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2StringAndCMStringField")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: String,
    valueIfNotExists: CMJsonField<String>,
) = nvl2(initialExpression, valueIfExists, valueIfNotExists.toDopeType())

@JvmName("nvl2BooleanAndCMBooleanField")
fun nvl2(
    initialExpression: UnaliasedExpression<out ValidType>,
    valueIfExists: Boolean,
    valueIfNotExists: CMJsonField<Boolean>,
) = nvl2(initialExpression, valueIfExists, valueIfNotExists.toDopeType())
