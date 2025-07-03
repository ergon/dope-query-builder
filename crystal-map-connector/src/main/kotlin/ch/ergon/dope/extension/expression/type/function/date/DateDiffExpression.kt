package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnit
import ch.ergon.dope.resolvable.expression.type.function.date.differenceIn
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisDiffCMNumberDateComponent")
fun CMJsonField<Number>.differenceIn(
    other: CMJsonField<Number>,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), dateUnit)

@JvmName("millisDiffNumberDateComponent")
fun CMJsonField<Number>.differenceIn(
    other: Number,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), dateUnit)

fun Number.differenceIn(
    other: CMJsonField<Number>,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), dateUnit)

@JvmName("millisDiffNumberDateComponent")
fun TypeExpression<NumberType>.differenceIn(
    other: CMJsonField<Number>,
    dateUnit: DateUnit,
) = differenceIn(other.toDopeType(), dateUnit)

@JvmName("millisDiffCMNumberDateComponent")
fun CMJsonField<Number>.differenceIn(
    other: TypeExpression<NumberType>,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other, dateUnit)

@JvmName("millisDiffCMNumberDateComponentConverter")
fun <Convertable : Any> CMConverterField<Convertable, Number>.differenceIn(
    other: Convertable,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(toDopeType(other), dateUnit)

@JvmName("strDiffCMStringDateComponent")
fun CMJsonField<String>.differenceIn(
    other: CMJsonField<String>,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), dateUnit)

@JvmName("strDiffTypeCMStringDateComponent")
fun TypeExpression<StringType>.differenceIn(
    other: CMJsonField<String>,
    dateUnit: DateUnit,
) = differenceIn(other.toDopeType(), dateUnit)

@JvmName("strDiffCMStringTypeDateComponent")
fun CMJsonField<String>.differenceIn(
    other: TypeExpression<StringType>,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other, dateUnit)

fun CMJsonField<String>.differenceIn(
    other: String,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), dateUnit)

fun String.differenceIn(
    other: CMJsonField<String>,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), dateUnit)

@JvmName("millisDiffCMStringDateComponentConverter")
fun <Convertable : Any> CMConverterField<Convertable, String>.differenceIn(
    increment: Convertable,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(toDopeType(increment), dateUnit)
