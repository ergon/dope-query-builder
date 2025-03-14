package ch.ergon.dope.extension.expression

import ch.ergon.dope.resolvable.asterisk
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema

fun <S : Schema> asterisk(objectField: CMObjectField<S>) = asterisk(objectField.toDopeType())

@JvmName("asteriskCMObjectFieldReceiver")
fun <S : Schema> CMObjectField<S>.asterisk() = toDopeType().asterisk()
