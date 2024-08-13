package ch.ergon.dope.extension.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.div
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<out Number>.add(numberExpression: TypeExpression<NumberType>) = toDopeType().add(numberExpression)

fun CMJsonField<out Number>.add(number: CMJsonField<out Number>) = toDopeType().add(number.toDopeType())

fun TypeExpression<NumberType>.add(number: CMJsonField<out Number>) = add(number.toDopeType())

fun Number.add(number: CMJsonField<out Number>) = toDopeType().add(number.toDopeType())

fun CMJsonField<out Number>.add(number: Number) = toDopeType().add(number.toDopeType())

fun <Convertable : Any, JsonType : Number> Convertable.add(other: CMConverterField<Convertable, JsonType>) =
    toDopeType(other).add(other.toDopeType())

fun CMJsonField<out Number>.sub(numberExpression: TypeExpression<NumberType>) = toDopeType().sub(numberExpression)

fun CMJsonField<out Number>.sub(number: CMJsonField<out Number>) = toDopeType().sub(number.toDopeType())

fun TypeExpression<NumberType>.sub(number: CMJsonField<out Number>) = sub(number.toDopeType())

fun Number.sub(number: CMJsonField<out Number>) = toDopeType().sub(number.toDopeType())

fun CMJsonField<out Number>.sub(number: Number) = toDopeType().sub(number.toDopeType())

fun <Convertable : Any, JsonType : Number> Convertable.sub(other: CMConverterField<Convertable, JsonType>) =
    toDopeType(other).sub(other.toDopeType())

fun CMJsonField<out Number>.mul(numberExpression: TypeExpression<NumberType>) = toDopeType().mul(numberExpression)

fun CMJsonField<out Number>.mul(number: CMJsonField<out Number>) = toDopeType().mul(number.toDopeType())

fun TypeExpression<NumberType>.mul(number: CMJsonField<out Number>) = mul(number.toDopeType())

fun Number.mul(number: CMJsonField<out Number>) = toDopeType().mul(number.toDopeType())

fun CMJsonField<out Number>.mul(number: Number) = toDopeType().mul(number.toDopeType())

fun <Convertable : Any, JsonType : Number> Convertable.mul(other: CMConverterField<Convertable, JsonType>) =
    toDopeType(other).mul(other.toDopeType())

fun CMJsonField<out Number>.div(numberExpression: TypeExpression<NumberType>) = toDopeType().div(numberExpression)

fun CMJsonField<out Number>.div(number: CMJsonField<out Number>) = toDopeType().div(number.toDopeType())

fun TypeExpression<NumberType>.div(number: CMJsonField<out Number>) = div(number.toDopeType())

fun Number.div(number: CMJsonField<out Number>) = toDopeType().div(number.toDopeType())

fun CMJsonField<out Number>.div(number: Number) = toDopeType().div(number.toDopeType())

fun <Convertable : Any, JsonType : Number> Convertable.div(other: CMConverterField<Convertable, JsonType>) =
    toDopeType(other).div(other.toDopeType())

fun CMJsonField<out Number>.mod(numberExpression: TypeExpression<NumberType>) = toDopeType().mod(numberExpression)

fun CMJsonField<out Number>.mod(number: CMJsonField<out Number>) = toDopeType().mod(number.toDopeType())

fun TypeExpression<NumberType>.mod(number: CMJsonField<out Number>) = mod(number.toDopeType())

fun Number.mod(number: CMJsonField<out Number>) = toDopeType().mod(number.toDopeType())

fun CMJsonField<out Number>.mod(number: Number) = toDopeType().mod(number.toDopeType())

fun <Convertable : Any, JsonType : Number> Convertable.mod(other: CMConverterField<Convertable, JsonType>) =
    toDopeType(other).mod(other.toDopeType())
