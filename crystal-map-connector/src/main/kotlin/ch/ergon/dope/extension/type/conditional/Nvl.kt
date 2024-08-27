package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.nvl
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("nvlCMNumberField")
fun nvl(initialExpression: CMField<out Number>, substituteExpression: CMField<out Number>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMStringField")
fun nvl(initialExpression: CMField<String>, substituteExpression: CMField<String>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMBooleanField")
fun nvl(initialExpression: CMField<Boolean>, substituteExpression: CMField<Boolean>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMNumberList")
fun nvl(initialExpression: CMList<out Number>, substituteExpression: CMList<out Number>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMStringList")
fun nvl(initialExpression: CMList<String>, substituteExpression: CMList<String>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMBooleanList")
fun nvl(initialExpression: CMList<Boolean>, substituteExpression: CMList<Boolean>) =
    nvl(initialExpression.toDopeType(), substituteExpression.toDopeType())

@JvmName("nvlCMNumberFieldAndNumber")
fun nvl(initialExpression: CMField<out Number>, substituteExpression: Number) =
    nvl(initialExpression.toDopeType(), substituteExpression)

@JvmName("nvlCMStringFieldAndString")
fun nvl(initialExpression: CMField<String>, substituteExpression: String) =
    nvl(initialExpression.toDopeType(), substituteExpression)

@JvmName("nvlCMBooleanFieldAndBoolean")
fun nvl(initialExpression: CMField<Boolean>, substituteExpression: Boolean) =
    nvl(initialExpression.toDopeType(), substituteExpression)
