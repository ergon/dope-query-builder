package ch.ergon.dope.extension.keyspace

import ch.ergon.dope.resolvable.keyspace.Keyspace
import ch.ergon.dope.resolvable.keyspace.useKeys
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

fun Keyspace.useKeys(useKeys: CMJsonField<String>) = useKeys(useKeys.toDopeType())

fun Keyspace.useKeys(useKeys: CMJsonList<String>) = useKeys(useKeys.toDopeType())
