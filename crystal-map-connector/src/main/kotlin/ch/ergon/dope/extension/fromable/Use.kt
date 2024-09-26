package ch.ergon.dope.extension.fromable

import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.useKeys
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

fun Bucket.useKeys(useKeys: CMJsonField<String>) = useKeys(useKeys.toDopeType())

fun Bucket.useKeys(useKeys: CMJsonList<String>) = useKeys(useKeys.toDopeType())