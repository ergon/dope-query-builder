package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayStar
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

fun <S : Schema> arrayStar(array: CMObjectList<S>) = arrayStar(array.toDopeType())

@JvmName("schemaArrayStar")
fun <S : Schema> CMObjectList<S>.arrayStar() = arrayStar(this.toDopeType())
