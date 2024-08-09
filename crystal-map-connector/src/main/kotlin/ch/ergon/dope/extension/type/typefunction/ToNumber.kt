package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toNumber
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberToNumber")
fun CMField<out Number>.toNumber() = toDopeType().toNumber()

@JvmName("stringToNumber")
fun CMField<String>.toNumber() = toDopeType().toNumber()

@JvmName("stringToNumber")
fun CMField<String>.toNumber(stringExpression: String) = toDopeType().toNumber(stringExpression.toDopeType())

@JvmName("stringToNumber")
fun CMField<String>.toNumber(stringExpression: TypeExpression<StringType>) = toDopeType().toNumber(stringExpression)

@JvmName("stringToNumber")
fun String.toNumber(stringExpression: CMField<String>) = toDopeType().toNumber(stringExpression.toDopeType())

@JvmName("stringToNumber")
fun TypeExpression<StringType>.toNumber(stringExpression: CMField<String>) = toNumber(stringExpression.toDopeType())

@JvmName("stringToNumber")
fun CMField<String>.toNumber(stringExpression: CMField<String>) = toDopeType().toNumber(stringExpression.toDopeType())

@JvmName("booleanToNumber")
fun CMField<Boolean>.toNumber() = toDopeType().toNumber()

@JvmName("numberListToNumber")
fun CMList<out Number>.toNumber() = toDopeType().toNumber()

@JvmName("stringListToNumber")
fun CMList<String>.toNumber() = toDopeType().toNumber()

@JvmName("booleanListToNumber")
fun CMList<Boolean>.toNumber() = toDopeType().toNumber()
