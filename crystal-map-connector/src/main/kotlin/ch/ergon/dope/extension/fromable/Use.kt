package ch.ergon.dope.extension.fromable

import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.useKeys
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

fun Bucket.useKeys(useKeys: CMField<String>) = useKeys(useKeys.toDopeType())

fun Bucket.useKeys(useKeys: CMList<String>) = useKeys(useKeys.toDopeType())
