package ch.ergon.dope.extension.expression.type.relational

import ch.ergon.dope.resolvable.expression.type.relational.isNotNull
import ch.ergon.dope.resolvable.expression.type.relational.isNull
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema

@JvmName("isNullNumber")
fun CMJsonField<out Number>.isNull() = toDopeType().isNull()

@JvmName("isNullString")
fun CMJsonField<String>.isNull() = toDopeType().isNull()

@JvmName("isNullBoolean")
fun CMJsonField<Boolean>.isNull() = toDopeType().isNull()

@JvmName("isNullObject")
fun CMObjectField<Schema>.isNull() = toDopeType().isNull()

@JvmName("isNotNullNumber")
fun CMJsonField<out Number>.isNotNull() = toDopeType().isNotNull()

@JvmName("isNotNullString")
fun CMJsonField<String>.isNotNull() = toDopeType().isNotNull()

@JvmName("isNotNullBoolean")
fun CMJsonField<Boolean>.isNotNull() = toDopeType().isNotNull()

@JvmName("isNotNullObject")
fun CMObjectField<Schema>.isNotNull() = toDopeType().isNotNull()
