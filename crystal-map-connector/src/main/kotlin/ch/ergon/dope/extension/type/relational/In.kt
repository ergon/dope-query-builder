package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asArrayField
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.InExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.inArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toArrayType
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("inArrayNumber")
fun CMField<out Number>.inArray(array: TypeExpression<ArrayType<NumberType>>): InExpression<NumberType> =
    asField().inArray(array)

@JvmName("inArrayString")
fun CMField<String>.inArray(array: TypeExpression<ArrayType<StringType>>): InExpression<StringType> =
    asField().inArray(array)

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: TypeExpression<ArrayType<BooleanType>>): InExpression<BooleanType> =
    asField().inArray(array)

@JvmName("inArrayNumber")
fun CMField<out Number>.inArray(array: CMList<out Number>): InExpression<NumberType> =
    asField().inArray(array.asArrayField())

@JvmName("inArrayString")
fun CMField<String>.inArray(array: CMList<String>): InExpression<StringType> =
    asField().inArray(array.asArrayField())

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: CMList<Boolean>): InExpression<BooleanType> =
    asField().inArray(array.asArrayField())

@JvmName("inArrayNumber")
fun CMField<out Number>.inArray(array: Collection<TypeExpression<NumberType>>): InExpression<NumberType> =
    asField().inArray(array.toArrayType())

@JvmName("inArrayString")
fun CMField<String>.inArray(array: Collection<TypeExpression<StringType>>): InExpression<StringType> =
    asField().inArray(array.toArrayType())

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: Collection<TypeExpression<BooleanType>>): InExpression<BooleanType> =
    asField().inArray(array.toArrayType())

@JvmName("inArrayNumber")
fun Number.inArray(array: CMList<out Number>): InExpression<NumberType> =
    toNumberType().inArray(array.asArrayField())

@JvmName("inArrayString")
fun String.inArray(array: CMList<String>): InExpression<StringType> =
    toStringType().inArray(array.asArrayField())

@JvmName("inArrayBoolean")
fun Boolean.inArray(array: CMList<Boolean>): InExpression<BooleanType> =
    toBooleanType().inArray(array.asArrayField())
