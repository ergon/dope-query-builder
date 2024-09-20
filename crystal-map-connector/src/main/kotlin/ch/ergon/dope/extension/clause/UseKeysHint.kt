package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.model.joinHint.UseKeysHintClass.Companion.UseKeysHint
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

fun useKeys(useKeys: CMJsonField<String>) = UseKeysHint(useKeys.toDopeType())

fun useKeys(useKeys: CMJsonList<String>) = UseKeysHint(useKeys.toDopeType())
