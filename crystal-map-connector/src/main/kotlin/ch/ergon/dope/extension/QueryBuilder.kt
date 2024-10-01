package ch.ergon.dope.extension

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMType

fun QueryBuilder.select(expression: CMType, vararg expressions: CMType) =
    select(expression.toDopeType(), *expressions.map { it.toDopeType() }.toTypedArray())

fun QueryBuilder.selectDistinct(expression: CMType, vararg expressions: CMType) =
    selectDistinct(expression.toDopeType(), *expressions.map { it.toDopeType() }.toTypedArray())

@JvmName("selectRawNumber")
fun QueryBuilder.selectRaw(expression: CMJsonField<Number>) = selectRaw(expression.toDopeType())

@JvmName("selectRawString")
fun QueryBuilder.selectRaw(expression: CMJsonField<String>) = selectRaw(expression.toDopeType())

@JvmName("selectRawBoolean")
fun QueryBuilder.selectRaw(expression: CMJsonField<Boolean>) = selectRaw(expression.toDopeType())

@JvmName("selectRawListNumber")
fun QueryBuilder.selectRaw(expression: CMJsonList<Number>) = selectRaw(expression.toDopeType())

@JvmName("selectRawListString")
fun QueryBuilder.selectRaw(expression: CMJsonList<String>) = selectRaw(expression.toDopeType())

@JvmName("selectRawListBoolean")
fun QueryBuilder.selectRaw(expression: CMJsonList<Boolean>) = selectRaw(expression.toDopeType())
