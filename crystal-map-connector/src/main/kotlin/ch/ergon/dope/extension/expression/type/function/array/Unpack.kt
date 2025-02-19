package ch.ergon.dope.extension.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.function.array.unpack
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

fun <S : Schema> CMObjectList<S>.unpack() = toDopeType().unpack()
