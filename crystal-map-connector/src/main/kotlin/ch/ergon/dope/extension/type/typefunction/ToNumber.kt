package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toNumber
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberToNumber")
fun CMJsonField<out Number>.toNumber() = toDopeType().toNumber()

@JvmName("stringToNumber")
fun CMJsonField<String>.toNumber() = toDopeType().toNumber()

@JvmName("stringToNumber")
fun CMJsonField<String>.toNumber(filterChars: String) = toDopeType().toNumber(filterChars.toDopeType())

@JvmName("stringToNumber")
fun CMJsonField<String>.toNumber(filterChars: TypeExpression<StringType>) = toDopeType().toNumber(filterChars)

@JvmName("stringToNumber")
fun String.toNumber(filterChars: CMJsonField<String>) = toDopeType().toNumber(filterChars.toDopeType())

@JvmName("stringToNumber")
fun TypeExpression<StringType>.toNumber(filterChars: CMJsonField<String>) = toNumber(filterChars.toDopeType())

@JvmName("stringToNumber")
fun CMJsonField<String>.toNumber(filterChars: CMJsonField<String>) = toDopeType().toNumber(filterChars.toDopeType())

@JvmName("booleanToNumber")
fun CMJsonField<Boolean>.toNumber() = toDopeType().toNumber()

@JvmName("numberListToNumber")
fun CMJsonList<out Number>.toNumber() = toDopeType().toNumber()

@JvmName("stringListToNumber")
fun CMJsonList<String>.toNumber() = toDopeType().toNumber()

@JvmName("booleanListToNumber")
fun CMJsonList<Boolean>.toNumber() = toDopeType().toNumber()
