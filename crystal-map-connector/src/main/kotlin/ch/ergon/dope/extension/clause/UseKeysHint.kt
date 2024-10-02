package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.model.joinHint.keysHint
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

fun keysHint(useKeys: CMJsonField<String>) = keysHint(useKeys.toDopeType())

fun keysHint(useKeys: CMJsonList<String>) = keysHint(useKeys.toDopeType())
