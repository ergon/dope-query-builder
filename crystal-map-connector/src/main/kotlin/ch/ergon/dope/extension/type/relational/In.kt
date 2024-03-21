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
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("inArrayNumber")
fun CMField<Number>.inArray(array: TypeExpression<out ArrayType<out ValidType>>): InExpression =
    asField().inArray(array)

@JvmName("inArrayString")
fun CMField<String>.inArray(array: TypeExpression<out ArrayType<out ValidType>>): InExpression =
    asField().inArray(array)

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: TypeExpression<out ArrayType<out ValidType>>): InExpression =
    asField().inArray(array)

@JvmName("inArrayNumber")
fun CMField<Number>.inArray(array: CMList<out Any>): InExpression =
    asField().inArray(array.asArrayField())

@JvmName("inArrayString")
fun CMField<String>.inArray(array: CMList<out Any>): InExpression =
    asField().inArray(array.asArrayField())

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: CMList<out Any>): InExpression =
    asField().inArray(array.asArrayField())

@JvmName("inArrayNumber")
fun CMField<Number>.inArray(array: Collection<TypeExpression<out ValidType>>): InExpression =
    asField().inArray(array.toArrayType())

@JvmName("inArrayString")
fun CMField<String>.inArray(array: Collection<TypeExpression<out ValidType>>): InExpression =
    asField().inArray(array.toArrayType())

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: Collection<TypeExpression<out ValidType>>): InExpression =
    asField().inArray(array.toArrayType())

@JvmName("inArrayNumber")
fun Number.inArray(array: CMList<out Any>): InExpression =
    toNumberType().inArray(array.asArrayField())

@JvmName("inArrayString")
fun String.inArray(array: CMList<out Any>): InExpression =
    toStringType().inArray(array.asArrayField())

@JvmName("inArrayBoolean")
fun Boolean.inArray(array: CMList<out Any>): InExpression =
    toBooleanType().inArray(array.asArrayField())
