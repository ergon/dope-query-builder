package ch.ergon.dope.extension.clause.joinhint

import ch.ergon.dope.resolvable.clause.model.joinHint.keysHint
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

fun keysHint(keys: CMJsonField<String>) = keysHint(keys.toDopeType())

fun keysHint(keys: CMJsonList<String>) = keysHint(keys.toDopeType())
