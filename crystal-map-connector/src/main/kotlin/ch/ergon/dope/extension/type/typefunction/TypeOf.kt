package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.typeOf
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("typeOfNumber")
fun typeOf(field: CMJsonField<out Number>) = typeOf(field.toDopeType())

@JvmName("typeOfString")
fun typeOf(field: CMJsonField<String>) = typeOf(field.toDopeType())

@JvmName("typeOfBoolean")
fun typeOf(field: CMJsonField<Boolean>) = typeOf(field.toDopeType())

@JvmName("typeOfObject")
fun typeOf(field: CMObjectField<Schema>) = typeOf(field.toDopeType())

@JvmName("typeOfNumberList")
fun typeOf(field: CMJsonList<out Number>) = typeOf(field.toDopeType())

@JvmName("typeOfStringList")
fun typeOf(field: CMJsonList<String>) = typeOf(field.toDopeType())

@JvmName("typeOfBooleanList")
fun typeOf(field: CMJsonList<Boolean>) = typeOf(field.toDopeType())

@JvmName("typeOfObjectList")
fun typeOf(field: CMObjectList<Schema>) = typeOf(field.toDopeType())
