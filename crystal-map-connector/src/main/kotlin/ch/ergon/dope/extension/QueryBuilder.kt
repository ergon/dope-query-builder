package ch.ergon.dope.extension

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMType

fun QueryBuilder.select(expression: CMType, vararg expressions: CMType) =
    select(expression.toDopeType(), *expressions.map { it.toDopeType() }.toTypedArray())

fun QueryBuilder.selectDistinct(expression: CMType, vararg expressions: CMType) =
    selectDistinct(expression.toDopeType(), *expressions.map { it.toDopeType() }.toTypedArray())

fun QueryBuilder.selectRaw(expression: CMType) = selectRaw(expression.toDopeType())
