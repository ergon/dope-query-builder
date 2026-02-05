package ch.ergon.dope.extension.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.function.array.sum
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonList

fun CMJsonList<Number>.sum() = toDopeType().sum()
