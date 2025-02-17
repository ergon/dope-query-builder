package ch.ergon.dope.extension.expression.type.function.conditional

import ch.ergon.dope.resolvable.expression.type.function.conditional.nvl
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("nvlCMNumberField")
fun nvl(initialExpression: CMJsonField<out Number>, substituteExpression: CMJsonField<out Number>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMStringField")
fun nvl(initialExpression: CMJsonField<String>, substituteExpression: CMJsonField<String>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMBooleanField")
fun nvl(initialExpression: CMJsonField<Boolean>, substituteExpression: CMJsonField<Boolean>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMNumberList")
fun nvl(initialExpression: CMJsonList<out Number>, substituteExpression: CMJsonList<out Number>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMStringList")
fun nvl(initialExpression: CMJsonList<String>, substituteExpression: CMJsonList<String>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMBooleanList")
fun nvl(initialExpression: CMJsonList<Boolean>, substituteExpression: CMJsonList<Boolean>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMNumberFieldAndNumber")
fun nvl(initialExpression: CMJsonField<out Number>, substituteExpression: Number) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMStringFieldAndString")
fun nvl(initialExpression: CMJsonField<String>, substituteExpression: String) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMBooleanFieldAndBoolean")
fun nvl(initialExpression: CMJsonField<Boolean>, substituteExpression: Boolean) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMConverterNumberList")
fun <Convertable : Any, JsonType : Number> nvl(initialExpression: CMConverterField<Convertable, JsonType>, substituteExpression: Convertable) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType(initialExpression))

@JvmName("nvlCMConverterStringList")
fun <Convertable : Any> nvl(initialExpression: CMConverterField<Convertable, String>, substituteExpression: Convertable) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType(initialExpression))

@JvmName("nvlCMConverterBooleanList")
fun <Convertable : Any> nvl(initialExpression: CMConverterField<Convertable, Boolean>, substituteExpression: Convertable) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType(initialExpression))
