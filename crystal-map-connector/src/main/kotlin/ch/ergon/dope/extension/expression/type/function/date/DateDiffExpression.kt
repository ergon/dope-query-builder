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
    component: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), component)

@JvmName("millisDiffNumberDateComponent")
fun CMJsonField<Number>.differenceIn(
    other: Number,
    component: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), component)

fun Number.differenceIn(
    other: CMJsonField<Number>,
    component: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), component)

@JvmName("millisDiffNumberDateComponent")
fun TypeExpression<NumberType>.differenceIn(
    other: CMJsonField<Number>,
    component: DateUnit,
) = differenceIn(other.toDopeType(), component)

@JvmName("millisDiffCMNumberDateComponent")
fun CMJsonField<Number>.differenceIn(
    other: TypeExpression<NumberType>,
    component: DateUnit,
) = toDopeType().differenceIn(other, component)

@JvmName("millisDiffCMNumberDateComponentConverter")
fun <Convertable : Any> CMConverterField<Convertable, Number>.differenceIn(
    other: Convertable,
    component: DateUnit,
) = toDopeType().differenceIn(toDopeType(other), component)

@JvmName("strDiffCMStringDateComponent")
fun CMJsonField<String>.differenceIn(
    other: CMJsonField<String>,
    component: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), component)

@JvmName("strDiffTypeCMStringDateComponent")
fun TypeExpression<StringType>.differenceIn(
    other: CMJsonField<String>,
    component: DateUnit,
) = differenceIn(other.toDopeType(), component)

@JvmName("strDiffCMStringTypeDateComponent")
fun CMJsonField<String>.differenceIn(
    other: TypeExpression<StringType>,
    component: DateUnit,
) = toDopeType().differenceIn(other, component)

fun CMJsonField<String>.differenceIn(
    other: String,
    component: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), component)

fun String.differenceIn(
    other: CMJsonField<String>,
    component: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), component)

@JvmName("millisDiffCMStringDateComponentConverter")
fun <Convertable : Any> CMConverterField<Convertable, String>.differenceIn(
    increment: Convertable,
    component: DateUnit,
) = toDopeType().differenceIn(toDopeType(increment), component)
