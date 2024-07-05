package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotInExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.notInArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("notInArrayNumber")
fun CMField<out Number>.notInArray(array: TypeExpression<ArrayType<NumberType>>): NotInExpression<NumberType> =
    toDopeType().notInArray(array)

@JvmName("notInArrayString")
fun CMField<String>.notInArray(array: TypeExpression<ArrayType<StringType>>): NotInExpression<StringType> =
    toDopeType().notInArray(array)

@JvmName("notInArrayBoolean")
fun CMField<Boolean>.notInArray(array: TypeExpression<ArrayType<BooleanType>>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array)

@JvmName("notInArrayNumber")
fun TypeExpression<NumberType>.notInArray(array: CMList<out Number>): NotInExpression<NumberType> =
    this.notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun TypeExpression<StringType>.notInArray(array: CMList<String>): NotInExpression<StringType> =
    this.notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun TypeExpression<BooleanType>.notInArray(array: CMList<Boolean>): NotInExpression<BooleanType> =
    this.notInArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun CMField<out Number>.notInArray(array: CMList<out Number>): NotInExpression<NumberType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun CMField<String>.notInArray(array: CMList<String>): NotInExpression<StringType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun CMField<Boolean>.notInArray(array: CMList<Boolean>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun CMField<out Number>.notInArray(array: Collection<TypeExpression<NumberType>>): NotInExpression<NumberType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun CMField<String>.notInArray(array: Collection<TypeExpression<StringType>>): NotInExpression<StringType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun CMField<Boolean>.notInArray(array: Collection<TypeExpression<BooleanType>>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun Number.notInArray(array: CMList<out Number>): NotInExpression<NumberType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun String.notInArray(array: CMList<String>): NotInExpression<StringType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun Boolean.notInArray(array: CMList<Boolean>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array.toDopeType())
